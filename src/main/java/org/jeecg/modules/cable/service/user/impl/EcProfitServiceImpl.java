package org.jeecg.modules.cable.service.user.impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.user.EcProfit;
import org.jeecg.modules.cable.mapper.dao.user.EcProfitMapper;
import org.jeecg.modules.cable.service.user.EcProfitService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EcProfitServiceImpl implements EcProfitService {
    @Resource
    EcProfitMapper ecProfitMapper;

    @Override
    public EcProfit getObject(EcProfit record) {
        return ecProfitMapper.getObject(record);
    }

    @Override
    public List<EcProfit> getList(EcProfit record) {
        return ecProfitMapper.getList(record);
    }

    @Override
    public long getCount(EcProfit record) {
        return ecProfitMapper.getCount(record);
    }

    @Override
    public Integer insert(EcProfit record) {
        record.setAddTime(new Date());
        return ecProfitMapper.insert(record);
    }

    @Override
    public Integer update(EcProfit record) {
        record.setUpdateTime(new Date());
        return ecProfitMapper.updateById(record);
    }

    @Override
    public Integer delete(Integer ecpId) {
        return ecProfitMapper.deleteById(ecpId);
    }
}
