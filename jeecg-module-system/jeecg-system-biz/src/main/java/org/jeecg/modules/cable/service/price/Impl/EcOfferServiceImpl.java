package org.jeecg.modules.cable.service.price.Impl;

import org.jeecg.modules.cable.mapper.dao.systemOffer.EcOfferDao;
import org.jeecg.modules.cable.entity.systemOffer.EcOffer;
import org.jeecg.modules.cable.service.price.EcOfferService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcOfferServiceImpl implements EcOfferService {
    @Resource
    EcOfferDao ecOfferDao;

    @Override
    public List<EcOffer> getList(EcOffer record) {
        return ecOfferDao.getList(record);
    }
}
