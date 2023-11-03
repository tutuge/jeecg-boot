package org.jeecg.modules.cable.service.userEcable.Impl;

import org.jeecg.modules.cable.mapper.dao.userEcable.EcbuBagMapper;
import org.jeecg.modules.cable.entity.userEcable.EcbuBag;
import org.jeecg.modules.cable.service.userEcable.EcbuBagService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbuBagServiceImpl implements EcbuBagService {
    @Resource
    EcbuBagMapper ecbuBagMapper;

    @Override
    public EcbuBag getObject(EcbuBag record) {
        return ecbuBagMapper.getObject(record);
    }

    @Override
    public Integer insert(EcbuBag record) {
        return ecbuBagMapper.insert(record);
    }

    @Override
    public Integer update(EcbuBag record) {
        return ecbuBagMapper.update(record);
    }

    @Override
    public List<EcbuBag> getList(EcbuBag record) {
        return ecbuBagMapper.getList(record);
    }

    @Override
    public Integer delete(EcbuBag record) {
        return ecbuBagMapper.delete(record);
    }
}
