package com.nju.projectManagement.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Toby Fu
 * @date 2022/2/6
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTaskVO {
    private TaskVO baseTaskInfo;
    private String projectName;
    private List<UserIdAndNameVO> assignUsers;
}
