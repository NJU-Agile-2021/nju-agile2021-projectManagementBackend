package com.nju.projectManagement.Service.BLImpl;

import com.nju.projectManagement.DO.*;
import com.nju.projectManagement.Exception.ScheduleException;
import com.nju.projectManagement.Mapper.ProjectMapper;
import com.nju.projectManagement.Mapper.ProjectMemberMapper;
import com.nju.projectManagement.Mapper.ScheduleMapper;
import com.nju.projectManagement.Mapper.TaskMapper;
import com.nju.projectManagement.Service.BL.ScheduleService;
import com.nju.projectManagement.VO.ResponseVO;
import com.nju.projectManagement.VO.ScheduleForm;
import com.nju.projectManagement.VO.ScheduleInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author WangYuxiao
 * @date 2022/4/18 20:14
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    ScheduleMapper scheduleMapper;

    @Autowired
    ProjectMapper projectMapper;

    @Autowired
    TaskMapper taskMapper;

    @Override
    public ResponseVO<Boolean> addSchedule(ScheduleForm schedule) {
        ScheduleDO scheduleDO = new ScheduleDO();
        BeanUtils.copyProperties(schedule, scheduleDO);
        if (scheduleMapper.insert(scheduleDO) == 1) {
            return ResponseVO.buildSuccess(true);
        }
        return new ResponseVO<>(false, "add schedule failed", false);
    }

    @Override
    public ResponseVO<List<ScheduleInfoVO>> getAllSchedule(Integer userId) {
        List<ScheduleDO> scheduleDOList = getAllScheduleByUserId(userId);
        List<ProjectDO> projectDOList = getProjectByIdList(scheduleDOList.stream()
                .map(ScheduleDO::getProjectId).collect(Collectors.toList()));
        List<TaskDO> taskDOList = getTaskByIdList(scheduleDOList.stream()
                .map(ScheduleDO::getTaskId).collect(Collectors.toList()));
        List<ScheduleInfoVO> scheduleInfoVOList = scheduleDOList.stream().map(scheduleDO -> {
            ScheduleInfoVO scheduleInfoVO = new ScheduleInfoVO();
            scheduleInfoVO.setId(scheduleDO.getId());
            scheduleInfoVO.setTime(scheduleDO.getTime());
            ProjectDO curProject = projectDOList.stream()
                    .filter(x -> x.getId().equals(scheduleDO.getProjectId())).findFirst()
                    .orElseThrow(() -> new ScheduleException("project does not exist"));
            scheduleInfoVO.setProjectId(curProject.getId());
            scheduleInfoVO.setProjectName(curProject.getName());
            scheduleInfoVO.setProjectDescription(curProject.getDescription());
            scheduleInfoVO.setUserId(scheduleDO.getUserId());
            TaskDO curTask = taskDOList.stream()
                    .filter(x -> x.getId().equals(scheduleDO.getTaskId())).findFirst()
                    .orElseThrow(() -> new ScheduleException("task does not exist"));
            scheduleInfoVO.setTaskId(curTask.getId());
            scheduleInfoVO.setTaskContent(curTask.getContent());
            scheduleInfoVO.setTaskPriority(curTask.getPriority());
            scheduleInfoVO.setTaskState(curTask.getState());
            scheduleInfoVO.setTaskTitle(curTask.getTitle());
            return scheduleInfoVO;
        }).collect(Collectors.toList());
//        Map<String, List<ScheduleInfoVO>> test = scheduleInfoVOList.stream().collect(Collectors.groupingBy(item -> new SimpleDateFormat("yyyy-MM-dd").format(item.getTime())));
        return ResponseVO.buildSuccess(scheduleInfoVOList);
    }

    /**
     * 根据用户id查询所有日程
     * @param userId
     * @return
     */
    private List<ScheduleDO> getAllScheduleByUserId(Integer userId) {
        ScheduleDOExample example = new ScheduleDOExample();
        example.createCriteria().andUserIdEqualTo(userId);
        return scheduleMapper.selectByExample(example);
    }

    /**
     * 根据id列表查询项目
     * @param ids
     * @return
     */
    private List<ProjectDO> getProjectByIdList(List<Integer> ids) {
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }
        ProjectDOExample example = new ProjectDOExample();
        example.createCriteria().andIdIn(ids);
        return projectMapper.selectByExample(example);
    }

    /**
     * 根据任务id查询任务
     * @param ids
     * @return
     */
    private List<TaskDO> getTaskByIdList(List<Integer> ids) {
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }
        TaskDOExample example = new TaskDOExample();
        example.createCriteria().andIdIn(ids);
        return taskMapper.selectByExample(example);
    }
}
