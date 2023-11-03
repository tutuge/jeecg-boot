package org.jeecg.modules.cable.service.price.Impl;

import org.jeecg.modules.cable.mapper.dao.price.EcuQuotedMapper;
import org.jeecg.modules.cable.entity.price.EcuQuoted;
import org.jeecg.modules.cable.service.price.EcuQuotedService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcuQuotedServiceImpl implements EcuQuotedService {
    @Resource
    EcuQuotedMapper ecuQuotedMapper;

    //getList
    @Override
    public List<EcuQuoted> getList(EcuQuoted record) {
        return ecuQuotedMapper.getList(record);
    }

    //getCount
    @Override
    public long getCount(EcuQuoted record) {
        return ecuQuotedMapper.getCount(record);
    }

    //getObject
    @Override
    public EcuQuoted getObject(EcuQuoted record) {
        return ecuQuotedMapper.getObject(record);
    }

    //getLatestObject
    @Override
    public EcuQuoted getLatestObject(EcuQuoted record) {
        return ecuQuotedMapper.getLatestObject(record);
    }

    //insert
    @Override
    public Integer insert(EcuQuoted record) {
        return ecuQuotedMapper.insert(record);
    }

    //deleteByPrimaryKey
    @Override
    public Integer deleteByPrimaryKey(Integer ecuqId) {
        return ecuQuotedMapper.deleteByPrimaryKey(ecuqId);
    }

    @Override
    public Integer update(EcuQuoted record) {
        return ecuQuotedMapper.update(record);
    }
}
