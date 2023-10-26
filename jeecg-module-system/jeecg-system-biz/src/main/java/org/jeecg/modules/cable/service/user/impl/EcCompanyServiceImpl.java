package org.jeecg.modules.cable.service.user.impl;

import org.jeecg.modules.cable.mapper.dao.user.EcCompanyDao;
import org.jeecg.modules.cable.entity.user.EcCompany;
import org.jeecg.modules.cable.service.user.EcCompanyService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class EcCompanyServiceImpl implements EcCompanyService {
    @Resource
    EcCompanyDao ecCompanyDao;

    @Override
    public EcCompany getObject(EcCompany record) {//根据EcUser获取EcCompany
        return ecCompanyDao.getObject(record);
    }

    @Override
    public Integer insert(EcCompany record) {
        return ecCompanyDao.insert(record);
    }

}
