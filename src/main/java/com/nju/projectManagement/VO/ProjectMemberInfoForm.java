package com.nju.projectManagement.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Toby Fu
 * @date 2022/2/6
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectMemberInfoForm {
    private Integer projectId;
    private Integer userId;
    private String name;
    private Integer role;
    private Integer relationshipId;
}
