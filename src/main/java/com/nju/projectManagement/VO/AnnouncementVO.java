package com.nju.projectManagement.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Toby Fu
 * @date 2022/2/6
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementVO {
    private Integer id;
    private Integer projectId;
    private Date createTime;
    private Date updateTime;
    private String content;
}
