package com.nju.projectManagement.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Toby Fu
 * @date 2022/2/6
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskFinishedInPeriodForm {
    @NotNull(message = "Empty project id")
    private Integer projectId;
    @JsonFormat(locale = "zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "Empty start time")
    private Date startTime;
    @JsonFormat(locale = "zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "Empty end time")
    private Date endTime;
}
