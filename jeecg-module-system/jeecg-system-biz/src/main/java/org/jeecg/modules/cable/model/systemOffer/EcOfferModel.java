package org.jeecg.modules.cable.model.systemOffer;

import org.jeecg.modules.cable.entity.systemOffer.EcOffer;
import org.jeecg.modules.cable.service.price.EcOfferService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EcOfferModel {
    @Resource
    EcOfferService ecOfferService;

    /***===数据模型===***/
    //getList
    public List<EcOffer> getList(int ecsId) {
        EcOffer record = new EcOffer();
        record.setEcsId(ecsId);
        return ecOfferService.getList(record);
    }
}
