package org.jeecg.modules.cable.service.userCommon.Impl;

import org.jeecg.modules.cable.mapper.dao.userCommon.EcduCompanyMapper;
import org.jeecg.modules.cable.entity.userCommon.EcduCompany;
import org.jeecg.modules.cable.service.userCommon.EcduCompanyService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcduCompanyServiceImpl implements EcduCompanyService {
    @Resource
    EcduCompanyMapper ecduCompanyMapper;


    @Override
    public List<EcduCompany> getList(EcduCompany record) {
        return ecduCompanyMapper.getList(record);
    }


    @Override
    public long getCount(EcduCompany record) {
        return ecduCompanyMapper.getCount(record);
    }


    @Override
    public EcduCompany getObject(EcduCompany record) {
        return ecduCompanyMapper.getObject(record);
    }


    @Override
    public Integer insert(EcduCompany record) {
        return ecduCompanyMapper.insert(record);
    }

    //updateByPrimaryKeySelective
    @Override
    public Integer update(EcduCompany record) {
        return ecduCompanyMapper.update(record);
    }

    @Override
    public Integer delete(EcduCompany record) {
        return ecduCompanyMapper.delete(record);
    }

    
    @Override
    public List<EcduCompany> getListGreaterThanSortId(EcduCompany record) {
        return ecduCompanyMapper.getListGreaterThanSortId(record);
    }

    //getObjectPassAbbreviationAndFullName
    @Override
    public EcduCompany getObjectPassAbbreviationAndFullName(EcduCompany record) {
        return ecduCompanyMapper.getObjectPassAbbreviationOrFullName(record);
    }


    @Override
    public EcduCompany getLatestObject(EcduCompany record) {
        return ecduCompanyMapper.getLatestObject(record);
    }

}
