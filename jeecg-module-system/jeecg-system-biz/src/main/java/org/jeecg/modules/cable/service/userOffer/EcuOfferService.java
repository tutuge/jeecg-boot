package org.jeecg.modules.cable.service.userOffer;

import org.jeecg.modules.cable.entity.userOffer.EcuOffer;

import java.util.List;

public interface EcuOfferService {
    List<EcuOffer> getList(EcuOffer record);

    long getCount(EcuOffer record);

    EcuOffer getObject(EcuOffer record);

    int insert(EcuOffer record);

    int delete(EcuOffer record);

    int update(EcuOffer record);
}
