package org.jeecg.modules.cable.service.systemEcable.Impl;

import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbMicatapeDao;
import org.jeecg.modules.cable.entity.systemEcable.EcbMicatape;
import org.jeecg.modules.cable.service.systemEcable.EcbMicatapeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbMicatapeServiceImpl implements EcbMicatapeService {
    @Resource
    EcbMicatapeDao ecbMicatapeDao;

    @Override
    public List<EcbMicatape> getList(EcbMicatape record) {//插入

        return ecbMicatapeDao.getList(record);
    }

    @Override
    public List<EcbMicatape> getListStart(EcbMicatape record) {
        return ecbMicatapeDao.getListStart(record);
    }

    @Override
    public long getCount() {
        return ecbMicatapeDao.getCount();
    }

    @Override
    public EcbMicatape getObject(EcbMicatape record) {
        return ecbMicatapeDao.getObject(record);
    }
}
