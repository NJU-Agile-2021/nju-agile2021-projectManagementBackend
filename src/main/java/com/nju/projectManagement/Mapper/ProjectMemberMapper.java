package com.nju.projectManagement.Mapper;

import com.nju.projectManagement.DO.ProjectMemberDO;
import com.nju.projectManagement.DO.ProjectMemberDOExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProjectMemberMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProjectMemberDO record);

    int insertSelective(ProjectMemberDO record);

    List<ProjectMemberDO> selectByExample(ProjectMemberDOExample example);

    ProjectMemberDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProjectMemberDO record);

    int updateByPrimaryKey(ProjectMemberDO record);

    int batchInsert(@Param("list") List<ProjectMemberDO> list);

    int batchInsertSelective(@Param("list") List<ProjectMemberDO> list, @Param("selective") ProjectMemberDO.Column ... selective);
}