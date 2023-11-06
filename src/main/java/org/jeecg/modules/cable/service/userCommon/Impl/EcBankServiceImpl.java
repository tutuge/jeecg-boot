package org.jeecg.modules.cable.service.userCommon.Impl;

import org.jeecg.modules.cable.mapper.dao.userCommon.EcBankMapper;
import org.jeecg.modules.cable.entity.userCommon.EcBank;
import org.jeecg.modules.cable.service.userCommon.EcBankService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class EcBankServiceImpl implements EcBankService {
    @Resource
    EcBankMapper ecBankMapper;

    @Override
    public EcBank getObject(EcBank record) {
        return ecBankMapper.getObject(record);
    }

}
