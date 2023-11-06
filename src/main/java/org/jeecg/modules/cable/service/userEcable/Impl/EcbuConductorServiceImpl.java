package org.jeecg.modules.cable.service.userEcable.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.userEcable.EcbuConductor;
import org.jeecg.modules.cable.mapper.dao.userEcable.EcbuConductorMapper;
import org.jeecg.modules.cable.service.userEcable.EcbuConductorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbuConductorServiceImpl implements EcbuConductorService {
    @Resource
    EcbuConductorMapper ecbuConductorMapper;

    @Override
    public EcbuConductor getObject(EcbuConductor record) {
        return ecbuConductorMapper.getObject(record);
    }

    @Override
    public Integer insert(EcbuConductor record) {
        return ecbuConductorMapper.insert(record);
    }

    @Override
    public Integer update(EcbuConductor record) {
        return ecbuConductorMapper.update(record);
    }

    @Override
    public List<EcbuConductor> getList(EcbuConductor record) {
        return ecbuConductorMapper.getList(record);
    }

    @Override
    public Integer delete(EcbuConductor record) {
        return ecbuConductorMapper.delete(record);
    }
}
