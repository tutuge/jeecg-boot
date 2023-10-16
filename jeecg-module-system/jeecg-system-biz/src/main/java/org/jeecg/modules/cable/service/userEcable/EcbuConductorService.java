package org.jeecg.modules.cable.service.userEcable;

import org.jeecg.modules.cable.entity.userEcable.EcbuConductor;

import java.util.List;

public interface EcbuConductorService {
    EcbuConductor getObject(EcbuConductor record);

    int insert(EcbuConductor record);

    int update(EcbuConductor record);

    List<EcbuConductor> getList(EcbuConductor record);

    int delete(EcbuConductor record);
}
