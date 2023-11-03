package org.jeecg.modules.cable.service.user.impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.user.EcCompany;
import org.jeecg.modules.cable.mapper.dao.user.EcCompanyMapper;
import org.jeecg.modules.cable.service.user.EcCompanyService;
import org.springframework.stereotype.Service;

@Service
public class EcCompanyServiceImpl implements EcCompanyService {
    @Resource
    EcCompanyMapper ecCompanyMapper;

    @Override
    public EcCompany getObject(EcCompany record) {//根据EcUser获取EcCompany
        return ecCompanyMapper.getObject(record);
    }

    @Override
    public Integer insert(EcCompany record) {
        return ecCompanyMapper.insert(record);
    }

}
