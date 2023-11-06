package org.jeecg.modules.cable.service.systemEcable.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.systemEcable.EcbConductor;
import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbConductorMapper;
import org.jeecg.modules.cable.service.systemEcable.EcbConductorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbConductorServiceImpl implements EcbConductorService {
    @Resource
    EcbConductorMapper conductorMapper;

    @Override
    public List<EcbConductor> getList(EcbConductor record) {
        return conductorMapper.getList(record);
    }

    @Override
    public List<EcbConductor> getListStart(EcbConductor record) {
        return conductorMapper.getListStart(record);
    }

    @Override
    public long getCount() {
        return conductorMapper.getCount();
    }

    @Override
    public EcbConductor getObject(EcbConductor record) {
        return conductorMapper.getObject(record);
    }
}
