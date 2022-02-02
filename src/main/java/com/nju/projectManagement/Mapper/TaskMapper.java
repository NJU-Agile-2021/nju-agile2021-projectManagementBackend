package com.nju.projectManagement.Mapper;

import com.nju.projectManagement.DO.TaskDO;
import com.nju.projectManagement.DO.TaskDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TaskMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskDO record);

    int insertSelective(TaskDO record);

    List<TaskDO> selectByExampleWithBLOBs(TaskDOExample example);

    List<TaskDO> selectByExample(TaskDOExample example);

    TaskDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskDO record);

    int updateByPrimaryKeyWithBLOBs(TaskDO record);

    int updateByPrimaryKey(TaskDO record);

    int batchInsert(@Param("list") List<TaskDO> list);

    int batchInsertSelective(@Param("list") List<TaskDO> list, @Param("selective") TaskDO.Column ... selective);
}