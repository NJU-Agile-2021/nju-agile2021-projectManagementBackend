package com.nju.projectManagement.Service.BLImpl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.nju.projectManagement.DO.*;
import com.nju.projectManagement.Enum.TaskStateKind;
import com.nju.projectManagement.Exception.StatisticException;
import com.nju.projectManagement.Mapper.ProjectMapper;
import com.nju.projectManagement.Mapper.TaskBelongMapper;
import com.nju.projectManagement.Mapper.TaskListMapper;
import com.nju.projectManagement.Mapper.TaskMapper;
import com.nju.projectManagement.Service.BL.StatisticService;
import com.nju.projectManagement.Service.BL.UserServiceForBl;
import com.nju.projectManagement.VO.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author WangYuxiao
 * @date 2022/2/8 23:14
 */
@Service
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private TaskListMapper taskListMapper;

    @Autowired
    private TaskBelongMapper taskBelongMapper;

    @Autowired
    private UserServiceForBl userService;

    @Override
    public ResponseVO<Map<String, Integer>> getOverviewStatistic(Integer projectId) {
        verifyProject(projectId);
        List<TaskDO> taskList = getAllTaskByProjectId(projectId);

        Map<String, Integer> resultMap = new HashMap<>(10);
        for (TaskStateKind taskStateKind : TaskStateKind.values()) {
            resultMap.put(taskStateKind.toString(), 0);
        }
        for (TaskDO task: taskList) {
            int state = task.getState();
            if (state == 0) {
                resultMap.put(TaskStateKind.UNASSIGNED.toString(), resultMap.get(TaskStateKind.UNASSIGNED.toString()) + 1);
            }
            else if (state == 1) {
                if (task.getEstimateCompleteTime().after(new Date())) {
                    resultMap.put(TaskStateKind.IN_PROGRESS.toString(), resultMap.get(TaskStateKind.IN_PROGRESS.toString()) + 1);
                }
                else {
                    resultMap.put(TaskStateKind.IN_PROGRESS_DELAYED.toString(), resultMap.get(TaskStateKind.IN_PROGRESS_DELAYED.toString()) + 1);
                }
            }
            else if (state == 2) {
                resultMap.put(TaskStateKind.TO_BE_CHECKED.toString(), resultMap.get(TaskStateKind.TO_BE_CHECKED.toString()) + 1);
            }
            else if (state == 3) {
                if (task.getEstimateCompleteTime().after(new Date())) {
                    resultMap.put(TaskStateKind.FINISHED.toString(), resultMap.get(TaskStateKind.FINISHED.toString()) + 1);
                }
                else {
                    resultMap.put(TaskStateKind.FINISHED_DELAYED.toString(), resultMap.get(TaskStateKind.FINISHED_DELAYED.toString()) + 1);
                }
            }
        }
        return ResponseVO.buildSuccess(resultMap);
    }

    @Override
    public ResponseVO<Map<String, List<StatisticTaskVO>>> getStatisticDetails(Integer projectId) {
        verifyProject(projectId);
        List<TaskDO> allTask = getAllTaskByProjectId(projectId);
        List<TaskListDO> taskLists = getTaskListByProjectId(projectId);
        List<TaskBelongDO> taskBelongList = getAllTaskBelongByTaskId(allTask
                .stream().map(TaskDO::getId).collect(Collectors.toList()));
        List<UserIdAndNameVO> userIdAndNameVOList = userService.getBatchUserSimpleInfoById(taskBelongList
                .stream().map(TaskBelongDO::getBelongUserId).distinct().collect(Collectors.toList()));
        Map<String, List<StatisticTaskVO>> resultMap = new HashMap<>(10);
        for (TaskStateKind taskStateKind : TaskStateKind.values()) {
            resultMap.put(taskStateKind.toString(), new ArrayList<>());
        }
        for (TaskDO task : allTask) {
            Optional<TaskStateKind> taskState = TaskStateKind.fromValue(task.getState());
            if (!taskState.isPresent()) {
                continue;
            }
            String taskListName = taskLists.stream()
                    .filter(x -> x.getId().equals(task.getTaskListId()))
                    .findFirst()
                    .orElseThrow(() -> new StatisticException("Task list does not exist"))
                    .getName();
            List<Integer> belongUserIdList = taskBelongList.stream()
                    .filter(x -> x.getTaskId().equals(task.getId()))
                    .map(TaskBelongDO::getBelongUserId)
                    .collect(Collectors.toList());
            List<UserIdAndNameVO> users = userIdAndNameVOList.stream()
                    .filter(x -> belongUserIdList.contains(x.getId()))
                    .collect(Collectors.toList());
            StatisticTaskVO statisticTaskVO = new StatisticTaskVO();
            TaskVO taskVO = new TaskVO();
            BeanUtils.copyProperties(task, taskVO);
            statisticTaskVO.setBaseTaskInfo(taskVO);
            statisticTaskVO.setTaskListName(taskListName);
            statisticTaskVO.setBelongUsersInfo(users);
            resultMap.get(taskState.get().toString()).add(statisticTaskVO);
        }
        return ResponseVO.buildSuccess(resultMap);
    }

    @Override
    public ResponseVO<List<DateAndNumberOfFinishTasksForm>> getTaskFinishedNumberByDateChart(TaskFinishedInPeriodForm form) {
        verifyProject(form.getProjectId());
        List<TaskDO> tasks = getFinishedTaskByProjectIdAndTime(form.getProjectId(), form.getEndTime());
        long dateRange = DateUtil.between(form.getStartTime(), form.getEndTime(), DateUnit.DAY);
        List<DateAndNumberOfFinishTasksForm> result = new ArrayList<>();
        for (int offset = 0; offset <= (int) dateRange; offset++) {
            Date curDate = DateUtil.offsetDay(form.getStartTime(), offset);
            long finishedTaskNum = tasks.stream().filter(x -> x.getCompleteTime().before(DateUtil.offsetDay(curDate, 1))).count();
            DateAndNumberOfFinishTasksForm f = new DateAndNumberOfFinishTasksForm();
            f.setDate(curDate);
            f.setNumOfFinishedTasks((int) finishedTaskNum);
            result.add(f);
        }
        return ResponseVO.buildSuccess(result);
    }

    @Override
    public ResponseVO<List<MemberAndNumberOfFinishTasksForm>> getTaskFinishedNumberByMemberChart(Integer projectId) {
        verifyProject(projectId);
        List<TaskDO> allTask = getFinishedTaskByProjectId(projectId);
        List<TaskBelongDO> taskBelongList = getAllTaskBelongByTaskId(allTask
                .stream().map(TaskDO::getId).collect(Collectors.toList()));
        List<UserIdAndNameVO> userIdAndNameVOList = userService.getBatchUserSimpleInfoById(taskBelongList
                .stream().map(TaskBelongDO::getBelongUserId).distinct().collect(Collectors.toList()));

        Map<Integer, Long> userTaskNum = taskBelongList.stream()
                .collect(Collectors.groupingBy(TaskBelongDO::getBelongUserId, Collectors.counting()));
        List<MemberAndNumberOfFinishTasksForm> finishTasksForms = new ArrayList<>();

        userTaskNum.forEach((userId, num) -> {
            MemberAndNumberOfFinishTasksForm form = new MemberAndNumberOfFinishTasksForm();
            form.setUserId(userId);
            form.setNumOfFinishedTasks(num.intValue());
            form.setUsername(userIdAndNameVOList.stream()
                    .filter(x -> x.getId().equals(userId))
                    .findFirst()
                    .orElseThrow(() -> new StatisticException("No such user"))
                    .getName());
            finishTasksForms.add(form);
        });

        return ResponseVO.buildSuccess(finishTasksForms);
    }

    @Override
    public ResponseVO<List<UserWorkProgessVo>> getUserWorkProgressByProjectId(Integer projectId) {
        verifyProject(projectId);
        List<TaskDO> taskDOS=getAllTaskByProjectId(projectId);
        List<TaskBelongDO> taskBelongDOS=getAllTaskBelongByTaskId(taskDOS.stream().map(TaskDO::getId).collect(Collectors.toList()));
        Map<Integer,TaskVO> taskVOMap=new HashMap<>();
        for(TaskDO i:taskDOS){
            taskVOMap.put(i.getId(),new TaskVO(i));
        }
        List<UserIdAndNameVO> users=userService.getBatchUserSimpleInfoById(taskBelongDOS.stream().map(TaskBelongDO::getBelongUserId).collect(Collectors.toList()));
        List<UserWorkProgessVo> ans=new ArrayList<>();
        for(UserIdAndNameVO user:users){
            UserWorkProgessVo now=new UserWorkProgessVo();
            now.setUser(user);
            for(TaskBelongDO belong:taskBelongDOS){
                if(belong.getBelongUserId().equals(user.getId())){
                    TaskVO thisTask=taskVOMap.get(belong.getTaskId());
                    if(thisTask.getState()==1&&thisTask.getEstimateCompleteTime().before(new Date(System.currentTimeMillis()))){
                        now.getPostponed().add(taskVOMap.get(belong.getTaskId()));
                    }
                    else if(thisTask.getState()==3){
                        now.getCompleted().add(taskVOMap.get(belong.getTaskId()));
                    }
                    else{
                        now.getInProgress().add(taskVOMap.get(belong.getTaskId()));
                    }
                }
            }
            ans.add(now);
        }
        return ResponseVO.buildSuccess(ans);
    }

    private void verifyProject(Integer projectId) {
        ProjectDO projectDO = projectMapper.selectByPrimaryKey(projectId);
        if (projectDO == null) {
            throw new StatisticException("No such project");
        }
    }

    private List<TaskDO> getAllTaskByProjectId(Integer projectId) {
        TaskDOExample taskExample = new TaskDOExample();
        taskExample.createCriteria().andProjectIdEqualTo(projectId);
        return taskMapper.selectByExample(taskExample);
    }

    private List<TaskListDO> getTaskListByProjectId(Integer projectId) {
        TaskListDOExample example = new TaskListDOExample();
        example.createCriteria().andProjectIdEqualTo(projectId);
        return taskListMapper.selectByExample(example);
    }

    private List<TaskDO> getFinishedTaskByProjectId(Integer projectId) {
        TaskDOExample taskExample = new TaskDOExample();
        taskExample.createCriteria().andProjectIdEqualTo(projectId).andStateGreaterThanOrEqualTo(2);
        return taskMapper.selectByExample(taskExample);
    }

    private List<TaskBelongDO> getAllTaskBelongByTaskId(List<Integer> tasks) {
        if (tasks.size() == 0) {
            return new ArrayList<>();
        }
        TaskBelongDOExample example = new TaskBelongDOExample();
        example.createCriteria().andTaskIdIn(tasks);
        return taskBelongMapper.selectByExample(example);
    }

    private List<TaskDO> getFinishedTaskByProjectIdAndTime(Integer projectId, Date endTime) {
        TaskDOExample example = new TaskDOExample();
        example.createCriteria().andProjectIdEqualTo(projectId).andCompleteTimeLessThanOrEqualTo(endTime);
        return taskMapper.selectByExample(example);
    }
}
