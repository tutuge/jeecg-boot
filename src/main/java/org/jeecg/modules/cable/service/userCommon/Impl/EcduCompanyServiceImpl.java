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

    //getCount
    @Override
    public long getCount(EcduCompany record) {
        return ecduCompanyMapper.getCount(record);
    }

    //getObject
    @Override
    public EcduCompany getObject(EcduCompany record) {
        return ecduCompanyMapper.getObject(record);
    }

    //insert
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

    //getListGreaterThanSortId 获取大于指定序号的数据列表
    @Override
    public List<EcduCompany> getListGreaterThanSortId(EcduCompany record) {
        return ecduCompanyMapper.getListGreaterThanSortId(record);
    }

    //getObjectPassAbbreviationAndFullName
    @Override
    public EcduCompany getObjectPassAbbreviationAndFullName(EcduCompany record) {
        return ecduCompanyMapper.getObjectPassAbbreviationOrFullName(record);
    }

    //getLatestObject
    @Override
    public EcduCompany getLatestObject(EcduCompany record) {
        return ecduCompanyMapper.getLatestObject(record);
    }

}
