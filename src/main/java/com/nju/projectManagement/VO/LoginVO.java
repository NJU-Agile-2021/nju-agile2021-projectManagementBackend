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
public class LoginVO {
    private String name;
    private String pwd;
    private String email;
}
