package com.nju.projectManagement.Service.BLImpl;

import com.nju.projectManagement.DO.UserDO;
import com.nju.projectManagement.DO.UserDOExample;
import com.nju.projectManagement.Mapper.UserMapper;
import com.nju.projectManagement.Service.BL.UserService;
import com.nju.projectManagement.VO.LoginVO;
import com.nju.projectManagement.VO.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Toby Fu
 * @date 2022/2/7
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public ResponseVO<LoginVO> login(LoginVO loginVO) {
        UserDOExample userDOExampleByName = new UserDOExample();
        userDOExampleByName.createCriteria().andNameEqualTo(loginVO.getName());
        UserDOExample userDOExampleByEmail = new UserDOExample();
        userDOExampleByEmail.createCriteria().andEmailEqualTo(loginVO.getEmail());
        List<UserDO> nameRes = userMapper.selectByExample(userDOExampleByName);
        List<UserDO> emailRes = userMapper.selectByExample(userDOExampleByEmail);

        if(nameRes.size()>1 || emailRes.size()>1){
            return null;
        }
        if(nameRes.size()==0 && emailRes.size()==0){
            return null;
        }

        UserDO userDO = nameRes.size()==0? emailRes.get(0):nameRes.get(0);
        if(!userDO.getPwd().equals(loginVO.getPwd())){
            return null;
        }

        //todo
        return null;
    }
}
