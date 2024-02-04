package org.jeecg.modules.cable.service.systemEcable.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.systemEcable.EcbMaterialType;
import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbMaterialTypeMapper;
import org.jeecg.modules.cable.service.systemEcable.EcbMaterialService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbMaterialServiceImpl implements EcbMaterialService {
    @Resource
    EcbMaterialTypeMapper materialMapper;

    @Override
    public List<EcbMaterialType> getList(EcbMaterialType record) {//插入
        return materialMapper.getList(record);
    }

    @Override
    public List<EcbMaterialType> getListStart(EcbMaterialType record) {
        return materialMapper.getListStart(record);
    }

    @Override
    public long getCount() {
        return materialMapper.getCount();
    }

    @Override
    public EcbMaterialType getObject(EcbMaterialType record) {
        return materialMapper.getSysObject(record);
    }
}
