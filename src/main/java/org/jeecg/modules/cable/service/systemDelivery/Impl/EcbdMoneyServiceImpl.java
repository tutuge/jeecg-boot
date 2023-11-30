package org.jeecg.modules.cable.service.systemDelivery.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.systemDelivery.EcbdMoney;
import org.jeecg.modules.cable.mapper.dao.systemDelivery.EcbdMoneyMapper;
import org.jeecg.modules.cable.service.systemDelivery.EcbdMoneyService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EcbdMoneyServiceImpl implements EcbdMoneyService {
    @Resource
    EcbdMoneyMapper ecbdMoneyMapper;

    @Override
    public List<EcbdMoney> getList(EcbdMoney record) {
        return ecbdMoneyMapper.getList(record);
    }

    @Override
    public long getCount(EcbdMoney record) {
        return ecbdMoneyMapper.getCount(record);
    }

    @Override
    public EcbdMoney getObject(EcbdMoney record) {
        return ecbdMoneyMapper.getObject(record);
    }

    @Override
    public Integer insert(EcbdMoney record) {
        record.setCreateTime(new Date());
        return ecbdMoneyMapper.insert(record);
    }

    @Override
    public Integer update(EcbdMoney record) {
        record.setUpdateTime(new Date());
        return ecbdMoneyMapper.updateById(record);
    }

    @Override
    public void deleteById(Integer ecbdmId) {
        ecbdMoneyMapper.deleteById(ecbdmId);
    }

    @Override
    public EcbdMoney getObjectPassProvinceName(EcbdMoney money) {
        return ecbdMoneyMapper.getObjectPassProvinceName(money);
    }

    @Override
    public EcbdMoney getLatestObject(EcbdMoney record) {
        return ecbdMoneyMapper.getLatestObject(record);
    }
}
