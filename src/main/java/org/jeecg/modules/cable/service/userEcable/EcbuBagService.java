package org.jeecg.modules.cable.service.userEcable;

import org.jeecg.modules.cable.entity.userEcable.EcbuBag;

import java.util.List;

public interface EcbuBagService {
    EcbuBag getObject(EcbuBag record);

    Integer insert(EcbuBag record);

    Integer update(EcbuBag record);

    List<EcbuBag> getList(EcbuBag record);

    Integer deleteByEcCompanyId(EcbuBag record);

    EcbuBag getObjectById(Integer bagId);
}
