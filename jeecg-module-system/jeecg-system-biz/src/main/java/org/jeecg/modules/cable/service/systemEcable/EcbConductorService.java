package org.jeecg.modules.cable.service.systemEcable;

import org.jeecg.modules.cable.entity.systemEcable.EcbConductor;

import java.util.List;

public interface EcbConductorService {
    List<EcbConductor> getList(EcbConductor record);

    List<EcbConductor> getListStart(EcbConductor record);

    long getCount();

    EcbConductor getObject(EcbConductor record);
}
