package com.nju.projectManagement.Controller;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;
import com.nju.projectManagement.Service.BL.UserService;
import com.nju.projectManagement.VO.ResponseVO;
import com.nju.projectManagement.VO.UserVO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.nju.projectManagement.Const.Constants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * @author WangYuxiao
 * @date 2022/2/8 11:52
 */
public class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void registerSuccessTest() throws Exception {
        UserVO userVO = new UserVO(null, NAME, VALID_EMAIL_1, PASSWORD);
        UserVO userVORet = new UserVO(null, NAME, VALID_EMAIL_1, null);
        Mockito.when(userService.register(any(UserVO.class))).thenReturn(ResponseVO.buildSuccess(userVORet));
        MockMvc mockMvc = standaloneSetup(userController).build();
        MvcResult mvcResult = mockMvc
                .perform(
                        post("/api/user/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JSONUtil.toJsonStr(userVO))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        ResponseVO<UserVO> responseVO = JSONUtil.toBean(response, new TypeReference<ResponseVO<UserVO>>(){}, true);
        Assert.assertEquals(responseVO, ResponseVO.buildSuccess(userVORet));
    }
}
