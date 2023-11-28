package org.jeecg.modules.cable.service.systemQuality.impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.systemQuality.EcqLevel;
import org.jeecg.modules.cable.mapper.dao.systemQuality.EcqLevelMapper;
import org.jeecg.modules.cable.service.systemQuality.EcqLevelService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcqLevelServiceImpl implements EcqLevelService {
    @Resource
    EcqLevelMapper ecqLevelMapper;

    @Override
    public List<EcqLevel> getList(EcqLevel record) {
        return ecqLevelMapper.getList(record);
    }

    @Override
    public long getCount(EcqLevel record) {
        return ecqLevelMapper.getCount(record);
    }

    @Override
    public EcqLevel getObject(EcqLevel record) {
        return ecqLevelMapper.getObject(record);
    }

    @Override
    public Integer insert(EcqLevel record) {
        return ecqLevelMapper.insert(record);
    }

    @Override
    public Integer update(EcqLevel record) {
        return ecqLevelMapper.updateRecord(record);
    }

    @Override
    public Integer delete(Integer ecqlId) {
        return ecqLevelMapper.deleteById(ecqlId);
    }

}
