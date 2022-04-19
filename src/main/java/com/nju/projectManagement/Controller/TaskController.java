package com.nju.projectManagement.Controller;

import com.nju.projectManagement.Service.BL.TaskService;
import com.nju.projectManagement.VO.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    TaskService taskService;

    @Operation(summary = "getTaskLists")
    @GetMapping(value = "/getTaskLists")
    public ResponseVO<List<TaskListVO>> getTaskLists(@RequestParam int projectId) {
        return taskService.getTaskLists(projectId);
    }

    @Operation(summary = "updateTask")
    @PostMapping(value = "/updateTask", consumes={ "application/json"}, produces={ "application/json"})
    public ResponseVO<Boolean> updateTask(@RequestBody TaskVO taskVO) {
        return taskService.updateTask(taskVO);
    }

    @Operation(summary = "deleteTask")
    @GetMapping(value = "/deleteTask")
    public ResponseVO<Boolean> deleteTask(@RequestParam int taskId) {
        return taskService.deleteTask(taskId);
    }

    @Operation(summary = "deleteTaskList")
    @GetMapping(value = "/deleteTaskList")
    public ResponseVO<Boolean> deleteTaskList(@RequestParam int taskListId) {
        return taskService.deleteTaskList(taskListId);
    }


    @Operation(summary = "updateTaskListName")
    @GetMapping(value = "/updateTaskListName")
    public ResponseVO<Boolean> updateTaskListName(@RequestParam int taskListId, @RequestParam String name) {
        return taskService.updateTaskListName(taskListId, name);
    }

    @Operation(summary = "createTaskList")
    @GetMapping(value = "/createTaskList")
    public ResponseVO<Boolean> createTaskList(@RequestParam int projectId, @RequestParam String name) {
        return taskService.createTaskList(projectId, name);
    }


    @Operation(summary = "createTask")
    @PostMapping(value = "/createTask", consumes={ "application/json"}, produces={ "application/json"})
    public ResponseVO<Boolean> createTask(@RequestBody CreateTaskForm createTaskForm) {
        return taskService.createTask(createTaskForm);
    }

    @Operation(summary = "getTaskInfo")
    @GetMapping(value = "/getTaskInfo")
    public ResponseVO<TaskInfoVO> getTaskInfo(@RequestParam int taskId) {
        return taskService.getTaskInfo(taskId);
    }

    @Operation(summary = "assignTask")
    @PostMapping(value = "/assignTask", consumes={ "application/json"}, produces={ "application/json"})
    public ResponseVO<Boolean> assignTask(@RequestBody AssignTaskForm assignTaskForm) {
        return taskService.assignTask(assignTaskForm);
    }


    @Operation(summary = "getUserTaskList")
    @GetMapping(value = "/getUserTaskList")
    public ResponseVO<List<UserTaskVO>> getUserTaskList(@RequestParam int userId) {
        return taskService.getUserTaskList(userId);
    }

    @Operation(summary = "changeTaskFinishState")
    @GetMapping(value = "/changeTaskFinishState")
    public ResponseVO<Boolean> changeTaskFinishState(@RequestParam int taskId) {
        return taskService.changeTaskFinishState(taskId);
    }

    @Operation(summary = "checkTask管理员验收项目任务")
    @GetMapping(value = "/checkTask")
    ResponseVO<Boolean> checkTask(@RequestParam int taskId){
        return taskService.checkTask(taskId);
    }

    @Operation(summary = "addTaskLabel给任务添加标签")
    @GetMapping(value = "/addTaskLabel")
    ResponseVO<Boolean> addTaskLabel(@RequestParam int taskId, @RequestParam String label){
        return taskService.addTaskLabel(taskId, label);
    }

    @Operation(summary = "用户主动认领任务")
    @PostMapping(value = "/claimTask")
    ResponseVO<Boolean> claimTask(@RequestParam int userId,@RequestParam int taskId){
        return taskService.claimTask(userId,taskId);
    }

//    @Operation(summary = "getUncheckedTasks获取未验收的任务列表")
//    @GetMapping(value = "/getUncheckedTasks")
//    ResponseVO<List<UserTaskVO>> getUncheckTasks(@RequestParam int projectId){
//        return null;
//    }
}
