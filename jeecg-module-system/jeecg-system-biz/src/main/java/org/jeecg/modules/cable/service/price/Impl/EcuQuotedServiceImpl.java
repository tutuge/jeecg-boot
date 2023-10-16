package org.jeecg.modules.cable.service.price.Impl;

import org.jeecg.modules.cable.mapper.dao.price.EcuQuotedDao;
import org.jeecg.modules.cable.entity.price.EcuQuoted;
import org.jeecg.modules.cable.service.price.EcuQuotedService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcuQuotedServiceImpl implements EcuQuotedService {
    @Resource
    EcuQuotedDao ecuQuotedDao;

    //getList
    @Override
    public List<EcuQuoted> getList(EcuQuoted record) {
        return ecuQuotedDao.getList(record);
    }

    //getCount
    @Override
    public long getCount(EcuQuoted record) {
        return ecuQuotedDao.getCount(record);
    }

    //getObject
    @Override
    public EcuQuoted getObject(EcuQuoted record) {
        return ecuQuotedDao.getObject(record);
    }

    //getLatestObject
    @Override
    public EcuQuoted getLatestObject(EcuQuoted record) {
        return ecuQuotedDao.getLatestObject(record);
    }

    //insert
    @Override
    public int insert(EcuQuoted record) {
        return ecuQuotedDao.insert(record);
    }

    //deleteByPrimaryKey
    @Override
    public int deleteByPrimaryKey(int ecuqId) {
        return ecuQuotedDao.deleteByPrimaryKey(ecuqId);
    }

    @Override
    public int update(EcuQuoted record) {
        return ecuQuotedDao.update(record);
    }
}
