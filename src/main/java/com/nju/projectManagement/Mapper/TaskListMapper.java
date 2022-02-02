package com.nju.projectManagement.Mapper;

import com.nju.projectManagement.DO.TaskListDO;
import com.nju.projectManagement.DO.TaskListDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TaskListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskListDO record);

    int insertSelective(TaskListDO record);

    List<TaskListDO> selectByExample(TaskListDOExample example);

    TaskListDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskListDO record);

    int updateByPrimaryKey(TaskListDO record);

    int batchInsert(@Param("list") List<TaskListDO> list);

    int batchInsertSelective(@Param("list") List<TaskListDO> list, @Param("selective") TaskListDO.Column ... selective);
}