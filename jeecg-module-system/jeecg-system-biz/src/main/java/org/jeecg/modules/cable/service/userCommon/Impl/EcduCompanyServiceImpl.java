package org.jeecg.modules.cable.service.userCommon.Impl;

import org.jeecg.modules.cable.mapper.dao.userCommon.EcduCompanyDao;
import org.jeecg.modules.cable.entity.userCommon.EcduCompany;
import org.jeecg.modules.cable.service.userCommon.EcduCompanyService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcduCompanyServiceImpl implements EcduCompanyService {
    @Resource
    EcduCompanyDao ecduCompanyDao;

    //getList
    @Override
    public List<EcduCompany> getList(EcduCompany record) {
        return ecduCompanyDao.getList(record);
    }

    //getCount
    @Override
    public long getCount(EcduCompany record) {
        return ecduCompanyDao.getCount(record);
    }

    //getObject
    @Override
    public EcduCompany getObject(EcduCompany record) {
        return ecduCompanyDao.getObject(record);
    }

    //insert
    @Override
    public int insert(EcduCompany record) {
        return ecduCompanyDao.insert(record);
    }

    //updateByPrimaryKeySelective
    @Override
    public int update(EcduCompany record) {
        return ecduCompanyDao.update(record);
    }

    @Override
    public int delete(EcduCompany record) {
        return ecduCompanyDao.delete(record);
    }

    //getListGreaterThanSortId 获取大于指定序号的数据列表
    @Override
    public List<EcduCompany> getListGreaterThanSortId(EcduCompany record) {
        return ecduCompanyDao.getListGreaterThanSortId(record);
    }

    //getObjectPassAbbreviationAndFullName
    @Override
    public EcduCompany getObjectPassAbbreviationAndFullName(EcduCompany record) {
        return ecduCompanyDao.getObjectPassAbbreviationOrFullName(record);
    }

    //getLatestObject
    @Override
    public EcduCompany getLatestObject(EcduCompany record) {
        return ecduCompanyDao.getLatestObject(record);
    }

}
