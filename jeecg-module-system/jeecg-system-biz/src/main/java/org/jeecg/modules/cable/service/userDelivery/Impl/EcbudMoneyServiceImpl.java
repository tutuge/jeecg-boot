package org.jeecg.modules.cable.service.userDelivery.Impl;

import org.jeecg.modules.cable.mapper.dao.userDelivery.EcbudMoneyMapper;
import org.jeecg.modules.cable.entity.userDelivery.EcbudMoney;
import org.jeecg.modules.cable.service.userDelivery.EcbudMoneyService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbudMoneyServiceImpl implements EcbudMoneyService {
    @Resource
    EcbudMoneyMapper ecbudMoneyMapper;


    @Override
    public List<EcbudMoney> getList(EcbudMoney record) {
        return ecbudMoneyMapper.getList(record);
    }

    //getCount
    @Override
    public long getCount(EcbudMoney record) {
        return ecbudMoneyMapper.getCount(record);
    }

    //getObject
    @Override
    public EcbudMoney getObject(EcbudMoney record) {
        return ecbudMoneyMapper.getObject(record);
    }

    //insert
    @Override
    public Integer insert(EcbudMoney record) {
        return ecbudMoneyMapper.insert(record);
    }

    @Override
    public Integer update(EcbudMoney record) {
        return ecbudMoneyMapper.update(record);
    }

    @Override
    public Integer delete(EcbudMoney record) {
        return ecbudMoneyMapper.delete(record);
    }

    //getListGreaterThanSortId 获取大于指定序号的数据列表
    @Override
    public List<EcbudMoney> getListGreaterThanSortId(EcbudMoney record) {
        return ecbudMoneyMapper.getListGreaterThanSortId(record);
    }

    //getObjectPassProvinceName
    @Override
    public EcbudMoney getObjectPassProvinceName(EcbudMoney record) {
        return ecbudMoneyMapper.getObjectPassProvinceName(record);
    }

    //getLatestObject
    @Override
    public EcbudMoney getLatestObject(EcbudMoney record) {
        return ecbudMoneyMapper.getLatestObject(record);
    }

}
