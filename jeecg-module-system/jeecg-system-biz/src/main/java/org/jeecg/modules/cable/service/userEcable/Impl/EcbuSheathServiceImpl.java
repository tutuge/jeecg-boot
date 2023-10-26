package org.jeecg.modules.cable.service.userEcable.Impl;

import org.jeecg.modules.cable.mapper.dao.userEcable.EcbuSheathDao;
import org.jeecg.modules.cable.entity.userEcable.EcbuSheath;
import org.jeecg.modules.cable.service.userEcable.EcbuSheathService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbuSheathServiceImpl implements EcbuSheathService {
    @Resource
    EcbuSheathDao ecbuSheathDao;

    @Override
    public EcbuSheath getObject(EcbuSheath record) {
        return ecbuSheathDao.getObject(record);
    }

    @Override
    public Integer insert(EcbuSheath record) {
        return ecbuSheathDao.insert(record);
    }

    @Override
    public Integer update(EcbuSheath record) {
        return ecbuSheathDao.update(record);
    }

    @Override
    public List<EcbuSheath> getList(EcbuSheath record) {
        return ecbuSheathDao.getList(record);
    }

    @Override
    public Integer delete(EcbuSheath record) {
        return ecbuSheathDao.delete(record);
    }
}
