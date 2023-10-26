package org.jeecg.modules.cable.service.price.Impl;

import org.jeecg.modules.cable.mapper.dao.price.EcuqInputDao;
import org.jeecg.modules.cable.entity.price.EcuqInput;
import org.jeecg.modules.cable.service.price.EcuqInputService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcuqInputServiceImpl implements EcuqInputService {
    @Resource
    EcuqInputDao ecuqInputDao;

    //getList
    @Override
    public List<EcuqInput> getList(EcuqInput record) {
        return ecuqInputDao.getList(record);
    }

    //getCount
    @Override
    public long getCount(EcuqInput record) {
        return ecuqInputDao.getCount(record);
    }

    //getObjectPassId
    @Override
    public EcuqInput getObject(EcuqInput record) {
        return ecuqInputDao.getObject(record);
    }

    //insert
    @Override
    public Integer insert(EcuqInput record) {
        return ecuqInputDao.insert(record);
    }

    //getListGreaterThanSortId
    @Override
    public List<EcuqInput> getListGreaterThanSortId(EcuqInput record) {
        return ecuqInputDao.getListGreaterThanSortId(record);
    }

    @Override
    public Integer delete(EcuqInput record) {
        return ecuqInputDao.delete(record);
    }

    @Override
    public Integer update(EcuqInput record) {
        return ecuqInputDao.update(record);
    }

    //getLatestObject
    @Override
    public EcuqInput getLatestObject(EcuqInput record) {
        return ecuqInputDao.getLatestObject(record);
    }
}
