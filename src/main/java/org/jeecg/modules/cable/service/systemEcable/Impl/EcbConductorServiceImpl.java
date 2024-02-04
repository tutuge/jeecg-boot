package org.jeecg.modules.cable.service.systemEcable.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.systemEcable.EcbMaterials;
import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbMaterialsMapper;
import org.jeecg.modules.cable.service.systemEcable.EcbConductorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbConductorServiceImpl implements EcbConductorService {
    @Resource
    EcbMaterialsMapper conductorMapper;

    @Override
    public List<EcbMaterials> getList(EcbMaterials record) {
        return conductorMapper.getList(record);
    }

    @Override
    public List<EcbMaterials> getListStart(EcbMaterials record) {
        return conductorMapper.getListStart(record);
    }

    @Override
    public long getCount() {
        return conductorMapper.getCount();
    }

    @Override
    public EcbMaterials getObject(EcbMaterials record) {
        return conductorMapper.getObject(record);
    }
}
