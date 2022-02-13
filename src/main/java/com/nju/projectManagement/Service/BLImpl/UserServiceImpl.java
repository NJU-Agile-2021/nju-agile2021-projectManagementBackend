package com.nju.projectManagement.Service.BLImpl;

import cn.hutool.crypto.digest.DigestUtil;
import com.nju.projectManagement.DO.UserDO;
import com.nju.projectManagement.DO.UserDOExample;
import com.nju.projectManagement.Mapper.UserMapper;
import com.nju.projectManagement.Service.BL.UserService;
import com.nju.projectManagement.Service.BL.UserServiceForBl;
import com.nju.projectManagement.VO.LoginVO;
import com.nju.projectManagement.VO.ResponseVO;
import com.nju.projectManagement.VO.UserIdAndNameVO;
import com.nju.projectManagement.VO.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Toby Fu
 * @date 2022/2/7
 **/
@Service
public class UserServiceImpl implements UserService, UserServiceForBl {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseVO<UserVO> login(LoginVO loginVO) {
        boolean isValid = false;
        UserVO userVO = new UserVO();
        // Check username if present
        if (loginVO.getName() != null) {
            List<UserDO> nameRes = getUsersByName(loginVO.getName());
            UserDO userDO = nameRes.stream().filter(x -> DigestUtil.bcryptCheck(loginVO.getPwd(), x.getPwd())).findAny().orElse(null);
            if (userDO != null) {
                BeanUtils.copyProperties(userDO, userVO);
                isValid = true;
            }
    }
        // Check email if present
        else if (loginVO.getEmail() != null) {
            List<UserDO> emailRes = getUsersByEmail(loginVO.getEmail());
            UserDO userDO = emailRes.stream().filter(x -> x.getEmail().equals(loginVO.getEmail())).findAny().orElse(null);
            if (userDO != null) {
                BeanUtils.copyProperties(userDO, userVO);
                isValid = true;
            }
        }
        // Clear password
        userVO.setPwd(null);
        return isValid ? ResponseVO.buildSuccess(userVO) : new ResponseVO<>(false, "Wrong username or password", null);

    }

    @Override
    public ResponseVO<UserVO> register(UserVO userVO) {
        // Check user's name and email
        List<UserDO> userList = getExactMatchUsersByNameOrEmail(userVO.getName(), userVO.getEmail());
        if (userList != null && !userList.isEmpty()) {
            return new ResponseVO<>(false, "Username or Email already exists", null);
        }
        // encrypt password and insert
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userVO, userDO);
        userDO.setPwd(DigestUtil.bcrypt(userVO.getPwd()));
        if (userMapper.insert(userDO) == 1) {
            userDO.setPwd(null);
            BeanUtils.copyProperties(userDO, userVO);
            return ResponseVO.buildSuccess(userVO);
        }
        return new ResponseVO<>(false, "Register failed", null);
    }

    @Override
    public ResponseVO<List<UserIdAndNameVO>> searchUsersByNameOrEmail(String userNameOrEmail) {
        // search username and email
        String nameOrEmail = "%" + userNameOrEmail + "%";
        return ResponseVO.buildSuccess(getMatchUsersByNameOrEmail(nameOrEmail).stream().map(x -> {
            UserIdAndNameVO userIdAndNameVO = new UserIdAndNameVO();
            BeanUtils.copyProperties(x, userIdAndNameVO);
            return userIdAndNameVO;
        }).collect(Collectors.toList()));
    }

    @Override
    public ResponseVO<Boolean> checkUserEmail(String email) {
        return ResponseVO.buildSuccess(!getUsersByEmail(email).isEmpty());
    }

    @Override
    public ResponseVO<Boolean> checkUserName(String name) {
        return null;
    }

    @Override
    public List<UserIdAndNameVO> getBatchUserSimpleInfoById(List<Integer> idList) {
        // mybatis generator's query doesn't accept array size of 0
        if (idList.isEmpty()) {
            return new ArrayList<>();
        }
        // search by user id list
        UserDOExample example = new UserDOExample();
        example.createCriteria().andIdIn(idList);
        // refactor to UserIdAndNameVO list
        return userMapper.selectByExample(example).stream().map(x -> {
            UserIdAndNameVO userIdAndNameVO = new UserIdAndNameVO();
            BeanUtils.copyProperties(x, userIdAndNameVO);
            return userIdAndNameVO;
        }).collect(Collectors.toList());
    }

    /**
     * 封装方法，根据用户名查询用户
     * @param name 用户名
     * @return 用户列表
     */
    private List<UserDO> getUsersByName(String name) {
        UserDOExample userExampleByName = new UserDOExample();
        userExampleByName.createCriteria().andNameEqualTo(name);
        return userMapper.selectByExample(userExampleByName);
    }

    /**
     * 封装方法，根据用户邮箱查询用户
     * @param email 邮箱
     * @return 用户列表
     */
    private List<UserDO> getUsersByEmail(String email) {
        UserDOExample userExampleByEmail = new UserDOExample();
        userExampleByEmail.createCriteria().andEmailEqualTo(email);
        return userMapper.selectByExample(userExampleByEmail);
    }

    /**
     * 封装方法，根据用户用户名或邮箱精确查询用户
     * @param name 用户名
     * @param email 邮箱
     * @return 用户列表
     */
    private List<UserDO> getExactMatchUsersByNameOrEmail(String name, String email) {
        UserDOExample userExample = new UserDOExample();
        userExample.or().andNameEqualTo(name);
        userExample.or().andEmailEqualTo(email);
        return userMapper.selectByExample(userExample);
    }

    /**
     * 封装方法，根据用户用户名或邮箱模糊查询用户
     * @param nameOrEmail 用户名或邮箱
     * @return 用户列表
     */
    private List<UserDO> getMatchUsersByNameOrEmail(String nameOrEmail) {
        UserDOExample userExample = new UserDOExample();
        userExample.or().andNameLike(nameOrEmail);
        userExample.or().andEmailLike(nameOrEmail);
        return userMapper.selectByExample(userExample);
    }
}
