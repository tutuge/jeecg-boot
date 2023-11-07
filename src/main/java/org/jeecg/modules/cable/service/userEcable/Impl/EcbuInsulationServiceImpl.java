package org.jeecg.modules.cable.service.userEcable.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.cable.mapper.dao.userEcable.EcbuInsulationMapper;
import org.jeecg.modules.cable.entity.userEcable.EcbuInsulation;
import org.jeecg.modules.cable.service.userEcable.EcbuInsulationService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbuInsulationServiceImpl extends ServiceImpl<EcbuInsulationMapper,EcbuInsulation> implements EcbuInsulationService {
    @Resource
    EcbuInsulationMapper ecbuInsulationMapper;

    @Override
    public EcbuInsulation getObject(EcbuInsulation record) {
        return ecbuInsulationMapper.getObject(record);
    }

    @Override
    public Integer insert(EcbuInsulation record) {
        return ecbuInsulationMapper.insert(record);
    }

    @Override
    public Integer update(EcbuInsulation record) {
        return ecbuInsulationMapper.update(record);
    }

    @Override
    public List<EcbuInsulation> getList(EcbuInsulation record) {
        return ecbuInsulationMapper.getList(record);
    }

    @Override
    public Integer delete(EcbuInsulation record) {
        return ecbuInsulationMapper.delete(record);
    }
}
