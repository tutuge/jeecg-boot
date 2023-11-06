package org.jeecg.modules.cable.service.systemEcable;

import org.jeecg.modules.cable.entity.systemEcable.EcbInsulation;

import java.util.List;

public interface EcbInsulationService {
    List<EcbInsulation> getList(EcbInsulation record);

    List<EcbInsulation> getListStart(EcbInsulation record);

    long getCount();

    EcbInsulation getObject(EcbInsulation record);
}
