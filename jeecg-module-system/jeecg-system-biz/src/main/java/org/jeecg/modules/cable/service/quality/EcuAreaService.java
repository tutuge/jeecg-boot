package org.jeecg.modules.cable.service.quality;

import org.jeecg.modules.cable.entity.quality.EcuArea;

import java.util.List;

public interface EcuAreaService {
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
    //getObjectPassName
    EcuArea getObjectPassAreaStr(EcuArea record);
    //getLatestObject
    EcuArea getLatestObject(EcuArea record);
    //deletePassEcqulId
    int deletePassEcqulId(int ecqulId);
}
