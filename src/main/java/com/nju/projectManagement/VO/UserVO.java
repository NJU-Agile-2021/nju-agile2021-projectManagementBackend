package com.nju.projectManagement.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author Toby Fu
 * @date 2022/2/3
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {
    private String id;
    @NotNull(message = "Empty username")
    private String name;
    @NotNull(message = "Empty email")
    private String email;
    @NotNull(message = "Empty password")
    private String pwd;
}
