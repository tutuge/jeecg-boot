package org.jeecg.modules.cable.service.user.impl;

import org.jeecg.modules.cable.mapper.dao.user.EcProfitDao;
import org.jeecg.modules.cable.entity.user.EcProfit;
import org.jeecg.modules.cable.service.user.EcProfitService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcProfitServiceImpl implements EcProfitService {
    @Resource
    EcProfitDao ecProfitDao;

    @Override
    public EcProfit getObject(EcProfit record) {
        return ecProfitDao.getObject(record);
    }

    @Override
    public List<EcProfit> getList(EcProfit record) {
        return ecProfitDao.getList(record);
    }

    @Override
    public long getCount(EcProfit record) {
        return ecProfitDao.getCount(record);
    }

    @Override
    public int insert(EcProfit record) {
        return ecProfitDao.insert(record);
    }

    @Override
    public int update(EcProfit record) {
        return ecProfitDao.update(record);
    }

    @Override
    public int delete(EcProfit record) {
        return ecProfitDao.delete(record);
    }
}
