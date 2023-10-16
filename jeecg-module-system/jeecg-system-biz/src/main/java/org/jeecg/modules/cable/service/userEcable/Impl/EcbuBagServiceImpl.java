package org.jeecg.modules.cable.service.userEcable.Impl;

import org.jeecg.modules.cable.mapper.dao.userEcable.EcbuBagDao;
import org.jeecg.modules.cable.entity.userEcable.EcbuBag;
import org.jeecg.modules.cable.service.userEcable.EcbuBagService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbuBagServiceImpl implements EcbuBagService {
    @Resource
    EcbuBagDao ecbuBagDao;

    @Override
    public EcbuBag getObject(EcbuBag record) {
        return ecbuBagDao.getObject(record);
    }

    @Override
    public int insert(EcbuBag record) {
        return ecbuBagDao.insert(record);
    }

    @Override
    public int update(EcbuBag record) {
        return ecbuBagDao.update(record);
    }

    @Override
    public List<EcbuBag> getList(EcbuBag record) {
        return ecbuBagDao.getList(record);
    }

    @Override
    public int delete(EcbuBag record) {
        return ecbuBagDao.delete(record);
    }
}
