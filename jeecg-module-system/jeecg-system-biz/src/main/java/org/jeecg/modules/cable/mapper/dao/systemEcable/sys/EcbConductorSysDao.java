package org.jeecg.modules.cable.mapper.dao.systemEcable.sys;


import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.systemEcable.EcbConductor;

import java.util.List;

@Mapper
public interface EcbConductorSysDao {
    EcbConductor getObject(EcbConductor record);

    List<EcbConductor> getList(EcbConductor record);

    long getCount(EcbConductor record);

    int insert(EcbConductor record);

    int update(EcbConductor record);

    int delete(EcbConductor record);
}
