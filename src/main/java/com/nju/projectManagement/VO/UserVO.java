package com.nju.projectManagement.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * @author Toby Fu
 * @date 2022/2/3
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {

    private Integer id;

    @NotNull(message = "Empty username")
    private String name;

    @Email(message = "Invalid email format")
    @NotNull(message = "Empty email")
    private String email;

    @NotNull(message = "Empty password")
    private String pwd;
}
