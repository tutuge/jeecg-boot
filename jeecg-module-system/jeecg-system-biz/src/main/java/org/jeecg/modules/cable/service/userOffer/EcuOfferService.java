package org.jeecg.modules.cable.service.userOffer;

import org.jeecg.modules.cable.entity.userOffer.EcuOffer;

import java.util.List;

public interface EcuOfferService {
    List<EcuOffer> getList(EcuOffer record);

    long getCount(EcuOffer record);

    EcuOffer getObject(EcuOffer record);

    Integer insert(EcuOffer record);

    Integer delete(EcuOffer record);

    Integer update(EcuOffer record);
}
