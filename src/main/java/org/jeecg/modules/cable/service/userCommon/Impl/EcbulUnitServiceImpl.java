package org.jeecg.modules.cable.service.userCommon.Impl;

import cn.hutool.core.util.ObjUtil;
import jakarta.annotation.Resource;
import org.jeecg.common.redis.CacheUtils;
import org.jeecg.modules.cable.entity.userCommon.EcbulUnit;
import org.jeecg.modules.cable.mapper.dao.userCommon.EcbulUnitMapper;
import org.jeecg.modules.cable.service.userCommon.EcbulUnitService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.jeecg.modules.cable.constants.CustomerCacheConstant.CUSTOMER_UNIT_CACHE;

@Service
public class EcbulUnitServiceImpl implements EcbulUnitService {
    @Resource
    private EcbulUnitMapper ecbulUnitMapper;

    @Override
    public List<EcbulUnit> getList(EcbulUnit record) {
        return ecbulUnitMapper.getList(record);
    }

    @Override
    public long getCount(EcbulUnit record) {
        return ecbulUnitMapper.getCount(record);
    }


    @Cacheable(value = CUSTOMER_UNIT_CACHE, key = "#ecbuluId", unless = "#result == null ")
    @Override
    public EcbulUnit getObjectById(Integer ecbuluId) {
        return ecbulUnitMapper.selectById(ecbuluId);
    }

    @Override
    public EcbulUnit getObject(EcbulUnit record) {
        return ecbulUnitMapper.getObject(record);
    }

    @Override
    public Integer insert(EcbulUnit record) {
        return ecbulUnitMapper.insert(record);
    }

    @Override
    public Integer update(EcbulUnit record) {
        EcbulUnit object = getObject(record);
        if (ObjUtil.isNotNull(object)) {
            CacheUtils.evict(CUSTOMER_UNIT_CACHE, object.getEcbuluId());
        }
        return ecbulUnitMapper.updateById(record);
    }

    @Override
    public Integer delete(EcbulUnit record) {
        List<EcbulUnit> list = getList(record);
        for (EcbulUnit unit : list) {
            CacheUtils.evict(CUSTOMER_UNIT_CACHE, unit.getEcbuluId());
        }
        return ecbulUnitMapper.delete(record);
    }


    @Override
    public List<EcbulUnit> getListGreaterThanSortId(EcbulUnit record) {
        return ecbulUnitMapper.getListGreaterThanSortId(record);
    }

    //getObjectPassProvinceName
    @Override
    public EcbulUnit getObjectPassLengthName(EcbulUnit record) {
        return ecbulUnitMapper.getObjectPassLengthName(record);
    }


    @Override
    public EcbulUnit getLatestObject(EcbulUnit record) {
        return ecbulUnitMapper.getLatestObject(record);
    }

    @Override
    public void reduceSort(Integer ecbuluId, Integer sortId) {
        ecbulUnitMapper.reduceSort(ecbuluId, sortId);
    }

}
