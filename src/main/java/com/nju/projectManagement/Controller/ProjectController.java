package com.nju.projectManagement.Controller;

import com.nju.projectManagement.Service.BL.ProjectService;
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
@Tag(name = "project",description = "project API")
@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @Operation(summary = "根据用户id获得所在的项目信息")
    @GetMapping(value = "/getProjectList")
    public ResponseVO<List<ProjectVO>> getProjectList(@RequestParam int userId) {
        return projectService.getProjectList(userId);
    }

    @Operation(summary = "创建项目")
    @PostMapping(value = "/createProject", consumes={ "application/json"}, produces={ "application/json"})
    public ResponseVO<ProjectVO> createProject(@RequestBody CreateProjectForm createProjectForm) {
        return projectService.createProject(createProjectForm);
    }

    @Operation(summary = "根据项目id获得项目所有成员，其中的role属性0为普通用户，1为管理员,2为创建者")
    @GetMapping(value = "/getProjectMembers")
    public ResponseVO<List<ProjectMemberInfoForm>> getProjectMembers(@RequestParam int projectId) {
        return projectService.getProjectMembers(projectId);
    }

    @Operation(summary = "获得一个用户在某个项目的角色(0:普通，1：管理员，2：创建者)")
    @GetMapping("/getRoleOfUserInProject")
    public ResponseVO<Integer> getRoleOfUserInProject(@RequestParam int uid,@RequestParam int pid){
        return projectService.getRoleOfUserInProject(uid, pid);
    }

    @Operation(summary = "将用户加入项目")
    @PostMapping(value = "/addProjectMember", consumes={ "application/json"}, produces={ "application/json"})
    public ResponseVO<Boolean> addProjectMember(@RequestBody ProjectMemberVO projectMemberVO) {
        return projectService.addProjectMember(projectMemberVO);
    }

    @Operation(summary = "更新用户在项目的角色")
    @PostMapping(value = "/updateRoleOfProjectMember", consumes={ "application/json"}, produces={ "application/json"})
    public ResponseVO<Boolean> updateRoleOfProjectMember(@RequestBody ProjectMemberRoleUpdateForm projectMemberRoleUpdateForm){
        return projectService.updateRoleOfProjectMember(projectMemberRoleUpdateForm);
    }

    @Operation(summary = "将用户移出项目")
    @GetMapping(value = "/deleteProjectMember")
    public ResponseVO<Boolean> deleteProjectMember(@RequestParam int projectMemberRelationshipId) {
        return projectService.deleteProjectMember(projectMemberRelationshipId);
    }

    @Operation(summary = "获得该项目的所有公告")
    @GetMapping(value = "/getAnnouncements")
    public ResponseVO<List<AnnouncementVO>> getAnnouncements(@RequestParam int projectId) {
        return projectService.getAnnouncements(projectId);
    }

    @Operation(summary = "发布公告")
    @PostMapping(value = "/createAnnouncement", consumes={ "application/json"}, produces={ "application/json"})
    public ResponseVO<Boolean> createAnnouncement(@RequestBody AnnouncementForm announcementForm) {
        return projectService.createAnnouncement(announcementForm);
    }

    @Operation(summary = "更新公告")
    @PostMapping(value = "/updateAnnouncement", consumes={ "application/json"}, produces={ "application/json"})
    public ResponseVO<AnnouncementVO> updateAnnouncement(@RequestBody AnnouncementVO announcementVO) {
        return projectService.updateAnnouncement(announcementVO);
    }

    @Operation(summary = "删除公告")
    @GetMapping(value = "/deleteAnnouncement")
    public ResponseVO<Boolean> deleteAnnouncement(@RequestParam int announcementId) {
        return projectService.deleteAnnouncement(announcementId);
    }

}
