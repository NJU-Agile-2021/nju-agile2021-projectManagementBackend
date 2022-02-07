package com.nju.projectManagement.Controller;

import com.nju.projectManagement.Enum.TaskStateKind;
import com.nju.projectManagement.VO.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

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
    @Operation(summary = "getOverviewStatistic, 类别分别为未分配,进行中并已延期,进行中并未延期,待验收,延期完成,期限内完成,总数")
    @GetMapping(value = "/getOverviewStatistic")
    public ResponseVO<Map<TaskStateKind, Integer>> getOverviewStatistic(@RequestParam int projectId) {
        return null;
    }

    @Operation(summary = "getStatisticDetails, 类别分别为未分配,进行中并已延期,进行中并未延期,待验收,延期完成,期限内完成,总数")
    @GetMapping(value = "/getStatisticDetails")
    public ResponseVO<Map<TaskStateKind, List<StatisticTaskVO>>> getStatisticDetails(@RequestParam int projectId) {
        return null;
    }

    @Operation(summary = "getTaskFinishedNumberByDateChart")
    @PostMapping(value = "/getTaskFinishedNumberByDateChart", consumes={ "application/json"}, produces={ "application/json"})
    public ResponseVO<List<DateAndNumberOfFinishTasksForm>> getTaskFinishedNumberByDateChart(@RequestBody TaskFinishedInPeriodForm taskFinishedInPeriodForm) {
        return null;
    }


    @Operation(summary = "getTaskFinishedNumberByMemberChart")
    @GetMapping(value = "/getTaskFinishedNumberByMemberChart")
    public ResponseVO<List<MemberAndNumberOfFinishTasksForm>> getTaskFinishedNumberByMemberChDateAndNumberOfFinishTasksFormart(@RequestParam int projectId) {
        return null;
    }

}
