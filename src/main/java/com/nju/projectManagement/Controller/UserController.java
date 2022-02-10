package com.nju.projectManagement.Controller;

import com.nju.projectManagement.Service.BL.UserService;
import com.nju.projectManagement.VO.LoginVO;
import com.nju.projectManagement.VO.ResponseVO;
import com.nju.projectManagement.VO.UserIdAndNameVO;
import com.nju.projectManagement.VO.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Toby Fu
 * @date 2022/2/3
 **/
@Tag(name = "User",description = "User API")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @Operation(summary = "login")
    @PostMapping(value = "/login", consumes={ "application/json"}, produces={ "application/json"})
    public ResponseVO<UserVO> login(@RequestBody @NotNull(message = "Invalid Parameter") LoginVO loginVO) {
        return userService.login(loginVO);
    }

    @Operation(summary = "register")
    @PostMapping(value = "/register",consumes={ "application/json"},produces={ "application/json" })
    public ResponseVO<UserVO> register(@RequestBody @Valid @NotNull(message = "Invalid parameter") UserVO userVO) {
        return userService.register(userVO);
    }

    @Operation(summary = "searchUsersByNameOrEmail")
    @GetMapping(value = "/searchUsersByNameOrEmail")
    public ResponseVO<List<UserIdAndNameVO>> searchUsersByNameOrEmail(@RequestParam @NotNull(message = "Invalid parameter") String userNameOrEmail) {
        return userService.searchUsersByNameOrEmail(userNameOrEmail);
    }
}
