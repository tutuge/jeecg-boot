package org.jeecg.modules.cable.service.userEcable.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.userEcable.EcbuInfilling;
import org.jeecg.modules.cable.mapper.dao.userEcable.EcbuInfillingMapper;
import org.jeecg.modules.cable.service.userEcable.EcbuInfillingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbuInfillingServiceImpl extends ServiceImpl<EcbuInfillingMapper, EcbuInfilling> implements EcbuInfillingService {
    @Resource
    EcbuInfillingMapper ecbuInfillingMapper;

    @Override
    public EcbuInfilling getObject(EcbuInfilling record) {
        return ecbuInfillingMapper.getObject(record);
    }


    @Override
    public Integer insert(EcbuInfilling record) {
        return ecbuInfillingMapper.insert(record);
    }

    @Override
    public Integer update(EcbuInfilling record) {
        return ecbuInfillingMapper.update(record);
    }

    @Override
    public List<EcbuInfilling> getList(EcbuInfilling record) {
        return ecbuInfillingMapper.getList(record);
    }

    @Override
    public Integer delete(EcbuInfilling record) {
        return ecbuInfillingMapper.delete(record);
    }
}
