package org.jeecg.modules.cable.service.price.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.systemOffer.EcOffer;
import org.jeecg.modules.cable.mapper.dao.systemOffer.EcOfferMapper;
import org.jeecg.modules.cable.service.price.EcOfferService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class EcOfferServiceImpl implements EcOfferService {
    @Resource
    private EcOfferMapper ecOfferMapper;

    @Override
    public void insert(EcOffer record) {
        record.setCreateTime(new Date());
        ecOfferMapper.insert(record);
    }

    @Override
    public EcOffer getObject(EcOffer record) {
        return ecOfferMapper.getObject(record);
    }

    @Override
    public void update(EcOffer record) {
        record.setUpdateTime(new Date());
        ecOfferMapper.updateById(record);
    }

    @Override
    public List<EcOffer> getList(EcOffer record) {
        List<EcOffer> list = ecOfferMapper.getList(record);
        return list;
    }

    @Override
    public void reduceSort(Integer ecqlId, Integer sortId) {
        ecOfferMapper.reduceSort(ecqlId, sortId);
    }

    @Override
    public void delete(EcOffer record) {
        ecOfferMapper.deleteById(record);
    }

    @Override
    public EcOffer getById(Integer ecoId) {
        return ecOfferMapper.selectById(ecoId);
    }

    @Override
    public void updateById(EcOffer ecOffer) {
        ecOfferMapper.updateById(ecOffer);
    }

    @Override
    public Long getCount(EcOffer offer) {
        LambdaQueryWrapper<EcOffer> eq = Wrappers.lambdaQuery(EcOffer.class)
                .eq(EcOffer::getEcqlId, offer.getEcqlId())
                .eq(EcOffer::getStartType, true);
        return ecOfferMapper.selectCount(eq);
    }

    @Override
    public Set<Integer> getMaterialIdList() {
        return ecOfferMapper.getMaterialIdList();
    }
}
