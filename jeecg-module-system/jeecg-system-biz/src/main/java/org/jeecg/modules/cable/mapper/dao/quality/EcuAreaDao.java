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
    Integer insert(EcuArea record);
    //updateByPrimaryKeySelective
    Integer updateByPrimaryKeySelective(EcuArea record);
    //deleteByPrimaryKey
    Integer deleteByPrimaryKey(Integer ecuaId);
    //getListGreaterThanSortId 获取大于指定序号的数据列表
    List<EcuArea> getListGreaterThanSortId(EcuArea record);
    //getObjectPassAreaStr
    EcuArea getObjectPassAreaStr(EcuArea record);
    //getLatestObject
    EcuArea getLatestObject(EcuArea record);
    //deletePassEcqulId
    Integer deletePassEcqulId(Integer ecqulId);

    void batchInsert(List<EcuArea> areas);
}
