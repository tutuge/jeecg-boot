package org.jeecg.modules.cable.service.systemCommon.Impl;

import org.jeecg.modules.cable.mapper.dao.systemCommon.EcdCompanyDao;
import org.jeecg.modules.cable.entity.systemCommon.EcdCompany;
import org.jeecg.modules.cable.service.systemCommon.EcdCompanyService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcdCompanyServiceImpl implements EcdCompanyService {
    @Resource
    EcdCompanyDao ecdCompanyDao;

    @Override
    public List<EcdCompany> getList(EcdCompany record) {
        return ecdCompanyDao.getList(record);
    }

    @Override
    public long getCount(EcdCompany record) {
        return ecdCompanyDao.getCount(record);
    }

    @Override
    public EcdCompany getObject(EcdCompany record) {
        return ecdCompanyDao.getObject(record);
    }

    @Override
    public Integer insert(EcdCompany record) {
        return ecdCompanyDao.insert(record);
    }

    @Override
    public Integer update(EcdCompany record) {
        return ecdCompanyDao.update(record);
    }

    @Override
    public Integer delete(EcdCompany record) {
        return ecdCompanyDao.delete(record);
    }
}
