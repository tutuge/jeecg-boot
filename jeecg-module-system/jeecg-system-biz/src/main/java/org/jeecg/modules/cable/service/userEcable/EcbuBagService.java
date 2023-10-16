package org.jeecg.modules.cable.service.userEcable;

import org.jeecg.modules.cable.entity.userEcable.EcbuBag;

import java.util.List;

public interface EcbuBagService {
    EcbuBag getObject(EcbuBag record);

    int insert(EcbuBag record);

    int update(EcbuBag record);

    List<EcbuBag> getList(EcbuBag record);

    int delete(EcbuBag record);
}
