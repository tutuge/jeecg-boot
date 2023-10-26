package org.jeecg.modules.cable.service.userDelivery.Impl;

import org.jeecg.modules.cable.mapper.dao.userDelivery.EcbudMoneyDao;
import org.jeecg.modules.cable.entity.userDelivery.EcbudMoney;
import org.jeecg.modules.cable.service.userDelivery.EcbudMoneyService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbudMoneyServiceImpl implements EcbudMoneyService {
    @Resource
    EcbudMoneyDao ecbudMoneyDao;

    //getList
    @Override
    public List<EcbudMoney> getList(EcbudMoney record) {
        return ecbudMoneyDao.getList(record);
    }

    //getCount
    @Override
    public long getCount(EcbudMoney record) {
        return ecbudMoneyDao.getCount(record);
    }

    //getObject
    @Override
    public EcbudMoney getObject(EcbudMoney record) {
        return ecbudMoneyDao.getObject(record);
    }

    //insert
    @Override
    public Integer insert(EcbudMoney record) {
        return ecbudMoneyDao.insert(record);
    }

    @Override
    public Integer update(EcbudMoney record) {
        return ecbudMoneyDao.update(record);
    }

    @Override
    public Integer delete(EcbudMoney record) {
        return ecbudMoneyDao.delete(record);
    }

    //getListGreaterThanSortId 获取大于指定序号的数据列表
    @Override
    public List<EcbudMoney> getListGreaterThanSortId(EcbudMoney record) {
        return ecbudMoneyDao.getListGreaterThanSortId(record);
    }

    //getObjectPassProvinceName
    @Override
    public EcbudMoney getObjectPassProvinceName(EcbudMoney record) {
        return ecbudMoneyDao.getObjectPassProvinceName(record);
    }

    //getLatestObject
    @Override
    public EcbudMoney getLatestObject(EcbudMoney record) {
        return ecbudMoneyDao.getLatestObject(record);
    }

}
