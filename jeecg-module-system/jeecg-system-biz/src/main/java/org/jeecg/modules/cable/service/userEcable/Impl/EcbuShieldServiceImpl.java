package org.jeecg.modules.cable.service.userEcable.Impl;

import org.jeecg.modules.cable.mapper.dao.userEcable.EcbuShieldDao;
import org.jeecg.modules.cable.entity.userEcable.EcbuShield;
import org.jeecg.modules.cable.service.userEcable.EcbuShieldService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbuShieldServiceImpl implements EcbuShieldService {
    @Resource
    EcbuShieldDao ecbuShieldDao;

    @Override
    public EcbuShield getObject(EcbuShield record) {
        return ecbuShieldDao.getObject(record);
    }

    @Override
    public int insert(EcbuShield record) {
        return ecbuShieldDao.insert(record);
    }

    @Override
    public int update(EcbuShield record) {
        return ecbuShieldDao.update(record);
    }

    @Override
    public List<EcbuShield> getList(EcbuShield record) {
        return ecbuShieldDao.getList(record);
    }

    @Override
    public int delete(EcbuShield record) {
        return ecbuShieldDao.delete(record);
    }
}
