package com.nju.projectManagement.Controller;

import com.nju.projectManagement.VO.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Toby Fu
 * @date 2022/2/7
 **/
@Tag(name = "task",description = "task API")
@RestController
@RequestMapping("/api/task")
public class TaskController {
    @Operation(summary = "getTaskLists")
    @GetMapping(value = "/getTaskLists")
    public ResponseVO<List<TaskListVO>> getTaskLists(@RequestParam int projectId) {
        return null;
    }

    @Operation(summary = "updateTask")
    @PostMapping(value = "/updateTask", consumes={ "application/json"}, produces={ "application/json"})
    public ResponseVO<Boolean> updateTask(@RequestBody TaskVO taskVO) {
        return null;
    }

    @Operation(summary = "deleteTask")
    @GetMapping(value = "/deleteTask")
    public ResponseVO<Boolean> deleteTask(@RequestParam int taskId) {
        return null;
    }

    @Operation(summary = "deleteTaskList")
    @GetMapping(value = "/deleteTaskList")
    public ResponseVO<Boolean> deleteTaskList(@RequestParam int taskListId) {
        return null;
    }


    @Operation(summary = "updateTaskListName")
    @PostMapping(value = "/updateTaskListName", consumes={ "application/json"}, produces={ "application/json"})
    public ResponseVO<Boolean> updateTaskListName(@RequestParam int taskListId, @RequestParam String name) {
        return null;
    }

    @Operation(summary = "createTaskList")
    @GetMapping(value = "/createTaskList")
    public ResponseVO<Boolean> createTaskList(@RequestParam int projectId, @RequestParam String name) {
        return null;
    }


    @Operation(summary = "createTask")
    @PostMapping(value = "/createTask", consumes={ "application/json"}, produces={ "application/json"})
    public ResponseVO<Boolean> createTask(@RequestBody CreateTaskForm createTaskForm) {
        return null;
    }

    @Operation(summary = "getTaskInfo")
    @GetMapping(value = "/getTaskInfo")
    public ResponseVO<TaskInfoVO> getTaskInfo(@RequestParam int taskId) {
        return null;
    }

    @Operation(summary = "assignTask")
    @PostMapping(value = "/assignTask", consumes={ "application/json"}, produces={ "application/json"})
    public ResponseVO<Boolean> assignTask(@RequestBody AssignTaskForm assignTaskForm) {
        return null;
    }


    @Operation(summary = "getUserTaskList")
    @GetMapping(value = "/getUserTaskList")
    public ResponseVO<List<UserTaskVO>> getUserTaskList(@RequestParam int userId) {
        return null;
    }

    @Operation(summary = "changeTaskFinishState")
    @GetMapping(value = "/changeTaskFinishState")
    public ResponseVO<Boolean> changeTaskFinishState(@RequestParam int taskId) {
        return null;
    }

}
