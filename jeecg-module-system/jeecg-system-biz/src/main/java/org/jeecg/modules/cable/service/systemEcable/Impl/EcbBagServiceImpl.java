package org.jeecg.modules.cable.service.systemEcable.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.systemEcable.EcbBag;
import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbBagDao;
import org.jeecg.modules.cable.service.systemEcable.EcbBagService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbBagServiceImpl implements EcbBagService {
    @Resource
    EcbBagDao ecbBagDao;

    @Override
    public List<EcbBag> getList(EcbBag record) {//插入
        return ecbBagDao.getList(record);
    }

    @Override
    public List<EcbBag> getListStart(EcbBag record) {
        return ecbBagDao.getListStart(record);
    }

    @Override
    public long getCount() {
        return ecbBagDao.getCount();
    }

    @Override
    public EcbBag getObject(EcbBag record) {
        return ecbBagDao.getObject(record);
    }
}
