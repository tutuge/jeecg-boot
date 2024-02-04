package org.jeecg.modules.cable.service.systemEcable.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.systemEcable.EcbMaterial;
import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbMaterialMapper;
import org.jeecg.modules.cable.service.systemEcable.EcbMaterialService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbMaterialServiceImpl implements EcbMaterialService {
    @Resource
    EcbMaterialMapper materialMapper;

    @Override
    public List<EcbMaterial> getList(EcbMaterial record) {//插入
        return materialMapper.getList(record);
    }

    @Override
    public List<EcbMaterial> getListStart(EcbMaterial record) {
        return materialMapper.getListStart(record);
    }

    @Override
    public long getCount() {
        return materialMapper.getCount();
    }

    @Override
    public EcbMaterial getObject(EcbMaterial record) {
        return materialMapper.getSysObject(record);
    }
}
