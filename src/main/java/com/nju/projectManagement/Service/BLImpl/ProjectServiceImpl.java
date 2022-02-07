package com.nju.projectManagement.Service.BLImpl;

import com.nju.projectManagement.DO.*;
import com.nju.projectManagement.Mapper.AnnouncementMapper;
import com.nju.projectManagement.Mapper.ProjectMapper;
import com.nju.projectManagement.Mapper.ProjectMemberMapper;
import com.nju.projectManagement.Mapper.UserMapper;
import com.nju.projectManagement.Service.BL.ProjectService;
import com.nju.projectManagement.VO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author huang
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    ProjectMapper projectMapper;

    @Autowired
    ProjectMemberMapper projectMemberMapper;

    @Autowired
    AnnouncementMapper announcementMapper;


    @Override
    public ResponseVO<List<ProjectVO>> getProjectList(int userId) {
        //取出有关联关系的project
        ProjectMemberDOExample projectMemberDOExample=new ProjectMemberDOExample();
        projectMemberDOExample.createCriteria().andUserIdEqualTo(userId);
        List<ProjectMemberDO> projectMembers=projectMemberMapper.selectByExample(projectMemberDOExample);
        if(projectMembers.size()==0){
            //为空
            return ResponseVO.buildSuccess(new ArrayList<>());
        }
        List<Integer> projectIds=projectMembers.stream().map(ProjectMemberDO::getProjectId).collect(Collectors.toList());
        ProjectDOExample projectDOExample =new ProjectDOExample();
        projectDOExample.createCriteria().andIdIn(projectIds);
        List<ProjectDO> projectDOS=projectMapper.selectByExample(projectDOExample);
        List<ProjectVO> ans=projectDOS.stream().map(i->new ProjectVO(i.getId(),i.getName(),i.getDescription())).collect(Collectors.toList());
        return ResponseVO.buildSuccess(ans);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseVO<ProjectVO> createProject(CreateProjectForm createProjectForm) {
        if(!createProjectForm.valid()){
            return ResponseVO.buildFailure("参数错误");
        }
        UserDO nowUser= userMapper.selectByPrimaryKey(createProjectForm.getUserId());
        if(nowUser==null){
            return ResponseVO.buildFailure("请求操作的用户不存在");
        }
        //先插入project
        ProjectDO toAddProject=createProjectForm.toProject();
        projectMapper.insert(toAddProject);
        //再插入projectMember这一关系
        ProjectMemberDO toAddProjectMember=new ProjectMemberDO();
        toAddProjectMember.setProjectId(toAddProject.getId());
        toAddProjectMember.setRole(2);
        toAddProjectMember.setUserId(createProjectForm.getUserId());
        projectMemberMapper.insert(toAddProjectMember);
        ProjectVO ans=new ProjectVO(toAddProject.getId(),toAddProject.getName(),toAddProject.getDescription());
        return ResponseVO.buildSuccess(ans);
    }

    @Override
    public ResponseVO<List<ProjectMemberInfoForm>> getProjectMembers(int projectId) {
        ProjectMemberDOExample projectMemberDOExampleByProjectId=new ProjectMemberDOExample();
        projectMemberDOExampleByProjectId.createCriteria().andProjectIdEqualTo(projectId);
        List<ProjectMemberDO> projectMembers=projectMemberMapper.selectByExample(projectMemberDOExampleByProjectId);

        List<Integer> userIds=projectMembers.stream().map(i->i.getUserId()).collect(Collectors.toList());
        UserDOExample userDOExample=new UserDOExample();
        userDOExample.createCriteria().andIdIn(userIds);
        List<UserDO> members=userMapper.selectByExample(userDOExample);

        Map<Integer,String> names=new HashMap<>();
        for(UserDO user:members){
            names.put(user.getId(),user.getName());
        }

        List<ProjectMemberInfoForm> ans=new ArrayList<>();
        for(ProjectMemberDO member:projectMembers){
            ans.add(new ProjectMemberInfoForm(member.getProjectId(),member.getUserId(),names.get(member.getUserId()),member.getRole(),member.getId()));
        }
        return ResponseVO.buildSuccess(ans);
    }

    @Override
    public ResponseVO<Integer> getRoleOfUserInProject(int uid, int pid) {
        ProjectMemberDOExample projectMemberDOExample=new ProjectMemberDOExample();
        projectMemberDOExample.createCriteria().andProjectIdEqualTo(pid).andUserIdEqualTo(uid);
        List<ProjectMemberDO> members=projectMemberMapper.selectByExample(projectMemberDOExample);
        if(members.size()==0){
            return ResponseVO.buildFailure("该用户未找到");
        }
        return ResponseVO.buildSuccess(members.get(0).getRole());
    }

    @Override
    public ResponseVO<Boolean> addProjectMember(ProjectMemberVO projectMemberVO) {
        try {
            ProjectMemberDO toAdd = projectMemberVO.toProjectMember();
            if(projectMapper.selectByPrimaryKey(toAdd.getProjectId())==null){
                return ResponseVO.buildFailure("要加入的项目不存在");
            }
            if(userMapper.selectByPrimaryKey(toAdd.getUserId())==null){
                return ResponseVO.buildFailure("要加入的人员不存在");
            }
            ProjectMemberDOExample example=new ProjectMemberDOExample();
            example.createCriteria().andProjectIdEqualTo(toAdd.getProjectId()).andUserIdEqualTo(toAdd.getUserId());
            if(projectMemberMapper.selectByExample(example).size()!=0){
                return ResponseVO.buildFailure("要加入的人员已经处于项目中");
            }
            projectMemberMapper.insert(toAdd);
            return ResponseVO.buildSuccess(Boolean.TRUE);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildSuccess(Boolean.FALSE);
        }
    }

    @Override
    public ResponseVO<Boolean> updateRoleOfProjectMember(ProjectMemberRoleUpdateForm projectMemberRoleUpdateForm) {
        try {
            ProjectMemberDO toUpdate = projectMemberMapper.selectByPrimaryKey(projectMemberRoleUpdateForm.getRelationshipId());
            if (toUpdate == null) {
                return ResponseVO.buildFailure("未找到该用户");
            }
            toUpdate.setRole(projectMemberRoleUpdateForm.getNewRole());
            projectMemberMapper.updateByPrimaryKey(toUpdate);
            return ResponseVO.buildSuccess(Boolean.TRUE);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildSuccess(Boolean.FALSE);
        }
    }

    @Override
    public ResponseVO<Boolean> deleteProjectMember(int projectMemberRelationshipId) {
        try{
            projectMemberMapper.deleteByPrimaryKey(projectMemberRelationshipId);
            return ResponseVO.buildSuccess(Boolean.TRUE);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildSuccess(Boolean.FALSE);
        }
    }

    @Override
    public ResponseVO<List<AnnouncementVO>> getAnnouncements(int projectId) {
        AnnouncementDOExample announcementDOExample=new AnnouncementDOExample();
        announcementDOExample.createCriteria().andProjectIdEqualTo(projectId);
        List<AnnouncementDO> announcements=announcementMapper.selectByExampleWithBLOBs(announcementDOExample);
        List<AnnouncementVO> ans=announcements.stream().
                map(i->new AnnouncementVO(i.getId(),i.getProjectId(),i.getCreateTime(),i.getUpdateTime(),i.getContent()))
                .collect(Collectors.toList());
        return ResponseVO.buildSuccess(ans);
    }

    @Override
    public ResponseVO<Boolean> createAnnouncement(AnnouncementForm announcementForm) {
        try{
            AnnouncementDO toAdd=announcementForm.toAnnouncement();
            if(projectMapper.selectByPrimaryKey(toAdd.getProjectId())==null){
                return ResponseVO.buildFailure("要插入的项目不存在");
            }
            announcementMapper.insert(toAdd);
            return ResponseVO.buildSuccess(Boolean.TRUE);
        }
        catch (Exception e){
            return ResponseVO.buildSuccess(Boolean.FALSE);
        }
    }

    @Override
    public ResponseVO<AnnouncementVO> updateAnnouncement(AnnouncementVO announcementVO) {
        try {
            AnnouncementDO toUpdate = announcementMapper.selectByPrimaryKey(announcementVO.getId());
            if (toUpdate == null) {
                return ResponseVO.buildFailure("要更新的公告不存在");
            }
            toUpdate.setContent(announcementVO.getContent());
            announcementMapper.updateByPrimaryKeyWithBLOBs(toUpdate);
            AnnouncementVO ans=new AnnouncementVO(toUpdate.getId(), toUpdate.getProjectId(),toUpdate.getCreateTime(),toUpdate.getUpdateTime(),toUpdate.getContent());
            return ResponseVO.buildSuccess(ans);
        }
        catch (Exception e){
            return ResponseVO.buildFailure("更新出错");
        }
    }

    @Override
    public ResponseVO<Boolean> deleteAnnouncement(int announcementId) {
        try{
            announcementMapper.deleteByPrimaryKey(announcementId);
            return ResponseVO.buildSuccess(Boolean.TRUE);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildSuccess(Boolean.FALSE);
        }
    }

}
