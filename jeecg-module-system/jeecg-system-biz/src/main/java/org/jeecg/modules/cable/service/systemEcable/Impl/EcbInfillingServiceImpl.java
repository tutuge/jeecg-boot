package org.jeecg.modules.cable.service.systemEcable.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.systemEcable.EcbInfilling;
import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbInfillingMapper;
import org.jeecg.modules.cable.service.systemEcable.EcbInfillingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbInfillingServiceImpl implements EcbInfillingService {
    @Resource
    EcbInfillingMapper ecbInfillingMapper;

    @Override
    public List<EcbInfilling> getList(EcbInfilling record) {
        return ecbInfillingMapper.getList(record);
    }

    @Override
    public List<EcbInfilling> getListStart(EcbInfilling record) {
        return ecbInfillingMapper.getListStart(record);
    }

    @Override
    public long getCount() {
        return ecbInfillingMapper.getCount();
    }

    @Override
    public EcbInfilling getObject(EcbInfilling record) {
        return ecbInfillingMapper.getObject(record);
    }
}
