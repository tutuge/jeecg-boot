package org.jeecg.modules.cable.service.systemEcable.Impl;

import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbShieldMapper;
import org.jeecg.modules.cable.entity.systemEcable.EcbShield;
import org.jeecg.modules.cable.service.systemEcable.EcbShieldService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbShieldServiceImpl implements EcbShieldService {
    @Resource
    EcbShieldMapper ecbShieldMapper;

    @Override
    public List<EcbShield> getList(EcbShield record) {//插入

        return ecbShieldMapper.getList(record);
    }

    @Override
    public List<EcbShield> getListStart(EcbShield record) {
        return ecbShieldMapper.getListStart(record);
    }

    @Override
    public long getCount() {
        return ecbShieldMapper.getCount();
    }

    @Override
    public EcbShield getObject(EcbShield record) {
        return ecbShieldMapper.getObject(record);
    }
}
