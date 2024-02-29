package org.jeecg.modules.cable.service.systemEcable.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.systemEcable.EcbMaterials;
import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbMaterialsMapper;
import org.jeecg.modules.cable.service.systemEcable.EcbMaterialsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbMaterialsServiceImpl implements EcbMaterialsService {
    @Resource
    EcbMaterialsMapper ecbMaterialsMapper;

    @Override
    public List<EcbMaterials> getList(EcbMaterials record) {
        return ecbMaterialsMapper.getList(record);
    }

    @Override
    public long getCount() {
        return ecbMaterialsMapper.getCount();
    }

    @Override
    public EcbMaterials getObject(EcbMaterials record) {
        return ecbMaterialsMapper.getObject(record);
    }
}
