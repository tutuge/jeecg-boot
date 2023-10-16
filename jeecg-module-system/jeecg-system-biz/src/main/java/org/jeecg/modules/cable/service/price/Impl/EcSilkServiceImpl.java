package org.jeecg.modules.cable.service.price.Impl;

import org.jeecg.modules.cable.mapper.dao.price.EcSilkDao;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;
import org.jeecg.modules.cable.service.price.EcSilkService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcSilkServiceImpl implements EcSilkService {
    @Resource
    EcSilkDao ecSilkDao;

    @Override
    public List<EcSilk> getList(EcSilk record) {//插入

        return ecSilkDao.getList(record);
    }

    //getObject
    @Override
    public EcSilk getObject(EcSilk record) {
        return ecSilkDao.getObject(record);
    }
}
