package com.nju.projectManagement.Mapper;

import com.nju.projectManagement.DO.AnnouncementDO;
import com.nju.projectManagement.DO.AnnouncementDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AnnouncementMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AnnouncementDO record);

    int insertSelective(AnnouncementDO record);

    List<AnnouncementDO> selectByExampleWithBLOBs(AnnouncementDOExample example);

    List<AnnouncementDO> selectByExample(AnnouncementDOExample example);

    AnnouncementDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AnnouncementDO record);

    int updateByPrimaryKeyWithBLOBs(AnnouncementDO record);

    int updateByPrimaryKey(AnnouncementDO record);

    int batchInsert(@Param("list") List<AnnouncementDO> list);

    int batchInsertSelective(@Param("list") List<AnnouncementDO> list, @Param("selective") AnnouncementDO.Column ... selective);
}