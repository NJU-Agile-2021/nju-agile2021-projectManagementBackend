package com.nju.projectManagement.VO;

import com.nju.projectManagement.DO.ProjectMemberDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Toby Fu
 * @date 2022/2/6
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMemberVO {
    private Integer projectId;
    private Integer userId;
    /**
     *0：普通，1：管理员,2:创建者
     */
    private Integer role;

    public ProjectMemberDO toProjectMember(){
        ProjectMemberDO ans=new ProjectMemberDO();
        ans.setProjectId(projectId);
        ans.setUserId(userId);
        ans.setRole(role);
        return ans;
    }
}
