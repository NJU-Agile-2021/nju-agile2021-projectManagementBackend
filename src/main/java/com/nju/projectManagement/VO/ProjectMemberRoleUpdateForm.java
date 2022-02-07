package com.nju.projectManagement.VO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMemberRoleUpdateForm {
    private int relationshipId;
    /**
     * 0：普通，1：管理员
     */
    private int newRole;

    public boolean valid(){
        return newRole==0||newRole==1;
    }
}
