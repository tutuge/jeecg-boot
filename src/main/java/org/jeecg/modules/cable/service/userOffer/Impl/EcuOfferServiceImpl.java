package org.jeecg.modules.cable.service.userOffer.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.userOffer.EcuOffer;
import org.jeecg.modules.cable.mapper.dao.userOffer.EcuOfferMapper;
import org.jeecg.modules.cable.service.userOffer.EcuOfferService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcuOfferServiceImpl implements EcuOfferService {
    @Resource
    EcuOfferMapper ecuOfferMapper;

    @Override
    public List<EcuOffer> getList(EcuOffer record) {
        return ecuOfferMapper.getList(record);
    }

    @Override
    public Long getCount(EcuOffer record) {
        return ecuOfferMapper.getCount(record);
    }

    @Override
    public EcuOffer getObject(EcuOffer record) {
        return ecuOfferMapper.getObject(record);
    }

    @Override
    public EcuOffer getById(Integer ecuoId) {
        return ecuOfferMapper.selectById(ecuoId);
    }

    @Override
    public Integer insert(EcuOffer record) {
        return ecuOfferMapper.insert(record);
    }

    @Override
    public Integer delete(EcuOffer record) {
        return ecuOfferMapper.deleteRecord(record);
    }

    @Override
    public Integer update(EcuOffer record) {
        return ecuOfferMapper.updateRecord(record);
    }

    @Override
    public void reduceSort(Integer ecqulId, Integer sortId) {
        ecuOfferMapper.reduceSort(ecqulId, sortId);
    }

    @Override
    public EcuOffer getByLevelIdAndArea(Integer ecqulId, String areaStr) {
        return ecuOfferMapper.getByLevelIdAndArea(ecqulId, areaStr);
    }

}
