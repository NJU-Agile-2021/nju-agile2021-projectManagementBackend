package com.nju.projectManagement.Service.BL;

import com.nju.projectManagement.VO.UserIdAndNameVO;

import java.util.List;

/**
 * @author WangYuxiao
 * @date 2022/2/9 20:51
 */
public interface UserServiceForBl {

    /**
     * 批量查询用户id与姓名信息
     * @param idList 用户id列表
     * @return 用户信息列表
     */
    List<UserIdAndNameVO> getBatchUserSimpleInfoById(List<Integer> idList);

}
