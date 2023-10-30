package org.jeecg.modules.cable.service.systemEcable.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.systemEcable.EcbMicatape;
import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbMicatapeMapper;
import org.jeecg.modules.cable.service.systemEcable.EcbMicatapeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbMicatapeServiceImpl implements EcbMicatapeService {
    @Resource
    EcbMicatapeMapper ecbMicatapeMapper;

    @Override
    public List<EcbMicatape> getList(EcbMicatape record) {// 插入
        return ecbMicatapeMapper.getList(record);
    }

    @Override
    public List<EcbMicatape> getListStart(EcbMicatape record) {
        return ecbMicatapeMapper.getListStart(record);
    }

    @Override
    public long getCount() {
        return ecbMicatapeMapper.getCount();
    }

    @Override
    public EcbMicatape getObject(EcbMicatape record) {
        return ecbMicatapeMapper.getObject(record);
    }
}
