package com.nju.projectManagement.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author WangYuxiao
 * @date 2022/4/18 20:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleInfoVO {

    private Integer id;

    private Date time;

    private Integer projectId;

    private String projectName;

    private String projectDescription;

    private Integer userId;

    private Integer taskId;

    private String taskTitle;

    private String taskContent;

    private Integer taskPriority;

    private Integer taskState;

}
