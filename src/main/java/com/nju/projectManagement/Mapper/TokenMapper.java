package com.nju.projectManagement.Mapper;

import com.nju.projectManagement.DO.TokenDO;
import com.nju.projectManagement.DO.TokenDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TokenMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TokenDO record);

    int insertSelective(TokenDO record);

    List<TokenDO> selectByExample(TokenDOExample example);

    TokenDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TokenDO record);

    int updateByPrimaryKey(TokenDO record);

    int batchInsert(@Param("list") List<TokenDO> list);

    int batchInsertSelective(@Param("list") List<TokenDO> list, @Param("selective") TokenDO.Column ... selective);
}