package com.nju.projectManagement.Controller;

import com.nju.projectManagement.Service.BL.ScheduleService;
import com.nju.projectManagement.VO.ResponseVO;
import com.nju.projectManagement.VO.ScheduleForm;
import com.nju.projectManagement.VO.ScheduleInfoVO;
import com.nju.projectManagement.VO.UserIdAndNameVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author WangYuxiao
 * @date 2022/4/19 10:53
 */
@Tag(name = "Schedule",description = "Schedule API")
@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @Operation(summary = "add")
    @PostMapping(value = "/add",consumes={ "application/json"},produces={ "application/json" })
    public ResponseVO<Boolean> add(@RequestBody @Valid @NotNull(message = "Invalid parameter") ScheduleForm scheduleForm) {
        return scheduleService.addSchedule(scheduleForm);
    }

    @Operation(summary = "getAllSchedule")
    @GetMapping(value = "/getAllSchedule")
    public ResponseVO<List<ScheduleInfoVO>> getAllSchedule(@RequestParam @NotNull(message = "Invalid parameter") Integer userId) {
        return scheduleService.getAllSchedule(userId);
    }
}
