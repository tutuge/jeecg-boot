package org.jeecg.modules.cable.service.userEcable;

import org.jeecg.modules.cable.entity.userEcable.EcbuSteelband;

import java.util.List;

public interface EcbuSteelbandService {
    EcbuSteelband getObject(EcbuSteelband record);

    EcbuSteelband getObjectById(Integer ecbusId);

    Integer insert(EcbuSteelband record);

    Integer update(EcbuSteelband record);

    List<EcbuSteelband> getList(EcbuSteelband record);

    Integer deleteByEcCompanyId(EcbuSteelband record);
}
