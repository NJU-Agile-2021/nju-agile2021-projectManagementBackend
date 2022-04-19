package com.nju.projectManagement.Controller;

import com.nju.projectManagement.Service.BL.StatisticService;
import com.nju.projectManagement.VO.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @author Toby Fu
 * @date 2022/2/7
 **/

@Tag(name = "statistic",description = "statistic API")
@RestController
@RequestMapping("/api/statistic")
public class StatisticController {

    @Autowired
    StatisticService statisticService;

    @Operation(summary = "getOverviewStatistic, 类别分别为未分配,进行中并已延期,进行中并未延期,待验收,延期完成,期限内完成,总数")
    @GetMapping(value = "/getOverviewStatistic")
    public ResponseVO<Map<String, Integer>> getOverviewStatistic
            (@RequestParam @NotNull(message = "Empty project id") Integer projectId) {
        return statisticService.getOverviewStatistic(projectId);
    }

    @Operation(summary = "getStatisticDetails, 类别分别为未分配,进行中并已延期,进行中并未延期,待验收,延期完成,期限内完成,总数")
    @GetMapping(value = "/getStatisticDetails")
    public ResponseVO<Map<String, List<StatisticTaskVO>>> getStatisticDetails
            (@RequestParam @NotNull(message = "Empty project id") Integer projectId) {
        return statisticService.getStatisticDetails(projectId);
    }

    @Operation(summary = "getTaskFinishedNumberByDateChart")
    @PostMapping(value = "/getTaskFinishedNumberByDateChart", consumes={ "application/json"}, produces={ "application/json"})
    public ResponseVO<List<DateAndNumberOfFinishTasksForm>> getTaskFinishedNumberByDateChart
            (@RequestBody @NotNull(message = "Invalid parameter") @Valid TaskFinishedInPeriodForm taskFinishedInPeriodForm) {
        return statisticService.getTaskFinishedNumberByDateChart(taskFinishedInPeriodForm);
    }


    @Operation(summary = "getTaskFinishedNumberByMemberChart")
    @GetMapping(value = "/getTaskFinishedNumberByMemberChart")
    public ResponseVO<List<MemberAndNumberOfFinishTasksForm>> getTaskFinishedNumberByMemberChart
            (@RequestParam @NotNull(message = "Empty project id") Integer projectId) {
        return statisticService.getTaskFinishedNumberByMemberChart(projectId);
    }

    @Operation(summary = "获得该项目的各个人员的工作进度：已完成项目数、进行中项目数、已延期项目数")
    @GetMapping(value = "/getUserWorkProgress")
    public ResponseVO<List<UserWorkProgessVo>> getUserWorkProgressByProjectId(@RequestParam @NotNull(message = "Empty project id") Integer projectId){
        return statisticService.getUserWorkProgressByProjectId(projectId);
    }

}
