package org.jeecg.modules.cable.service.price.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.systemOffer.EcOffer;
import org.jeecg.modules.cable.mapper.dao.systemOffer.EcOfferDao;
import org.jeecg.modules.cable.service.price.EcOfferService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcOfferServiceImpl implements EcOfferService {
    @Resource
    EcOfferDao ecOfferDao;

    @Override
    public void insert(EcOffer record) {
        ecOfferDao.insert(record);
    }

    @Override
    public EcOffer getObject(EcOffer record) {
        return ecOfferDao.getObject(record);
    }

    @Override
    public void update(EcOffer record) {
        ecOfferDao.update(record);
    }

    @Override
    public List<EcOffer> getList(EcOffer record) {
        return ecOfferDao.getList(record);
    }
}
