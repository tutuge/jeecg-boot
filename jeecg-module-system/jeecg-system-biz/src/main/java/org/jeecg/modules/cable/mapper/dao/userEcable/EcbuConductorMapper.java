package org.jeecg.modules.cable.mapper.dao.userEcable;

import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.userEcable.EcbuConductor;

import java.util.List;

@Mapper
public interface EcbuConductorMapper {
    EcbuConductor getObject(EcbuConductor record);

    Integer insert(EcbuConductor ecbuConductor);

    Integer update(EcbuConductor record);

    List<EcbuConductor> getList(EcbuConductor record);

    Integer delete(EcbuConductor record);
}
