package org.jeecg.modules.cable.service.user.impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.user.EcuData;
import org.jeecg.modules.cable.mapper.dao.user.EcuDataMapper;
import org.jeecg.modules.cable.service.user.EcuDataService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EcuDataServiceImpl implements EcuDataService {
    @Resource
    EcuDataMapper ecuDataMapper;


    @Override
    public EcuData getObject(EcuData record) {
        return ecuDataMapper.getObject(record);
    }

    @Override
    public List<EcuData> getList(EcuData record) {
        return ecuDataMapper.getList(record);
    }

    @Override
    public long getCount(EcuData record) {
        return ecuDataMapper.getCount(record);
    }

    @Override
    public Integer insert(EcuData record) {
        record.setCreateTime(new Date());
        return ecuDataMapper.insert(record);
    }

    @Override
    public Integer update(EcuData record) {
        record.setUpdateTime(new Date());
        return ecuDataMapper.update(record);
    }
}
