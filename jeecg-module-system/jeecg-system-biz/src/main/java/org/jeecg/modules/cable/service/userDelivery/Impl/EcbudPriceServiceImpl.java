package org.jeecg.modules.cable.service.userDelivery.Impl;

import org.jeecg.modules.cable.mapper.dao.userDelivery.EcbudPriceDao;
import org.jeecg.modules.cable.entity.userDelivery.EcbudPrice;
import org.jeecg.modules.cable.service.userDelivery.EcbudPriceService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbudPriceServiceImpl implements EcbudPriceService {
    @Resource
    EcbudPriceDao ecbudPriceDao;

    @Override
    public List<EcbudPrice> getList(EcbudPrice record) {
        return ecbudPriceDao.getList(record);
    }

    @Override
    public long getCount(EcbudPrice record) {
        return ecbudPriceDao.getCount(record);
    }

    @Override
    public EcbudPrice getObject(EcbudPrice record) {
        return ecbudPriceDao.getObject(record);
    }

    @Override
    public int insert(EcbudPrice record) {
        return ecbudPriceDao.insert(record);
    }

    @Override
    public int update(EcbudPrice record) {
        return ecbudPriceDao.update(record);
    }

    @Override
    public int delete(EcbudPrice record) {
        return ecbudPriceDao.delete(record);
    }

    //getListGreaterThanSortId 获取大于指定序号的数据列表
    @Override
    public List<EcbudPrice> getListGreaterThanSortId(EcbudPrice record) {
        return ecbudPriceDao.getListGreaterThanSortId(record);
    }

    //getObjectPassProvinceName
    @Override
    public EcbudPrice getObjectPassProvinceName(EcbudPrice record) {
        return ecbudPriceDao.getObjectPassProvinceName(record);
    }

    //getLatestObject
    @Override
    public EcbudPrice getLatestObject(EcbudPrice record) {
        return ecbudPriceDao.getLatestObject(record);
    }

}
