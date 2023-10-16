package org.jeecg.modules.cable.service.price;

import org.jeecg.modules.cable.entity.systemOffer.EcOffer;

import java.util.List;

public interface EcOfferService {
    List<EcOffer> getList(EcOffer record);
}
