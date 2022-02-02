package com.nju.projectManagement.Mapper;

import com.nju.projectManagement.DO.TaskLabelDO;
import com.nju.projectManagement.DO.TaskLabelDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TaskLabelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskLabelDO record);

    int insertSelective(TaskLabelDO record);

    List<TaskLabelDO> selectByExample(TaskLabelDOExample example);

    TaskLabelDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskLabelDO record);

    int updateByPrimaryKey(TaskLabelDO record);

    int batchInsert(@Param("list") List<TaskLabelDO> list);

    int batchInsertSelective(@Param("list") List<TaskLabelDO> list, @Param("selective") TaskLabelDO.Column ... selective);
}