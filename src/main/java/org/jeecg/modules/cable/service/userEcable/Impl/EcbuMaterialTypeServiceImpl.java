package org.jeecg.modules.cable.service.userEcable.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.userEcable.EcbuMaterialType;
import org.jeecg.modules.cable.mapper.dao.userEcable.EcbuMaterialTypeMapper;
import org.jeecg.modules.cable.service.userEcable.EcbuMaterialTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户材料类型
 */
@Service
public class EcbuMaterialTypeServiceImpl implements EcbuMaterialTypeService {
    @Resource
    private EcbuMaterialTypeMapper ecbuMaterialTypeMapper;

    @Override
    public List<EcbuMaterialType> getList(EcbuMaterialType record) {
        return ecbuMaterialTypeMapper.getList(record);
    }
}
