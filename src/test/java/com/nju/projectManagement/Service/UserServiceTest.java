package com.nju.projectManagement.Service;

import cn.hutool.crypto.digest.DigestUtil;
import com.nju.projectManagement.Const.Constants;
import com.nju.projectManagement.DO.UserDO;
import com.nju.projectManagement.DO.UserDOExample;
import com.nju.projectManagement.Mapper.UserMapper;
import com.nju.projectManagement.Service.BLImpl.UserServiceImpl;
import com.nju.projectManagement.VO.LoginVO;
import com.nju.projectManagement.VO.ResponseVO;
import com.nju.projectManagement.VO.UserIdAndNameVO;
import com.nju.projectManagement.VO.UserVO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

/**
 * @author WangYuxiao
 * @date 2022/2/8 11:52
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    UserMapper userMapper;

    @InjectMocks
    UserServiceImpl userService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void loginWithEmptyNameAndEmailTest() {
        LoginVO loginVO = new LoginVO(null, Constants.PASSWORD, null);
        ResponseVO<UserVO> response = userService.login(loginVO);
        ResponseVO<UserVO> expected = new ResponseVO<>(false, "Wrong username or password", null);
        Assert.assertEquals(expected, response);
    }

    @Test
    public void loginWithValidNameAndPasswordTest() {
        LoginVO loginVO = new LoginVO(Constants.NAME_1, Constants.PASSWORD, null);
        UserDO userDO = new UserDO();
        userDO.setId(1);
        userDO.setName(Constants.NAME_1);
        userDO.setEmail(Constants.VALID_EMAIL_1);
        userDO.setPwd(DigestUtil.bcrypt(Constants.PASSWORD));
        List<UserDO> userList = new ArrayList<>();
        userList.add(userDO);

        Mockito.when(userMapper.selectByExample(any(UserDOExample.class))).thenReturn(userList);

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDO, userVO);
        userVO.setPwd(null);

        ResponseVO<UserVO> response = userService.login(loginVO);
        ResponseVO<UserVO> expected = ResponseVO.buildSuccess(userVO);
        Assert.assertEquals(expected, response);
    }

    @Test
    public void registerWithDuplicatedEmailTest() {
        UserVO userVO = new UserVO(null, Constants.NAME_1, Constants.VALID_EMAIL_1, Constants.PASSWORD);
        List<UserDO> userList = new ArrayList<>();
        UserDO userDO = new UserDO();
        userDO.setName(Constants.NAME_2);
        userDO.setPwd(Constants.PASSWORD);
        userDO.setEmail(Constants.VALID_EMAIL_1);
        userList.add(userDO);
        Mockito.when(userMapper.selectByExample(any(UserDOExample.class))).thenReturn(userList);

        ResponseVO<UserVO> response = userService.register(userVO);
        ResponseVO<UserVO> expected = new ResponseVO<>(false, "Username or Email already exists", null);
        Assert.assertEquals(expected, response);
    }

    @Test
    public void registerWithDuplicatedNameTest() {
        UserVO userVO = new UserVO(null, Constants.NAME_1, Constants.VALID_EMAIL_1, Constants.PASSWORD);
        List<UserDO> userList = new ArrayList<>();
        UserDO userDO = new UserDO();
        userDO.setName(Constants.NAME_1);
        userDO.setPwd(Constants.PASSWORD);
        userDO.setEmail(Constants.VALID_EMAIL_2);
        userList.add(userDO);
        Mockito.when(userMapper.selectByExample(any(UserDOExample.class))).thenReturn(userList);

        ResponseVO<UserVO> response = userService.register(userVO);
        ResponseVO<UserVO> expected = new ResponseVO<>(false, "Username or Email already exists", null);
        Assert.assertEquals(expected, response);
    }

    @Test
    public void registerSuccessTest() {
        UserVO userVO = new UserVO(null, Constants.NAME_1, Constants.VALID_EMAIL_1, Constants.PASSWORD);
        List<UserDO> userList = new ArrayList<>();
        Mockito.when(userMapper.selectByExample(any(UserDOExample.class))).thenReturn(userList);
        Mockito.when(userMapper.insert(any(UserDO.class))).thenReturn(1);

        ResponseVO<UserVO> response = userService.register(userVO);
        ResponseVO<UserVO> expected = new ResponseVO<>(true, "success", userVO);
        Assert.assertEquals(expected, response);
    }

    @Test
    public void databaseInsertErrorTest() {
        UserVO userVO = new UserVO(null, Constants.NAME_1, Constants.VALID_EMAIL_1, Constants.PASSWORD);
        List<UserDO> userList = new ArrayList<>();
        Mockito.when(userMapper.selectByExample(any(UserDOExample.class))).thenReturn(userList);
        Mockito.when(userMapper.insert(any(UserDO.class))).thenReturn(0);

        ResponseVO<UserVO> response = userService.register(userVO);
        ResponseVO<UserVO> expected = new ResponseVO<>(false, "Register failed", null);
        Assert.assertEquals(expected, response);
    }

    @Test
    public void searchUsersWithEmptyResultTest() {
        List<UserDO> userList = new ArrayList<>();
        Mockito.when(userMapper.selectByExample(any(UserDOExample.class))).thenReturn(userList);

        ResponseVO<List<UserIdAndNameVO>> response = userService.searchUsersByNameOrEmail("test");
        ResponseVO<List<UserIdAndNameVO>> expected = new ResponseVO<>(true, "success", new ArrayList<>());
        Assert.assertEquals(expected, response);
    }

    @Test
    public void searchUsersWithResultTest() {
        UserVO userVO = new UserVO(1, Constants.NAME_1, Constants.VALID_EMAIL_1, Constants.PASSWORD);
        UserDO user = new UserDO();
        BeanUtils.copyProperties(userVO, user);
        List<UserDO> userList = new ArrayList<>();
        userList.add(user);
        Mockito.when(userMapper.selectByExample(any(UserDOExample.class))).thenReturn(userList);

        UserIdAndNameVO userIdAndNameVO = new UserIdAndNameVO(Constants.NAME_1, 1);
        List<UserIdAndNameVO> userRetList = new ArrayList<>();
        userRetList.add(userIdAndNameVO);
        ResponseVO<List<UserIdAndNameVO>> response = userService.searchUsersByNameOrEmail("test");
        ResponseVO<List<UserIdAndNameVO>> expected = new ResponseVO<>(true, "success", userRetList);
        Assert.assertEquals(expected, response);
    }
}
