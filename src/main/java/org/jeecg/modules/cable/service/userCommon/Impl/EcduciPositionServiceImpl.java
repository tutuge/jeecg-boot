package org.jeecg.modules.cable.service.userCommon.Impl;

import org.jeecg.modules.cable.mapper.dao.userCommon.EcduciPositionMapper;
import org.jeecg.modules.cable.entity.userCommon.EcduciPosition;
import org.jeecg.modules.cable.service.userCommon.EcduciPositionService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class EcduciPositionServiceImpl implements EcduciPositionService {
    @Resource
    EcduciPositionMapper ecduciPositionMapper;

    @Override
    public EcduciPosition getObject(EcduciPosition record) {
        return ecduciPositionMapper.getObject(record);
    }

    @Override
    public Integer insert(EcduciPosition record) {
        return ecduciPositionMapper.insert(record);
    }

    @Override
    public Integer delete(EcduciPosition record) {
        return ecduciPositionMapper.delete(record);
    }

    @Override
    public Integer update(EcduciPosition record) {
        return ecduciPositionMapper.update(record);
    }
}
