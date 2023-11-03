package org.jeecg.modules.cable.service.user.impl;

import org.jeecg.modules.cable.mapper.dao.user.EcuCodeMapper;
import org.jeecg.modules.cable.entity.user.EcuCode;
import org.jeecg.modules.cable.service.user.EcuCodeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class EcuCodeServiceImpl implements EcuCodeService {
    @Resource
    EcuCodeMapper ecuCodeMapper;

    @Override
    public EcuCode getObject(EcuCode record) {

        return ecuCodeMapper.getObject(record);
    }

    @Override
    public Integer insert(EcuCode record) {
        return ecuCodeMapper.insert(record);
    }

    //update
    @Override
    public Integer update(EcuCode record) {
        return ecuCodeMapper.update(record);
    }

}
