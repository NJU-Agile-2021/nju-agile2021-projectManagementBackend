package com.nju.projectManagement.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author WangYuxiao
 * @date 2022/4/18 15:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleForm {

    @NotNull(message = "project id can't be null")
    private Integer projectId;

    @NotNull(message = "user id can't be null")
    private Integer userId;

    @NotNull(message = "task id can't be null")
    private Integer taskId;

    @JsonFormat(locale = "zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "schedule time can't be null")
    private Date time;
}
