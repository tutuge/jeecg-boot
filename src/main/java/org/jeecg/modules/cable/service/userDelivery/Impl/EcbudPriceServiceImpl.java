package org.jeecg.modules.cable.service.userDelivery.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.userDelivery.EcbudPrice;
import org.jeecg.modules.cable.mapper.dao.userDelivery.EcbudPriceMapper;
import org.jeecg.modules.cable.service.userDelivery.EcbudPriceService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EcbudPriceServiceImpl implements EcbudPriceService {
    @Resource
    EcbudPriceMapper ecbudPriceMapper;

    @Override
    public List<EcbudPrice> getList(EcbudPrice record) {
        return ecbudPriceMapper.getList(record);
    }

    @Override
    public long getCount(EcbudPrice record) {
        return ecbudPriceMapper.getCount(record);
    }

    @Override
    public EcbudPrice getObject(EcbudPrice record) {
        return ecbudPriceMapper.getObject(record);
    }

    @Override
    public Integer insert(EcbudPrice record) {
        record.setAddTime(new Date());
        return ecbudPriceMapper.insert(record);
    }

    @Override
    public Integer update(EcbudPrice record) {
        record.setUpdateTime(new Date());
        return ecbudPriceMapper.updateById(record);
    }

    @Override
    public Integer delete(EcbudPrice record) {
        return ecbudPriceMapper.delete(record);
    }

    // getListGreaterThanSortId 获取大于指定序号的数据列表
    @Override
    public List<EcbudPrice> getListGreaterThanSortId(EcbudPrice record) {
        return ecbudPriceMapper.getListGreaterThanSortId(record);
    }

    // getObjectPassProvinceName
    @Override
    public EcbudPrice getObjectPassProvinceName(EcbudPrice record) {
        return ecbudPriceMapper.getObjectPassProvinceName(record);
    }


    @Override
    public EcbudPrice getLatestObject(EcbudPrice record) {
        return ecbudPriceMapper.getLatestObject(record);
    }

}
