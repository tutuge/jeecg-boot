package org.jeecg.modules.cable.service.systemDelivery.Impl;

import org.jeecg.modules.cable.mapper.dao.systemDelivery.EcbdMoneyMapper;
import org.jeecg.modules.cable.entity.systemDelivery.EcbdMoney;
import org.jeecg.modules.cable.service.systemDelivery.EcbdMoneyService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

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
        return ecbdMoneyMapper.insert(record);
    }

    @Override
    public Integer update(EcbdMoney record) {
        return ecbdMoneyMapper.update(record);
    }
}
