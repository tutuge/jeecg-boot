package org.jeecg.modules.cable.service.userEcable.Impl;

import org.jeecg.modules.cable.mapper.dao.userEcable.EcbuSheathMapper;
import org.jeecg.modules.cable.entity.userEcable.EcbuSheath;
import org.jeecg.modules.cable.service.userEcable.EcbuSheathService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbuSheathServiceImpl implements EcbuSheathService {
    @Resource
    EcbuSheathMapper ecbuSheathMapper;

    @Override
    public EcbuSheath getObject(EcbuSheath record) {
        return ecbuSheathMapper.getObject(record);
    }

    @Override
    public Integer insert(EcbuSheath record) {
        return ecbuSheathMapper.insert(record);
    }

    @Override
    public Integer update(EcbuSheath record) {
        return ecbuSheathMapper.update(record);
    }

    @Override
    public List<EcbuSheath> getList(EcbuSheath record) {
        return ecbuSheathMapper.getList(record);
    }

    @Override
    public Integer delete(EcbuSheath record) {
        return ecbuSheathMapper.delete(record);
    }
}
