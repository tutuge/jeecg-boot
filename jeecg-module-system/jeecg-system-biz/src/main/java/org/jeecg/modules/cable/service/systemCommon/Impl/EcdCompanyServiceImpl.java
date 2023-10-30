package org.jeecg.modules.cable.service.systemCommon.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.systemCommon.EcdCompany;
import org.jeecg.modules.cable.mapper.dao.systemCommon.EcdCompanyMapper;
import org.jeecg.modules.cable.service.systemCommon.EcdCompanyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcdCompanyServiceImpl implements EcdCompanyService {
    @Resource
    EcdCompanyMapper companyMapper;

    @Override
    public List<EcdCompany> getList(EcdCompany record) {
        return companyMapper.getList(record);
    }

    @Override
    public long getCount(EcdCompany record) {
        return companyMapper.getCount(record);
    }

    @Override
    public EcdCompany getObject(EcdCompany record) {
        return companyMapper.getObject(record);
    }

    @Override
    public Integer insert(EcdCompany record) {
        return companyMapper.insert(record);
    }

    @Override
    public Integer update(EcdCompany record) {
        return companyMapper.updateById(record);
    }

    @Override
    public Integer delete(EcdCompany record) {
        return companyMapper.deleteById(record);
    }
}
