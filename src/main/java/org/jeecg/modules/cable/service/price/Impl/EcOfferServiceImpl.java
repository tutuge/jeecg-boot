package org.jeecg.modules.cable.service.price.Impl;

import cn.hutool.core.util.ObjUtil;
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
        ecOfferMapper.updateById(record);
    }

    @Override
    public List<EcOffer> getList(EcOffer record) {
        List<EcOffer> list = ecOfferMapper.getList(record);
        for (EcOffer offer : list) {
            if (ObjUtil.isNotNull(offer.getAddPercent())) {
                offer.setAddPercent(offer.getAddPercent().stripTrailingZeros());
            }
            if (ObjUtil.isNotNull(offer.getFireSilkNumber())) {
                offer.setFireSilkNumber(offer.getFireSilkNumber().stripTrailingZeros());
            }

            if (ObjUtil.isNotNull(offer.getFirePress())) {
                offer.setFirePress(offer.getFirePress().stripTrailingZeros());
            }
            if (ObjUtil.isNotNull(offer.getFireStrand())) {
                offer.setFireStrand(offer.getFireStrand().stripTrailingZeros());
            }
            if (ObjUtil.isNotNull(offer.getZeroSilkNumber())) {
                offer.setZeroSilkNumber(offer.getZeroSilkNumber().stripTrailingZeros());
            }
            if (ObjUtil.isNotNull(offer.getZeroPress())) {
                offer.setZeroPress(offer.getZeroPress().stripTrailingZeros());
            }
            if (ObjUtil.isNotNull(offer.getInsulationFireThickness())) {
                offer.setInsulationFireThickness(offer.getInsulationFireThickness().stripTrailingZeros());
            }
            if (ObjUtil.isNotNull(offer.getInsulationZeroThickness())) {
                offer.setInsulationZeroThickness(offer.getInsulationZeroThickness().stripTrailingZeros());
            }
            if (ObjUtil.isNotNull(offer.getBagThickness())) {
                offer.setBagThickness(offer.getBagThickness().stripTrailingZeros());
            }
            if (ObjUtil.isNotNull(offer.getBag22Thickness())) {
                offer.setBag22Thickness(offer.getBag22Thickness().stripTrailingZeros());
            }
            if (ObjUtil.isNotNull(offer.getShieldThickness())) {
                offer.setShieldThickness(offer.getShieldThickness().stripTrailingZeros());
            }
            if (ObjUtil.isNotNull(offer.getShieldPercent())) {
                offer.setShieldPercent(offer.getShieldPercent().stripTrailingZeros());
            }
            if (ObjUtil.isNotNull(offer.getSteelbandThickness())) {
                offer.setSteelbandThickness(offer.getSteelbandThickness().stripTrailingZeros());
            }
            if (ObjUtil.isNotNull(offer.getSheathThickness())) {
                offer.setSheathThickness(offer.getSheathThickness().stripTrailingZeros());
            }
            if (ObjUtil.isNotNull(offer.getSheath22Thickness())) {
                offer.setSheath22Thickness(offer.getSheath22Thickness().stripTrailingZeros());
            }
            if (ObjUtil.isNotNull(offer.getMicatapeThickness())) {
                offer.setMicatapeThickness(offer.getMicatapeThickness().stripTrailingZeros());
            }
            if (ObjUtil.isNotNull(offer.getSteelwireMembrance())) {
                offer.setSteelwireMembrance(offer.getSteelwireMembrance().stripTrailingZeros());
            }
            if (ObjUtil.isNotNull(offer.getSteelwirePress())) {
                offer.setSteelwirePress(offer.getSteelwirePress().stripTrailingZeros());
            }
        }
        return list;
    }

    @Override
    public void reduceSort(Integer ecqlId, Integer sortId) {
        ecOfferMapper.reduceSort(ecqlId, sortId);
    }
}
