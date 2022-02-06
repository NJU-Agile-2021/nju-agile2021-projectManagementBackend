package com.nju.projectManagement.VO;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class AnnouncementForm {
    private Integer id;
    private Integer projectId;
    private String content;
}
