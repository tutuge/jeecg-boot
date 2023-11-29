package org.jeecg.modules.cable.service.userEcable;

import org.jeecg.modules.cable.entity.userEcable.EcbuSteelBand;

import java.util.List;

public interface EcbuSteelbandService {
    EcbuSteelBand getObject(EcbuSteelBand record);

    EcbuSteelBand getObjectById(Integer ecbusId);

    Integer insert(EcbuSteelBand record);

    Integer update(EcbuSteelBand record);

    List<EcbuSteelBand> getList(EcbuSteelBand record);

    Integer deleteByEcCompanyId(EcbuSteelBand record);
}
