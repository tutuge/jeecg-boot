package org.jeecg.modules.cable.service.systemEcable.Impl;

import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbShieldDao;
import org.jeecg.modules.cable.entity.systemEcable.EcbShield;
import org.jeecg.modules.cable.service.systemEcable.EcbShieldService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbShieldServiceImpl implements EcbShieldService {
    @Resource
    EcbShieldDao ecbShieldDao;

    @Override
    public List<EcbShield> getList(EcbShield record) {//插入

        return ecbShieldDao.getList(record);
    }

    @Override
    public List<EcbShield> getListStart(EcbShield record) {
        return ecbShieldDao.getListStart(record);
    }

    @Override
    public long getCount() {
        return ecbShieldDao.getCount();
    }

    @Override
    public EcbShield getObject(EcbShield record) {
        return ecbShieldDao.getObject(record);
    }
}
