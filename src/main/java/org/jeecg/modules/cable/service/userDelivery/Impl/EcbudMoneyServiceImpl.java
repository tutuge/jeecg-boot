package org.jeecg.modules.cable.service.userDelivery.Impl;

import cn.hutool.core.util.ObjUtil;
import jakarta.annotation.Resource;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.cable.entity.userDelivery.EcbudMoney;
import org.jeecg.modules.cable.mapper.dao.userDelivery.EcbudMoneyMapper;
import org.jeecg.modules.cable.service.userDelivery.EcbudMoneyService;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.jeecg.modules.cable.constants.CustomerCacheConstant.CUSTOMER_MONEY_CACHE;

@Service
public class EcbudMoneyServiceImpl implements EcbudMoneyService {
    @Resource
    EcbudMoneyMapper ecbudMoneyMapper;
    @Resource
    private RedisUtil redisUtil;


    @Override
    public List<EcbudMoney> getList(EcbudMoney record) {
        return ecbudMoneyMapper.getList(record);
    }


    @Override
    public long getCount(EcbudMoney record) {
        return ecbudMoneyMapper.getCount(record);
    }


    @Override
    public EcbudMoney getObject(EcbudMoney record) {
        return ecbudMoneyMapper.getObject(record);
    }


    @Override
    public Integer insert(EcbudMoney record) {
        return ecbudMoneyMapper.insert(record);
    }

    @Override
    public Integer update(EcbudMoney record) {
        EcbudMoney object = ecbudMoneyMapper.getObject(record);
        redisUtil.del(CUSTOMER_MONEY_CACHE + ":" + object.getEcbudId() + ":"
                + object.getStartType() + ":" + object.getEcpId());
        return ecbudMoneyMapper.update(record);
    }

    @Override
    public Integer delete(EcbudMoney record) {
        EcbudMoney object = ecbudMoneyMapper.getObject(record);
        redisUtil.del(CUSTOMER_MONEY_CACHE + ":" + object.getEcbudId() + ":"
                + object.getStartType() + ":" + object.getEcpId());
        return ecbudMoneyMapper.delete(record);
    }


    @Override
    public List<EcbudMoney> getListGreaterThanSortId(EcbudMoney record) {
        return ecbudMoneyMapper.getListGreaterThanSortId(record);
    }

    @Override
    public EcbudMoney getObjectPassProvinceName(EcbudMoney record) {
        return ecbudMoneyMapper.getObjectPassProvinceName(record);
    }


    @Override
    public EcbudMoney getLatestObject(EcbudMoney record) {
        return ecbudMoneyMapper.getLatestObject(record);
    }

    @Override
    public EcbudMoney getPricePassEcbudIdAndProvinceIdAndWeight(Integer ecbudId, Boolean startType, Integer provinceId) {
        EcbudMoney money = (EcbudMoney) redisUtil.get(CUSTOMER_MONEY_CACHE + ":" + ecbudId + ":" + startType + ":" + provinceId);
        if (ObjUtil.isNull(money)) {
            EcbudMoney record = new EcbudMoney();
            record.setEcbudId(ecbudId);
            record.setStartType(startType);
            record.setEcpId(provinceId);
            money = ecbudMoneyMapper.getObject(record);
            redisUtil.set(CUSTOMER_MONEY_CACHE + ":" + ecbudId + ":" + startType + ":" + provinceId, money);
        }
        return money;
    }

}
