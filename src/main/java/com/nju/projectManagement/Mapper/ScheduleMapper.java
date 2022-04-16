package com.nju.projectManagement.Mapper;

import com.nju.projectManagement.DO.ScheduleDO;
import com.nju.projectManagement.DO.ScheduleDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ScheduleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ScheduleDO record);

    int insertSelective(ScheduleDO record);

    List<ScheduleDO> selectByExample(ScheduleDOExample example);

    ScheduleDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ScheduleDO record);

    int updateByPrimaryKey(ScheduleDO record);

    int batchInsert(@Param("list") List<ScheduleDO> list);

    int batchInsertSelective(@Param("list") List<ScheduleDO> list, @Param("selective") ScheduleDO.Column ... selective);
}