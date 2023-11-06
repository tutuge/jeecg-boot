package org.jeecg.modules.cable.service.systemEcable.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.systemEcable.EcbMicaTape;
import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbMicaTapeMapper;
import org.jeecg.modules.cable.service.systemEcable.EcbMicaTapeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbMicaTapeServiceImpl implements EcbMicaTapeService {
    @Resource
    EcbMicaTapeMapper ecbMicaTapeMapper;

    @Override
    public List<EcbMicaTape> getList(EcbMicaTape record) {// 插入
        return ecbMicaTapeMapper.getList(record);
    }

    @Override
    public List<EcbMicaTape> getListStart(EcbMicaTape record) {
        return ecbMicaTapeMapper.getListStart(record);
    }

    @Override
    public long getCount() {
        return ecbMicaTapeMapper.getCount();
    }

    @Override
    public EcbMicaTape getObject(EcbMicaTape record) {
        return ecbMicaTapeMapper.getObject(record);
    }
}
