package org.jeecg.modules.cable.service.systemEcable;

import org.jeecg.modules.cable.entity.systemEcable.EcbSteelBand;

import java.util.List;

public interface EcbSteelbandService {
    List<EcbSteelBand> getList(EcbSteelBand record);

    List<EcbSteelBand> getListStart(EcbSteelBand record);

    long getCount();

    EcbSteelBand getObject(EcbSteelBand record);
}
