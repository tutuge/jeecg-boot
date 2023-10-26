package org.jeecg.modules.cable.service.userOffer.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.userOffer.EcuOffer;
import org.jeecg.modules.cable.mapper.dao.userOffer.EcuOfferDao;
import org.jeecg.modules.cable.service.userOffer.EcuOfferService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcuOfferServiceImpl implements EcuOfferService {
    @Resource
    EcuOfferDao ecuOfferDao;

    @Override
    public List<EcuOffer> getList(EcuOffer record) {
        return ecuOfferDao.getList(record);
    }

    @Override
    public long getCount(EcuOffer record) {
        return ecuOfferDao.getCount(record);
    }

    @Override
    public EcuOffer getObject(EcuOffer record) {
        return ecuOfferDao.getObject(record);
    }

    @Override
    public Integer insert(EcuOffer record) {
        return ecuOfferDao.insert(record);
    }

    @Override
    public Integer delete(EcuOffer record) {
        return ecuOfferDao.delete(record);
    }

    @Override
    public Integer update(EcuOffer record) {
        return ecuOfferDao.update(record);
    }

}
