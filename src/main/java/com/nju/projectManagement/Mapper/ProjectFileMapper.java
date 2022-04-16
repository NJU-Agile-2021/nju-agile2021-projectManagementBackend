package com.nju.projectManagement.Mapper;

import com.nju.projectManagement.DO.ProjectFileDO;
import com.nju.projectManagement.DO.ProjectFileDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProjectFileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProjectFileDO record);

    int insertSelective(ProjectFileDO record);

    List<ProjectFileDO> selectByExample(ProjectFileDOExample example);

    ProjectFileDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProjectFileDO record);

    int updateByPrimaryKey(ProjectFileDO record);

    int batchInsert(@Param("list") List<ProjectFileDO> list);

    int batchInsertSelective(@Param("list") List<ProjectFileDO> list, @Param("selective") ProjectFileDO.Column ... selective);
}