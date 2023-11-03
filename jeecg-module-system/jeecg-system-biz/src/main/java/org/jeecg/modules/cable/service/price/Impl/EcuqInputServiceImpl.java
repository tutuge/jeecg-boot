package org.jeecg.modules.cable.service.price.Impl;

import org.jeecg.modules.cable.mapper.dao.price.EcuqInputMapper;
import org.jeecg.modules.cable.entity.price.EcuqInput;
import org.jeecg.modules.cable.service.price.EcuqInputService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcuqInputServiceImpl implements EcuqInputService {
    @Resource
    EcuqInputMapper ecuqInputMapper;

    //getList
    @Override
    public List<EcuqInput> getList(EcuqInput record) {
        return ecuqInputMapper.getList(record);
    }

    //getCount
    @Override
    public long getCount(EcuqInput record) {
        return ecuqInputMapper.getCount(record);
    }

    //getObjectPassId
    @Override
    public EcuqInput getObject(EcuqInput record) {
        return ecuqInputMapper.getObject(record);
    }

    //insert
    @Override
    public Integer insert(EcuqInput record) {
        return ecuqInputMapper.insert(record);
    }

    //getListGreaterThanSortId
    @Override
    public List<EcuqInput> getListGreaterThanSortId(EcuqInput record) {
        return ecuqInputMapper.getListGreaterThanSortId(record);
    }

    @Override
    public Integer delete(EcuqInput record) {
        return ecuqInputMapper.delete(record);
    }

    @Override
    public Integer update(EcuqInput record) {
        return ecuqInputMapper.update(record);
    }

    //getLatestObject
    @Override
    public EcuqInput getLatestObject(EcuqInput record) {
        return ecuqInputMapper.getLatestObject(record);
    }
}
