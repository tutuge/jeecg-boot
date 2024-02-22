package org.jeecg.modules.cable.service.price;

import org.jeecg.modules.cable.entity.systemOffer.EcOffer;

import java.util.List;

public interface EcOfferService {

    void insert(EcOffer record);

    EcOffer getObject(EcOffer record);

    void update(EcOffer record);

    List<EcOffer> getList(EcOffer record);

    void reduceSort(Integer ecqlId, Integer sortId);

    void delete(EcOffer record);

    EcOffer getById(Integer ecoId);

    void updateById(EcOffer ecOffer);
}
