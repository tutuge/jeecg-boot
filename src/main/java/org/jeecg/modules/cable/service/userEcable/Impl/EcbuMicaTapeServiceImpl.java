package org.jeecg.modules.cable.service.userEcable.Impl;

import org.jeecg.modules.cable.mapper.dao.userEcable.EcbuMicaTapeMapper;
import org.jeecg.modules.cable.entity.userEcable.EcbuMicaTape;
import org.jeecg.modules.cable.service.userEcable.EcbuMicaTapeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbuMicaTapeServiceImpl implements EcbuMicaTapeService {
    @Resource
    EcbuMicaTapeMapper ecbuMicaTapeMapper;

    @Override
    public EcbuMicaTape getObject(EcbuMicaTape record) {
        return ecbuMicaTapeMapper.getObject(record);
    }

    @Override
    public Integer insert(EcbuMicaTape record) {
        return ecbuMicaTapeMapper.insert(record);
    }

    @Override
    public Integer update(EcbuMicaTape record) {
        return ecbuMicaTapeMapper.update(record);
    }

    @Override
    public List<EcbuMicaTape> getList(EcbuMicaTape record) {
        return ecbuMicaTapeMapper.getList(record);
    }

    @Override
    public Integer delete(EcbuMicaTape record) {
        return ecbuMicaTapeMapper.delete(record);
    }
}
