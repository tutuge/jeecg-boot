package org.jeecg.modules.cable.mapper.dao.quality;

import org.jeecg.modules.cable.entity.quality.EcuArea;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcuAreaDao {
    //getList
    List<EcuArea> getList(EcuArea record);
    //getCount
    long getCount(EcuArea record);
    //getObject
    EcuArea getObject(EcuArea record);
    //insert
    int insert(EcuArea record);
    //updateByPrimaryKeySelective
    int updateByPrimaryKeySelective(EcuArea record);
    //deleteByPrimaryKey
    int deleteByPrimaryKey(int ecuaId);
    //getListGreaterThanSortId 获取大于指定序号的数据列表
    List<EcuArea> getListGreaterThanSortId(EcuArea record);
    //getObjectPassAreaStr
    EcuArea getObjectPassAreaStr(EcuArea record);
    //getLatestObject
    EcuArea getLatestObject(EcuArea record);
    //deletePassEcqulId
    int deletePassEcqulId(int ecqulId);
}
