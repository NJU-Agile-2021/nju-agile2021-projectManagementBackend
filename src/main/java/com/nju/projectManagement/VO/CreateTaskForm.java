package com.nju.projectManagement.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Toby Fu
 * @date 2022/2/6
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTaskForm {
    private int projectId;
    private int userId;
    private String title;
}
