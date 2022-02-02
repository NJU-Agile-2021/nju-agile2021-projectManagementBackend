package com.nju.projectManagement.Mapper;

import com.nju.projectManagement.DO.TaskBelongDO;
import com.nju.projectManagement.DO.TaskBelongDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TaskBelongMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskBelongDO record);

    int insertSelective(TaskBelongDO record);

    List<TaskBelongDO> selectByExample(TaskBelongDOExample example);

    TaskBelongDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskBelongDO record);

    int updateByPrimaryKey(TaskBelongDO record);

    int batchInsert(@Param("list") List<TaskBelongDO> list);

    int batchInsertSelective(@Param("list") List<TaskBelongDO> list, @Param("selective") TaskBelongDO.Column ... selective);
}