package org.jeecg.modules.cable.service.quality.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.quality.EcquLevel;
import org.jeecg.modules.cable.mapper.dao.quality.EcquLevelMapper;
import org.jeecg.modules.cable.service.quality.EcquLevelService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcquLevelServiceImpl implements EcquLevelService {
    @Resource
    EcquLevelMapper ecquLevelMapper;

    @Override
    public List<EcquLevel> getList(EcquLevel record) {
        return ecquLevelMapper.getList(record);
    }

    @Override
    public long getCount(EcquLevel record) {
        return ecquLevelMapper.getCount(record);
    }

    @Override
    public EcquLevel getObject(EcquLevel record) {
        return ecquLevelMapper.getObject(record);
    }

    @Override
    public Integer insert(EcquLevel record) {
        return ecquLevelMapper.insert(record);
    }

    @Override
    public Integer update(EcquLevel record) {
        return ecquLevelMapper.update(record);
    }

    @Override
    public Integer delete(Integer ecqulId) {
        return ecquLevelMapper.deleteById(ecqulId);
    }

}
