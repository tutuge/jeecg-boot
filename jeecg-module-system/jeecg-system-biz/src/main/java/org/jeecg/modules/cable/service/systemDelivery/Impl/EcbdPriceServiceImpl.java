package org.jeecg.modules.cable.service.systemDelivery.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.systemDelivery.EcbdPrice;
import org.jeecg.modules.cable.mapper.dao.systemDelivery.EcbdPriceMapper;
import org.jeecg.modules.cable.service.systemDelivery.EcbdPriceService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EcbdPriceServiceImpl implements EcbdPriceService {
    @Resource
    EcbdPriceMapper ecbdPriceMapper;

    @Override
    public List<EcbdPrice> getList(EcbdPrice record) {
        return ecbdPriceMapper.getList(record);
    }

    @Override
    public long getCount(EcbdPrice record) {
        return ecbdPriceMapper.getCount(record);
    }

    @Override
    public EcbdPrice getObject(EcbdPrice record) {
        return ecbdPriceMapper.getObject(record);
    }

    @Override
    public Integer insert(EcbdPrice record) {
        record.setAddTime(new Date());
        return ecbdPriceMapper.insert(record);
    }

    @Override
    public Integer update(EcbdPrice record) {
        record.setUpdateTime(new Date());
        return ecbdPriceMapper.updateById(record);
    }
}
