package com.nju.projectManagement.Service.BL;

import com.nju.projectManagement.VO.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author huang
 */
public interface ProjectService {
    /**
     * 根据用户id，获得他所在的项目信息
     * @param userId
     * @return
     */
    ResponseVO<List<ProjectVO>> getProjectList( int userId);

    /**
     * 创建一个项目
     * @param createProjectForm
     * @return
     */
    ResponseVO<ProjectVO> createProject(CreateProjectForm createProjectForm);

    /**
     * 根据项目id获得项目所有成员，其中的role属性0为普通用户，1为管理员，2为创建者
     * @param projectId
     * @return
     */
    ResponseVO<List<ProjectMemberInfoForm>> getProjectMembers(int projectId);

    /**
     * 获得一个用户在某个项目的角色(0:普通，1：管理员，2：创建者)
     * @param uid
     * @param pid
     * @return
     */
    ResponseVO<Integer> getRoleOfUserInProject(int uid,int pid);

    /**
     * 将用户加入项目
     * @param projectMemberVO
     * @return
     */
    ResponseVO<Boolean> addProjectMember(ProjectMemberVO projectMemberVO);

    /**
     * 更新用户在项目的角色
     * @param projectMemberRoleUpdateForm
     * @return
     */
    ResponseVO<Boolean> updateRoleOfProjectMember(ProjectMemberRoleUpdateForm projectMemberRoleUpdateForm);

    /**
     * 将用户移出项目
     * @param projectMemberRelationshipId
     * @return
     */
    ResponseVO<Boolean> deleteProjectMember(int projectMemberRelationshipId);

    /**
     * 根据项目id获得其下的所有公告信息
     * @param projectId
     * @return
     */
    ResponseVO<List<AnnouncementVO>> getAnnouncements(int projectId);

    /**
     * 发布项目公告
     * @param announcementForm
     * @return
     */
    ResponseVO<Boolean> createAnnouncement(AnnouncementForm announcementForm);

    /**
     * 更新项目公告
     * @param announcementVO
     * @return
     */
    ResponseVO<AnnouncementVO> updateAnnouncement(AnnouncementVO announcementVO);

    /**
     * 删除项目公告
     * @param announcementId
     * @return
     */
    ResponseVO<Boolean> deleteAnnouncement(int announcementId);
}
