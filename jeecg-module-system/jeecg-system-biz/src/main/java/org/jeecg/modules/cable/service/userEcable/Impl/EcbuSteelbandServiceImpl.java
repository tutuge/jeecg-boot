package org.jeecg.modules.cable.service.userEcable.Impl;

import org.jeecg.modules.cable.mapper.dao.userEcable.EcbuSteelbandDao;
import org.jeecg.modules.cable.entity.userEcable.EcbuSteelband;
import org.jeecg.modules.cable.service.userEcable.EcbuSteelbandService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbuSteelbandServiceImpl implements EcbuSteelbandService {
    @Resource
    EcbuSteelbandDao ecbuSteelbandDao;

    @Override
    public EcbuSteelband getObject(EcbuSteelband record) {
        return ecbuSteelbandDao.getObject(record);
    }

    @Override
    public int insert(EcbuSteelband record) {
        return ecbuSteelbandDao.insert(record);
    }

    @Override
    public int update(EcbuSteelband record) {
        return ecbuSteelbandDao.update(record);
    }

    @Override
    public List<EcbuSteelband> getList(EcbuSteelband record) {
        return ecbuSteelbandDao.getList(record);
    }

    @Override
    public int delete(EcbuSteelband record) {
        return ecbuSteelbandDao.delete(record);
    }
}
