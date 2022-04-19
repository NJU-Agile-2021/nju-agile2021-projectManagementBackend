package com.nju.projectManagement.Service.BL;

import com.nju.projectManagement.Enum.TaskStateKind;
import com.nju.projectManagement.VO.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @author WangYuxiao
 * @date 2022/2/8 23:13
 */
public interface StatisticService {

    /**
     * 获取项目统计
     * @param projectId 项目id
     * @return 统计结果
     */
    ResponseVO<Map<String, Integer>> getOverviewStatistic(Integer projectId);

    /**
     * 获取项目详细统计
     * @param projectId 项目id
     * @return 统计结果
     */
    ResponseVO<Map<String, List<StatisticTaskVO>>> getStatisticDetails(Integer projectId);

    /**
     * 统计日期区间内的任务完成情况
     * @param form 日期区间及项目信息
     * @return 统计结果
     */
    ResponseVO<List<DateAndNumberOfFinishTasksForm>> getTaskFinishedNumberByDateChart(TaskFinishedInPeriodForm form);

    /**
     * 获取项目成员任务完成情况
     * @param projectId 项目id
     * @return 统计结果
     */
    ResponseVO<List<MemberAndNumberOfFinishTasksForm>> getTaskFinishedNumberByMemberChart(Integer projectId);

    /**
     * 获取该项目的各个人员的任务进度
     * @param projectId
     * @return 进度结果
     */
    ResponseVO<List<UserWorkProgessVo>> getUserWorkProgressByProjectId(Integer projectId);
}
