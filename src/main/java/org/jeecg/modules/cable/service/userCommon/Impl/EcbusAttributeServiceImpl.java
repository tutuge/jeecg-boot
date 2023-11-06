package org.jeecg.modules.cable.service.userCommon.Impl;

import org.jeecg.modules.cable.mapper.dao.userCommon.EcbusAttributeMapper;
import org.jeecg.modules.cable.entity.userCommon.EcbusAttribute;
import org.jeecg.modules.cable.service.userCommon.EcbusAttributeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class EcbusAttributeServiceImpl implements EcbusAttributeService {
    @Resource
    EcbusAttributeMapper ecbusAttributeMapper;

    @Override
    public EcbusAttribute getObject(EcbusAttribute record) {
        return ecbusAttributeMapper.getObject(record);
    }

    @Override
    public Integer insert(EcbusAttribute record) {
        return ecbusAttributeMapper.insert(record);
    }

    @Override
    public Integer update(EcbusAttribute record) {
        return ecbusAttributeMapper.update(record);
    }


}
