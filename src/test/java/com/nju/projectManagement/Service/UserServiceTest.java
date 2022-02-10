package com.nju.projectManagement.Service;

import com.nju.projectManagement.Const.Constants;
import com.nju.projectManagement.DO.UserDO;
import com.nju.projectManagement.DO.UserDOExample;
import com.nju.projectManagement.Mapper.UserMapper;
import com.nju.projectManagement.Service.BLImpl.UserServiceImpl;
import com.nju.projectManagement.VO.ResponseVO;
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
        Assert.assertEquals(response, expected);
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
        Assert.assertEquals(response, expected);
    }

    @Test
    public void registerSuccessTest() {
        UserVO userVO = new UserVO(null, Constants.NAME_1, Constants.VALID_EMAIL_1, Constants.PASSWORD);
        List<UserDO> userList = new ArrayList<>();
        Mockito.when(userMapper.selectByExample(any(UserDOExample.class))).thenReturn(userList);
        Mockito.when(userMapper.insert(any(UserDO.class))).thenReturn(1);

        ResponseVO<UserVO> response = userService.register(userVO);
        Assert.assertEquals(response.getSuccess(), true);
        Assert.assertEquals(response.getMessage(), "success");
        Assert.assertEquals(response.getContent(), userVO);
    }
}
