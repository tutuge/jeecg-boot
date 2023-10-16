package org.jeecg.modules.cable.service.systemEcable;

import org.jeecg.modules.cable.entity.systemEcable.EcbSteelband;

import java.util.List;

public interface EcbSteelbandService {
    List<EcbSteelband> getList(EcbSteelband record);

    List<EcbSteelband> getListStart(EcbSteelband record);

    long getCount();

    EcbSteelband getObject(EcbSteelband record);
}
