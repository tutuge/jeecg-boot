package org.jeecg.modules.cable.service.systemDelivery.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.systemEcable.EcbMaterialType;
import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbMaterialTypeMapper;
import org.jeecg.modules.cable.service.systemDelivery.EcbMaterialTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbMaterialTypeServiceImpl implements EcbMaterialTypeService {
    @Resource
    private EcbMaterialTypeMapper ecbMaterialTypeMapper;

    @Override
    public List<EcbMaterialType> getList(EcbMaterialType record) {
        return ecbMaterialTypeMapper.getList(record);
    }
}
