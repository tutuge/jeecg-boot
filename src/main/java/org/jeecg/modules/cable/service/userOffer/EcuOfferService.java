package org.jeecg.modules.cable.service.userOffer;

import org.jeecg.modules.cable.entity.userOffer.EcuOffer;

import java.util.List;

public interface EcuOfferService {
    List<EcuOffer> getList(EcuOffer record);

    Long getCount(EcuOffer record);

    EcuOffer getObject(EcuOffer record);

    EcuOffer getById(Integer ecuoId);

    Integer insert(EcuOffer record);

    Integer delete(EcuOffer record);

    Integer update(EcuOffer record);

    void reduceSort(Integer ecqulId, Integer sortId);

    /**
     * @param ecqulId 质量等级ID
     * @param areaStr 规格
     * @return
     */
    EcuOffer getByLevelIdAndArea(Integer ecqulId, String areaStr);
}
