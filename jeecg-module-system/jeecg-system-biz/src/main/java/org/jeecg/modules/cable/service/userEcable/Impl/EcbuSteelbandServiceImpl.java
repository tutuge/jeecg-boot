package org.jeecg.modules.cable.service.userEcable.Impl;

import org.jeecg.modules.cable.mapper.dao.userEcable.EcbuSteelbandMapper;
import org.jeecg.modules.cable.entity.userEcable.EcbuSteelband;
import org.jeecg.modules.cable.service.userEcable.EcbuSteelbandService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbuSteelbandServiceImpl implements EcbuSteelbandService {
    @Resource
    EcbuSteelbandMapper ecbuSteelbandMapper;

    @Override
    public EcbuSteelband getObject(EcbuSteelband record) {
        return ecbuSteelbandMapper.getObject(record);
    }

    @Override
    public Integer insert(EcbuSteelband record) {
        return ecbuSteelbandMapper.insert(record);
    }

    @Override
    public Integer update(EcbuSteelband record) {
        return ecbuSteelbandMapper.update(record);
    }

    @Override
    public List<EcbuSteelband> getList(EcbuSteelband record) {
        return ecbuSteelbandMapper.getList(record);
    }

    @Override
    public Integer delete(EcbuSteelband record) {
        return ecbuSteelbandMapper.delete(record);
    }
}
