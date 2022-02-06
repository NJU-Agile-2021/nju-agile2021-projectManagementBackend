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
 * @date 2022/2/6
 **/
@Tag(name = "temp",description = "temp API")
@RestController
@RequestMapping("/api/temp")
public class TempController {

    @Operation(summary = "getProjectList")
    @GetMapping(value = "/getProjectList")
    public ResponseVO<List<ProjectVO>> getProjectList(@RequestParam int userId) {
        return null;
    }

    @Operation(summary = "createProject")
    @PostMapping(value = "/createProject", consumes={ "application/json"}, produces={ "application/json"})
    public ResponseVO<ProjectVO> createProject(@RequestBody CreateProjectForm createProjectForm) {
        return null;
    }

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

    @Operation(summary = "getAnnouncements")
    @GetMapping(value = "/getAnnouncements")
    public ResponseVO<List<AnnouncementVO>> getAnnouncements(@RequestParam int projectId) {
        return null;
    }

    @Operation(summary = "createAnnouncement")
    @PostMapping(value = "/createAnnouncement", consumes={ "application/json"}, produces={ "application/json"})
    public ResponseVO<Boolean> createAnnouncement(@RequestBody AnnouncementForm announcementForm) {
        return null;
    }

    @Operation(summary = "updateAnnouncement")
    @PostMapping(value = "/updateAnnouncement", consumes={ "application/json"}, produces={ "application/json"})
    public ResponseVO<AnnouncementVO> updateAnnouncement(@RequestBody AnnouncementVO announcementVO) {
        return null;
    }

    @Operation(summary = "deleteAnnouncement")
    @GetMapping(value = "/deleteAnnouncement")
    public ResponseVO<Boolean> deleteAnnouncement(@RequestParam int announcementId) {
        return null;
    }

    @Operation(summary = "getProjectMembers")
    @GetMapping(value = "/getProjectMembers")
    public ResponseVO<List<ProjectMemberInfoForm>> getProjectMembers(@RequestParam int projectId) {
        return null;
    }

    @Operation(summary = "addProjectMember")
    @PostMapping(value = "/addProjectMember", consumes={ "application/json"}, produces={ "application/json"})
    public ResponseVO<Boolean> addProjectMember(@RequestBody ProjectMemberVO projectMemberVO) {
        return null;
    }


    @Operation(summary = "deleteProjectMember")
    @GetMapping(value = "/deleteProjectMember")
    public ResponseVO<Boolean> deleteProjectMember(@RequestParam int projectMemberRelationshipId) {
        return null;
    }

    @Operation(summary = "searchUsersByNameOrEmail")
    @GetMapping(value = "/searchUsersByNameOrEmail")
    public ResponseVO<List<UserIdAndNameVO>> searchUsersByNameOrEmail(@RequestParam String userNameOrEmail) {
        return null;
    }

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
