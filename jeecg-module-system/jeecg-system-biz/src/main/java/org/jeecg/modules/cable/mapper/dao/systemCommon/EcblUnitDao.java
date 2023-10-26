package org.jeecg.modules.cable.mapper.dao.systemCommon;

import org.jeecg.modules.cable.entity.systemCommon.EcblUnit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcblUnitDao {
    List<EcblUnit> getList(EcblUnit record);

    long getCount(EcblUnit record);

    EcblUnit getObject(EcblUnit record);

    Integer insert(EcblUnit record);

    Integer update(EcblUnit record);

    Integer delete(EcblUnit record);
}
