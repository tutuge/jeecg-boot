package org.jeecg.modules.cable.mapper.dao.userCommon;

import org.jeecg.modules.cable.entity.userCommon.EcbulUnit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcbulUnitMapper {
    List<EcbulUnit> getList(EcbulUnit record);

    long getCount(EcbulUnit record);

    EcbulUnit getObject(EcbulUnit record);

    Integer insert(EcbulUnit record);

    Integer update(EcbulUnit record);

    Integer delete(EcbulUnit record);

    //getListGreaterThanSortId 获取大于指定序号的数据列表
    List<EcbulUnit> getListGreaterThanSortId(EcbulUnit record);

    //getObjectPassLengthName
    EcbulUnit getObjectPassLengthName(EcbulUnit record);

    //getLatestObject
    EcbulUnit getLatestObject(EcbulUnit record);
}
