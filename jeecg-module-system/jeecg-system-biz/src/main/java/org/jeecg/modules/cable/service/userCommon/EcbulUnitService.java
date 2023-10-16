package org.jeecg.modules.cable.service.userCommon;

import org.jeecg.modules.cable.entity.userCommon.EcbulUnit;

import java.util.List;

public interface EcbulUnitService {
    //getList
    List<EcbulUnit> getList(EcbulUnit record);

    //getCount
    long getCount(EcbulUnit record);

    //getObject
    EcbulUnit getObject(EcbulUnit record);

    //insert
    int insert(EcbulUnit record);

    //update
    int update(EcbulUnit record);

    int delete(EcbulUnit record);

    //getListGreaterThanSortId 获取大于指定序号的数据列表
    List<EcbulUnit> getListGreaterThanSortId(EcbulUnit record);

    //getObjectPassLengthName
    EcbulUnit getObjectPassLengthName(EcbulUnit record);

    //getLatestObject
    EcbulUnit getLatestObject(EcbulUnit record);
}
