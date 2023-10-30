package org.jeecg.modules.cable.service.user.impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.user.EcuDesc;
import org.jeecg.modules.cable.mapper.dao.user.EcuDescMapper;
import org.jeecg.modules.cable.service.user.EcuDescService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcuDescServiceImpl implements EcuDescService {
    @Resource
    EcuDescMapper ecuDescMapper;

    @Override
    public EcuDesc getObject(EcuDesc record) {
        return ecuDescMapper.getObject(record);
    }

    @Override
    public List<EcuDesc> getList(EcuDesc record) {
        return ecuDescMapper.getList(record);
    }

    @Override
    public long getCount(EcuDesc record) {
        return ecuDescMapper.getCount(record);
    }

    @Override
    public Integer insert(EcuDesc record) {
        return ecuDescMapper.insert(record);
    }

    @Override
    public Integer update(EcuDesc record) {
        return ecuDescMapper.updateById(record);
    }

    @Override
    public Integer delete(EcuDesc record) {
        return ecuDescMapper.delete(record);
    }
}
