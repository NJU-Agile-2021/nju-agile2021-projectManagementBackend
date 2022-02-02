package com.nju.projectManagement.Mapper;

import com.nju.projectManagement.DO.UserDO;
import com.nju.projectManagement.DO.UserDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int insert(UserDO record);

    int insertSelective(UserDO record);

    List<UserDO> selectByExample(UserDOExample example);

    int batchInsert(@Param("list") List<UserDO> list);

    int batchInsertSelective(@Param("list") List<UserDO> list, @Param("selective") UserDO.Column ... selective);
}