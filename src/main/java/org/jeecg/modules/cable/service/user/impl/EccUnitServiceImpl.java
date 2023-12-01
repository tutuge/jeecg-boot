package org.jeecg.modules.cable.service.user.impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.user.EccUnit;
import org.jeecg.modules.cable.mapper.dao.user.EccUnitMapper;
import org.jeecg.modules.cable.service.user.EccUnitService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EccUnitServiceImpl implements EccUnitService {
    @Resource
    EccUnitMapper eccUnitMapper;

    @Override
    public EccUnit getObject(EccUnit record) {
        return eccUnitMapper.getObject(record);
    }

    @Override
    public EccUnit selectByModelId(Integer ecusmId) {
        return eccUnitMapper.selectByModelId(ecusmId);
    }

    @Override
    public List<EccUnit> getList(EccUnit record) {
        return eccUnitMapper.getList(record);
    }

    @Override
    public long getCount(EccUnit record) {
        return eccUnitMapper.getCount(record);
    }

    @Override
    public Integer insert(EccUnit record) {
        return eccUnitMapper.insert(record);
    }

    @Override
    public Integer update(EccUnit record) {
        return eccUnitMapper.updateById(record);
    }

    @Override
    public Integer delete(Integer eccuId) {
        return eccUnitMapper.deleteById(eccuId);
    }
}
