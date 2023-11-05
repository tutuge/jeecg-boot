package org.jeecg.modules.cable.service.standard.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.standard.EcStandard;
import org.jeecg.modules.cable.mapper.dao.standard.EcStandardMapper;
import org.jeecg.modules.cable.service.standard.EcStandardService;
import org.springframework.stereotype.Service;

@Service
public class EcStandardServiceImpl implements EcStandardService {
    @Resource
    EcStandardMapper ecStandardMapper;


    @Override
    public EcStandard getObject(EcStandard record) {
        return ecStandardMapper.getObject(record);
    }
}
