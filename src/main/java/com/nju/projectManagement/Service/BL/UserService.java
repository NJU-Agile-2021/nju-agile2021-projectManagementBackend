package com.nju.projectManagement.Service.BL;

import com.nju.projectManagement.VO.LoginVO;
import com.nju.projectManagement.VO.ResponseVO;


/**
 * @author Toby Fu
 * @date 2022/2/7
 **/
public interface UserService {
    ResponseVO<LoginVO> login( LoginVO loginVO);
}
