package com.nju.projectManagement.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Toby Fu
 * @date 2022/4/16
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectFileInfoVO {
    private Integer id;

    private Integer projectId;

    private String fileName;

    private Integer permission;

    private Integer uploadUserId;

    private Date uploadTime;

}
