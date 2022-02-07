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
@Tag(name = "project",description = "project API")
@RestController
@RequestMapping("/api/project")
public class ProjectController {
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

}
