package com.nju.projectManagement.Service.BL;

import com.nju.projectManagement.VO.LoginVO;
import com.nju.projectManagement.VO.ResponseVO;
import com.nju.projectManagement.VO.UserIdAndNameVO;
import com.nju.projectManagement.VO.UserVO;

import java.util.List;


/**
 * @author Toby Fu
 * @date 2022/2/7
 **/
public interface UserService {
    /**
     * 用户登录
     * @param loginVO 登录表单，填写姓名或邮箱，及密码
     * @return 用户信息
     */
    ResponseVO<UserVO> login(LoginVO loginVO);

    /**
     * 用户注册
     * @param userVO 用户注册表单
     * @return 用户信息
     */
    ResponseVO<UserVO> register(UserVO userVO);

    /**
     * 根据用户名或邮箱搜索用户
     * @param userNameOrEmail 用户名或邮箱
     * @return 用户信息列表
     */
    ResponseVO<List<UserIdAndNameVO>> searchUsersByNameOrEmail(String userNameOrEmail);

    /**
     * 检查用户邮箱是否已经被使用
     * @param email 邮箱
     * @return 是否使用
     */
    ResponseVO<Boolean> checkUserEmail(String email);

    /**
     * 检查用户名是否已被使用
     * @param name 用户名
     * @return 是否使用
     */
    ResponseVO<Boolean> checkUserName(String name);
}
