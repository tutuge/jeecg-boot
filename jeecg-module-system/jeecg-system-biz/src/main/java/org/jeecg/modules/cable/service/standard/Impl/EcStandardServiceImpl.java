package org.jeecg.modules.cable.service.standard.Impl;

import org.jeecg.modules.cable.mapper.dao.standard.EcStandardDao;
import org.jeecg.modules.cable.entity.standard.EcStandard;
import org.jeecg.modules.cable.service.standard.EcStandardService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class EcStandardServiceImpl implements EcStandardService {
    @Resource
    EcStandardDao ecStandardDao;
    //getObject
    @Override
    public EcStandard getObject(EcStandard record)
    {
        return ecStandardDao.getObject(record);
    }
}
