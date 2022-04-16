package com.nju.projectManagement.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Toby Fu
 * @date 2022/4/16
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectIdAndUserIdForm {
    private Integer projectId;
    private Integer userId;
}
