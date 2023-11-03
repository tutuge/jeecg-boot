package org.jeecg.modules.cable.service.price.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.systemOffer.EcOffer;
import org.jeecg.modules.cable.mapper.dao.systemOffer.EcOfferMapper;
import org.jeecg.modules.cable.service.price.EcOfferService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcOfferServiceImpl implements EcOfferService {
    @Resource
    EcOfferMapper ecOfferMapper;

    @Override
    public void insert(EcOffer record) {
        ecOfferMapper.insert(record);
    }

    @Override
    public EcOffer getObject(EcOffer record) {
        return ecOfferMapper.getObject(record);
    }

    @Override
    public void update(EcOffer record) {
        ecOfferMapper.update(record);
    }

    @Override
    public List<EcOffer> getList(EcOffer record) {
        return ecOfferMapper.getList(record);
    }
}
