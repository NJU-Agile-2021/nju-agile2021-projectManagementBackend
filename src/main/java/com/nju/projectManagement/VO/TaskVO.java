package com.nju.projectManagement.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nju.projectManagement.DO.TaskDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author Toby Fu
 * @date 2022/2/6
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskVO {
    private Integer id;

    private String title;

    private Integer priority;

    private Integer state;

    private Integer createUserId;

    @JsonFormat(locale = "zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(locale = "zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @JsonFormat(locale = "zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date estimateCompleteTime;

    @JsonFormat(locale = "zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date completeTime;

    private Integer taskListId;

    private Integer projectId;

    private String content;

    public TaskVO(TaskDO taskDO){
        BeanUtils.copyProperties(taskDO, this);
    }
}
