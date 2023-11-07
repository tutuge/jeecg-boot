package org.jeecg.modules.cable.service.userEcable.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.userEcable.EcbuShield;
import org.jeecg.modules.cable.mapper.dao.userEcable.EcbuShieldMapper;
import org.jeecg.modules.cable.service.userEcable.EcbuShieldService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbuShieldServiceImpl extends ServiceImpl<EcbuShieldMapper, EcbuShield> implements EcbuShieldService {
    @Resource
    EcbuShieldMapper ecbuShieldMapper;

    @Override
    public EcbuShield getObject(EcbuShield record) {
        return ecbuShieldMapper.getObject(record);
    }

    @Override
    public Integer insert(EcbuShield record) {
        return ecbuShieldMapper.insert(record);
    }

    @Override
    public Integer update(EcbuShield record) {
        return ecbuShieldMapper.update(record);
    }

    @Override
    public List<EcbuShield> getList(EcbuShield record) {
        return ecbuShieldMapper.getList(record);
    }

    @Override
    public Integer delete(EcbuShield record) {
        return ecbuShieldMapper.delete(record);
    }
}
