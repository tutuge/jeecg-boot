package org.jeecg.modules.cable.service.systemEcable.Impl;

import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbSteelBandMapper;
import org.jeecg.modules.cable.entity.systemEcable.EcbSteelBand;
import org.jeecg.modules.cable.service.systemEcable.EcbSteelbandService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbSteelbandServiceImpl implements EcbSteelbandService {
    @Resource
    EcbSteelBandMapper ecbSteelbandMapper;

    @Override
    public List<EcbSteelBand> getList(EcbSteelBand record) {//插入
        return ecbSteelbandMapper.getList(record);
    }

    @Override
    public List<EcbSteelBand> getListStart(EcbSteelBand record) {
        return ecbSteelbandMapper.getListStart(record);
    }

    @Override
    public long getCount() {
        return ecbSteelbandMapper.getCount();
    }

    @Override
    public EcbSteelBand getObject(EcbSteelBand record) {
        return ecbSteelbandMapper.getObject(record);
    }
}
