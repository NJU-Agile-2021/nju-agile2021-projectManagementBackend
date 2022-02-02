package com.nju.projectManagement.Mapper;

import com.nju.projectManagement.DO.ProjectDO;
import com.nju.projectManagement.DO.ProjectDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProjectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProjectDO record);

    int insertSelective(ProjectDO record);

    List<ProjectDO> selectByExample(ProjectDOExample example);

    ProjectDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProjectDO record);

    int updateByPrimaryKey(ProjectDO record);

    int batchInsert(@Param("list") List<ProjectDO> list);

    int batchInsertSelective(@Param("list") List<ProjectDO> list, @Param("selective") ProjectDO.Column ... selective);
}