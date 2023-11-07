package org.jeecg.modules.cable.service.price.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.price.EcuqInput;
import org.jeecg.modules.cable.mapper.dao.price.EcuqInputMapper;
import org.jeecg.modules.cable.service.price.EcuqInputService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcuqInputServiceImpl implements EcuqInputService {
    @Resource
    EcuqInputMapper ecuqInputMapper;


    @Override
    public List<EcuqInput> getList(EcuqInput record) {
        return ecuqInputMapper.getList(record);
    }


    @Override
    public long getCount(EcuqInput record) {
        return ecuqInputMapper.getCount(record);
    }

    //getObjectPassId
    @Override
    public EcuqInput getObject(EcuqInput record) {
        return ecuqInputMapper.getObject(record);
    }


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


    @Override
    public EcuqInput getLatestObject(EcuqInput record) {
        return ecuqInputMapper.getLatestObject(record);
    }

    @Override
    public void reduceSort(Integer ecuqId, Integer sortId) {
        ecuqInputMapper.reduceSort(ecuqId, sortId);
    }
}
