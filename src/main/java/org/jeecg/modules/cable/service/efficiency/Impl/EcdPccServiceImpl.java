package org.jeecg.modules.cable.service.efficiency.Impl;

import org.jeecg.modules.cable.mapper.dao.efficiency.EcdPccMapper;
import org.jeecg.modules.cable.entity.efficiency.EcdPcc;
import org.jeecg.modules.cable.service.efficiency.EcdPccService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class EcdPccServiceImpl implements EcdPccService {
    @Resource
    EcdPccMapper ecdPccMapper;

    @Override
    public EcdPcc getObject(EcdPcc record) {
        return ecdPccMapper.getObject(record);
    }

    @Override
    public Integer insert(EcdPcc record) {
        return ecdPccMapper.insert(record);
    }

    @Override
    public Integer delete(EcdPcc record) {
        return ecdPccMapper.delete(record);
    }

}
