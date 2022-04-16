package com.nju.projectManagement.VO;

import com.nju.projectManagement.DO.ProjectFileDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

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

    public ProjectFileInfoVO(ProjectFileDO projectFileDO) {
        BeanUtils.copyProperties(projectFileDO, this);
        this.fileName = this.fileName.substring(0, this.fileName.lastIndexOf("-"));
    }
}
