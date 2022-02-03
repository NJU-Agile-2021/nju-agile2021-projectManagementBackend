package com.nju.projectManagement.Controller;

import com.nju.projectManagement.VO.LoginVO;
import com.nju.projectManagement.VO.ResponseVO;
import com.nju.projectManagement.VO.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Toby Fu
 * @date 2022/2/3
 **/
@Tag(name = "User",description = "User API")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Operation(summary = "login")
    @PostMapping(value = "/login", consumes={ "application/json"}, produces={ "application/json"})
    public ResponseVO<LoginVO> login(@RequestBody LoginVO loginVO) {
        return null;
    }

    @Operation(summary = "register")
    @PostMapping(value = "/register",consumes={ "application/json"},produces={ "application/json" })
    public ResponseVO<UserVO> register(@RequestBody UserVO userVO) {
        return null;
    }
}
