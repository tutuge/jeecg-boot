package org.jeecg.modules.cable.service.userOffer;

import org.jeecg.modules.cable.entity.userOffer.EcuoCore;

import java.util.List;

public interface EcuoCoreService {
    List<EcuoCore> getList(EcuoCore record);

    EcuoCore getObject(EcuoCore record);

    Integer insert(EcuoCore record);

    Integer update(EcuoCore record);
}
