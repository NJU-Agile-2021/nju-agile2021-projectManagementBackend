package com.nju.projectManagement.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Toby Fu
 * @date 2022/2/3
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {
    private String name;
    private String email;
    private String pwd;
}
