package com.nju.projectManagement.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Toby Fu
 * @date 2022/4/16
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyFilePermissionVO {
    private Integer userId;
    private Integer projectId;
    private Integer fileId;
    private Integer permission;
}
