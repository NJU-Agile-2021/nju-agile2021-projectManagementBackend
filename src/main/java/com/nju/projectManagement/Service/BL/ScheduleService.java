package com.nju.projectManagement.Service.BL;

import com.nju.projectManagement.VO.ResponseVO;
import com.nju.projectManagement.VO.ScheduleForm;
import com.nju.projectManagement.VO.ScheduleInfoVO;

import java.util.List;

/**
 * @author WangYuxiao
 * @date 2022/4/18 15:07
 */
public interface ScheduleService {

    /**
     * 添加任务到日程
     * @param schedule
     * @return
     */
    ResponseVO<Boolean> addSchedule(ScheduleForm schedule);

    /**
     * 获取某一用户所有日程
     * @param userId
     * @return
     */
    ResponseVO<List<ScheduleInfoVO>> getAllSchedule(Integer userId);
}
