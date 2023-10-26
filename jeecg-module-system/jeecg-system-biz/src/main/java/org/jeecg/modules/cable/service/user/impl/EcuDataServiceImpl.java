package org.jeecg.modules.cable.service.user.impl;

import org.jeecg.modules.cable.mapper.dao.user.EcuDataDao;
import org.jeecg.modules.cable.entity.user.EcuData;
import org.jeecg.modules.cable.service.user.EcuDataService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcuDataServiceImpl implements EcuDataService {
    @Resource
    EcuDataDao ecuDataDao;


    @Override
    public EcuData getObject(EcuData record) {
        return ecuDataDao.getObject(record);
    }

    @Override
    public List<EcuData> getList(EcuData record) {
        return ecuDataDao.getList(record);
    }

    @Override
    public long getCount(EcuData record) {
        return ecuDataDao.getCount(record);
    }

    @Override
    public Integer insert(EcuData record) {
        return ecuDataDao.insert(record);
    }

    @Override
    public Integer update(EcuData record) {
        return ecuDataDao.update(record);
    }
}
