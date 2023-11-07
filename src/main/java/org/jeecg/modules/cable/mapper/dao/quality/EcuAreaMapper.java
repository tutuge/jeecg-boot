package org.jeecg.modules.cable.mapper.dao.quality;

import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.quality.EcuArea;

import java.util.List;

@Mapper
public interface EcuAreaMapper {

    List<EcuArea> getList(EcuArea record);


    long getCount(EcuArea record);


    EcuArea getObject(EcuArea record);


    Integer insert(EcuArea record);

    //updateByPrimaryKeySelective
    Integer updateByPrimaryKeySelective(EcuArea record);


    Integer deleteByPrimaryKey(Integer ecuaId);

    //getListGreaterThanSortId 获取大于指定序号的数据列表
    List<EcuArea> getListGreaterThanSortId(EcuArea record);

    //getObjectPassAreaStr
    EcuArea getObjectPassAreaStr(EcuArea record);


    EcuArea getLatestObject(EcuArea record);

    //deletePassEcqulId
    Integer deletePassEcqulId(Integer ecqulId);

    void batchInsert(List<EcuArea> areas);
}
