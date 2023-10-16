package org.jeecg.modules.cable.mapper.dao.userEcable;

import org.jeecg.modules.cable.entity.userEcable.EcbuConductor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcbuConductorDao {
    EcbuConductor getObject(EcbuConductor record);

    int insert(EcbuConductor ecbuConductor);

    int update(EcbuConductor record);

    List<EcbuConductor> getList(EcbuConductor record);

    int delete(EcbuConductor record);
}
