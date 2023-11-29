package org.jeecg.modules.cable.service.userDelivery.Impl;

import cn.hutool.core.util.ObjUtil;
import jakarta.annotation.Resource;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.cable.entity.userDelivery.EcbudPrice;
import org.jeecg.modules.cable.mapper.dao.userDelivery.EcbudPriceMapper;
import org.jeecg.modules.cable.service.userDelivery.EcbudPriceService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static org.jeecg.modules.cable.constants.CustomerCacheConstant.CUSTOMER_PRICE_CACHE;

@Service
public class EcbudPriceServiceImpl implements EcbudPriceService {
    @Resource
    EcbudPriceMapper ecbudPriceMapper;
    @Resource
    private RedisUtil redisUtil;

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
        EcbudPrice object = ecbudPriceMapper.getObject(record);
        redisUtil.del(CUSTOMER_PRICE_CACHE + ":" + object.getEcbudId() + ":"
                + object.getStartType() + ":" + object.getEcpId());
        record.setUpdateTime(new Date());
        return ecbudPriceMapper.updateById(record);
    }

    @Override
    public Integer delete(EcbudPrice record) {
        List<EcbudPrice> objects = ecbudPriceMapper.getList(record);
        for (EcbudPrice object : objects) {
            redisUtil.del(CUSTOMER_PRICE_CACHE + ":" + object.getEcbudId() + ":"
                    + object.getStartType() + ":" + object.getEcpId());
        }
        return ecbudPriceMapper.deleteRecord(record);
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

    @Override
    public EcbudPrice getPricePassEcbudIdAndProvinceIdAndWeight(Integer ecbudId, Boolean startType, Integer provinceId) {
        EcbudPrice price = (EcbudPrice) redisUtil.get(CUSTOMER_PRICE_CACHE + ":" + ecbudId + ":" + startType + ":" + provinceId);
        if (ObjUtil.isNull(price)) {
            EcbudPrice record = new EcbudPrice();
            record.setEcbudId(ecbudId);
            record.setStartType(startType);
            record.setEcpId(provinceId);
            price = ecbudPriceMapper.getObject(record);
            redisUtil.set(CUSTOMER_PRICE_CACHE + ":" + ecbudId + ":" + startType + ":" + provinceId, price);
        }
        return price;
    }

}
