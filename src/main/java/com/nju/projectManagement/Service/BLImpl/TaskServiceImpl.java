package com.nju.projectManagement.Service.BLImpl;

import com.nju.projectManagement.DO.*;
import com.nju.projectManagement.Mapper.*;
import com.nju.projectManagement.Service.BL.TaskService;
import com.nju.projectManagement.VO.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Toby Fu
 * @date 2022/2/7
 **/
@Component
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskListMapper taskListMapper;
    @Autowired
    TaskMapper taskMapper;
    @Autowired
    TaskBelongMapper taskBelongMapper;
    @Autowired
    TaskLabelMapper taskLabelMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ProjectMapper projectMapper;

    @Override
    public ResponseVO<List<TaskListVO>> getTaskLists(int projectId) {
        //todo:排序
        TaskListDOExample taskListDOExample = new TaskListDOExample();
        taskListDOExample.createCriteria().andProjectIdEqualTo(projectId);
        List<TaskListDO> taskListDOList = taskListMapper.selectByExample(taskListDOExample);

        List<Integer> taskListIdList = taskListDOList.stream().map(TaskListDO::getId).collect(Collectors.toList());
        if(taskListIdList.size()==0){
            return ResponseVO.buildSuccess(new ArrayList<>());
        }
        TaskDOExample taskDOExample = new TaskDOExample();
        taskDOExample.createCriteria().andTaskListIdIn(taskListIdList);
        List<TaskDO> taskDOList = taskMapper.selectByExample(taskDOExample);

        Map<Integer, List<TaskVO>> taskListAndTaskMap = taskDOList.stream().map(TaskVO::new).collect(Collectors.groupingBy(TaskVO::getTaskListId));

        List<TaskListVO> res = taskListDOList.stream().map(taskListDO -> {
            TaskListVO taskListVO = new TaskListVO();
            BeanUtils.copyProperties(taskListDO, taskListVO);
            taskListVO.setTasks(taskListAndTaskMap.get(taskListDO.getId()));
            return taskListVO;
        }).collect(Collectors.toList());

        return ResponseVO.buildSuccess(res);
    }

    @Override
    public ResponseVO<Boolean> updateTask(TaskVO taskVO) {
        if(taskMapper.selectByPrimaryKey(taskVO.getId())==null){
            return ResponseVO.buildFailure("no such task");
        }
        TaskDO taskDO = new TaskDO();
        BeanUtils.copyProperties(taskVO, taskDO);
        taskMapper.updateByPrimaryKey(taskDO);
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO<Boolean> deleteTask(int taskId) {
        if(taskMapper.selectByPrimaryKey(taskId)==null){
            return ResponseVO.buildFailure("No such task.");
        }
        taskMapper.deleteByPrimaryKey(taskId);
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO<Boolean> deleteTaskList(int taskListId) {
        if(taskListMapper.selectByPrimaryKey(taskListId)==null){
            return ResponseVO.buildFailure("No such task list.");
        }
        TaskDOExample taskDOExample = new TaskDOExample();
        taskDOExample.createCriteria().andTaskListIdEqualTo(taskListId);
        taskMapper.deleteByExample(taskDOExample);
        taskListMapper.deleteByPrimaryKey(taskListId);
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO<Boolean> updateTaskListName(int taskListId, String name) {
        TaskListDO taskListDO = taskListMapper.selectByPrimaryKey(taskListId);
        if(taskListDO==null){
            return ResponseVO.buildFailure("no such task list.");
        }
        taskListDO.setName(name);
        taskListMapper.updateByPrimaryKey(taskListDO);
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO<Boolean> createTaskList(int projectId, String name) {
        TaskListDO taskListDO = new TaskListDO();
        taskListDO.setName(name);
        taskListDO.setProjectId(projectId);
        taskListMapper.insert(taskListDO);
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO<Boolean> createTask(CreateTaskForm createTaskForm) {
        TaskDO taskDO = new TaskDO();
        taskDO.setTitle(createTaskForm.getTitle());
        taskDO.setProjectId(createTaskForm.getProjectId());
        taskDO.setCreateUserId(createTaskForm.getUserId());
        taskDO.setTaskListId(createTaskForm.getTaskListId());
        taskDO.setState(0);
        taskMapper.insert(taskDO);
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO<TaskInfoVO> getTaskInfo(int taskId) {
        TaskDO taskDO = taskMapper.selectByPrimaryKey(taskId);
        if(taskDO==null){
            return ResponseVO.buildFailure("no such task list.");
        }
        TaskInfoVO res = new TaskInfoVO();
        res.setBaseTaskInfo(new TaskVO(taskDO));
        TaskBelongDOExample taskBelongDOExample = new TaskBelongDOExample();
        taskBelongDOExample.createCriteria().andTaskIdEqualTo(taskId);
        List<TaskBelongDO> taskBelongDOList = taskBelongMapper.selectByExample(taskBelongDOExample);
        List<Integer> belongUserIdList = taskBelongDOList.stream().map(TaskBelongDO::getBelongUserId).collect(Collectors.toList());
        if(belongUserIdList.size()==0){
            res.setBelongUsersInfo(new ArrayList<>());
        }
        else{
            UserDOExample userDOExample = new UserDOExample();
            userDOExample.createCriteria().andIdIn(belongUserIdList);
            List<UserDO> userDOList = userMapper.selectByExample(userDOExample);
            List<UserIdAndNameVO> userIdAndNameVOList = userDOList.stream().map(UserIdAndNameVO::new).collect(Collectors.toList());
            res.setBelongUsersInfo(userIdAndNameVOList);
        }
        TaskLabelDOExample taskLabelDOExample = new TaskLabelDOExample();
        taskLabelDOExample.createCriteria().andTaskIdEqualTo(taskId);
        List<TaskLabelDO> taskLabelDOList = taskLabelMapper.selectByExample(taskLabelDOExample);
        List<String> labelNameList = taskLabelDOList.stream().map(TaskLabelDO::getName).collect(Collectors.toList());
        //todo:排序
        res.setLabels(labelNameList);
        return ResponseVO.buildSuccess(res);
    }

    @Override
    public ResponseVO<Boolean> assignTask(AssignTaskForm assignTaskForm) {
        if(taskMapper.selectByPrimaryKey(assignTaskForm.getTaskId())==null){
            return ResponseVO.buildFailure("no such task");
        }
        List<TaskBelongDO> taskBelongDOList = assignTaskForm.getUserIdList().stream().map(
                integer -> {
                    TaskBelongDO taskBelongDO = new TaskBelongDO();
                    taskBelongDO.setTaskId(assignTaskForm.getTaskId());
                    taskBelongDO.setBelongUserId(integer);
                    return taskBelongDO;
                }
        ).collect(Collectors.toList());
        TaskDO taskDO = taskMapper.selectByPrimaryKey(assignTaskForm.getTaskId());
        taskDO.setState(1);
        taskMapper.updateByPrimaryKey(taskDO);
        taskBelongMapper.batchInsert(taskBelongDOList);
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO<List<UserTaskVO>> getUserTaskList(int userId) {
        if(userMapper.selectByPrimaryKey(userId)==null){
            return ResponseVO.buildFailure("no such user");
        }

        TaskBelongDOExample taskBelongDOExample = new TaskBelongDOExample();
        taskBelongDOExample.createCriteria().andBelongUserIdEqualTo(userId);
        List<TaskBelongDO> taskBelongDOList = taskBelongMapper.selectByExample(taskBelongDOExample);
        List<Integer> taskIdList = taskBelongDOList.stream().map(TaskBelongDO::getTaskId).collect(Collectors.toList());
        if(taskIdList.size()==0){
            //taskBelongList为空
            return ResponseVO.buildSuccess(new ArrayList<>());
        }
        TaskDOExample taskDOExample = new TaskDOExample();
        taskDOExample.createCriteria().andIdIn(taskIdList);
        List<TaskDO> taskDOList = taskMapper.selectByExample(taskDOExample);

        List<Integer> projectIdList = taskDOList.stream().map(TaskDO::getProjectId).collect(Collectors.toList());
        if(projectIdList.size()==0){
            //数据完整性出现问题
            return ResponseVO.buildFailure("no such project");
        }
        ProjectDOExample projectDOExample = new ProjectDOExample();
        projectDOExample.createCriteria().andIdIn(projectIdList);
        List<ProjectDO> projectDOList = projectMapper.selectByExample(projectDOExample);

        List<Integer> belongUserIdList = taskBelongDOList.stream().map(TaskBelongDO::getBelongUserId).collect(Collectors.toList());
        if(belongUserIdList.size()==0){
            //taskBelongList为空
            return ResponseVO.buildSuccess(new ArrayList<>());
        }
        UserDOExample userDOExample = new UserDOExample();
        userDOExample.createCriteria().andIdIn(belongUserIdList);
        List<UserDO> userDOList = userMapper.selectByExample(userDOExample);
        List<UserIdAndNameVO> userIdAndNameVOList = userDOList.stream().map(UserIdAndNameVO::new).collect(Collectors.toList());

        Map<Integer, TaskVO> taskIdAndTaskVOMap = taskDOList.stream().map(TaskVO::new).
                collect(Collectors.toMap(TaskVO::getId, taskVO -> taskVO, (k1, k2)->k1));

        Map<Integer, List<TaskBelongDO>> taskIdAndTaskBelongDOMap = taskBelongDOList.stream().collect(Collectors.groupingBy(TaskBelongDO::getTaskId));

        Map<Integer, String> projectIdAndProjectNameMap = projectDOList.stream().
                collect(Collectors.toMap(ProjectDO::getId, ProjectDO::getName, (k1, k2)->k1));

        List<UserTaskVO> res = taskDOList.stream().map(taskDO -> {
            UserTaskVO userTaskVO = new UserTaskVO();
            userTaskVO.setBaseTaskInfo(taskIdAndTaskVOMap.get(taskDO.getId()));
            userTaskVO.setProjectName(projectIdAndProjectNameMap.get(taskDO.getProjectId()));

            List<Integer> curBelongUserIdList = taskIdAndTaskBelongDOMap.get(taskDO.getId()).stream().
                    map(TaskBelongDO::getBelongUserId).collect(Collectors.toList());
            List<UserIdAndNameVO> curUserIdAndNameVOList = userIdAndNameVOList.stream().
                    filter(userIdAndNameVO -> curBelongUserIdList.contains(userIdAndNameVO.getId())).collect(Collectors.toList());
            userTaskVO.setAssignUsers(curUserIdAndNameVOList);
            return userTaskVO;
        }).collect(Collectors.toList());
        return ResponseVO.buildSuccess(res);
    }

    @Override
    public ResponseVO<Boolean> changeTaskFinishState(int taskId) {
        TaskDO taskDO = taskMapper.selectByPrimaryKey(taskId);
        if(taskDO==null){
            return ResponseVO.buildFailure("no such task");
        }
        if(taskDO.getState()==0){
            return ResponseVO.buildFailure("this task is not assigned.");
        }
        //表驱动
        int[] stateArr = {0, 2, 1, 1};
        taskDO.setState(stateArr[taskDO.getState()]);
        taskMapper.updateByPrimaryKey(taskDO);
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO<Boolean> checkTask(int taskId) {
        TaskDO taskDO = taskMapper.selectByPrimaryKey(taskId);
        if(taskDO==null){
            return ResponseVO.buildFailure("no such task");
        }
        if(taskDO.getState()!=2){
            return ResponseVO.buildFailure("this task is not unchecked");
        }
        taskDO.setState(3);
        taskMapper.updateByPrimaryKey(taskDO);
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO<Boolean> addTaskLabel(int taskId, String label) {
        if(taskMapper.selectByPrimaryKey(taskId)==null){
            return ResponseVO.buildFailure("no such task");
        }
        TaskLabelDO taskLabelDO = new TaskLabelDO();
        taskLabelDO.setTaskId(taskId);
        taskLabelDO.setName(label);
        taskLabelMapper.insert(taskLabelDO);
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO<List<UserTaskVO>> getUncheckedTasks(int projectId) {
        return null;
    }
}
