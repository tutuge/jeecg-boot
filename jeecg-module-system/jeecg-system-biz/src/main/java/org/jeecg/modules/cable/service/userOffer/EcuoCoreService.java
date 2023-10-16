package org.jeecg.modules.cable.service.userOffer;

import org.jeecg.modules.cable.entity.userOffer.EcuoCore;

import java.util.List;

public interface EcuoCoreService {
    List<EcuoCore> getList(EcuoCore record);

    EcuoCore getObject(EcuoCore record);

    int insert(EcuoCore record);

    int update(EcuoCore record);
}
