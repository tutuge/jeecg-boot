package org.jeecg.modules.cable.service.userCommon.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.controller.userCommon.pcompany.vo.EcbuPCompanyVo;
import org.jeecg.modules.cable.entity.userCommon.EcbuPlatformCompany;
import org.jeecg.modules.cable.mapper.dao.userCommon.EcbuPlatformCompanyMapper;
import org.jeecg.modules.cable.service.userCommon.EcbuPlatformcompanyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbuPlatformCompanyServiceImpl implements EcbuPlatformcompanyService {
    @Resource
    EcbuPlatformCompanyMapper ecbuPlatformCompanyMapper;


    @Override
    public List<EcbuPCompanyVo> getList(EcbuPlatformCompany record) {
        return ecbuPlatformCompanyMapper.getList(record);
    }


    @Override
    public long getCount(EcbuPlatformCompany record) {
        return ecbuPlatformCompanyMapper.getCount(record);
    }


    @Override
    public EcbuPCompanyVo getObject(EcbuPlatformCompany record) {
        return ecbuPlatformCompanyMapper.getObject(record);
    }

    @Override
    public Integer insert(EcbuPlatformCompany record) {
        return ecbuPlatformCompanyMapper.insert(record);
    }

    @Override
    public Integer update(EcbuPlatformCompany record) {
        return ecbuPlatformCompanyMapper.updateById(record);
    }

    @Override
    public Integer delete(EcbuPlatformCompany record) {
        return ecbuPlatformCompanyMapper.deleteRecord(record);
    }


    @Override
    public List<EcbuPlatformCompany> getListGreaterThanSortId(EcbuPlatformCompany record) {
        return ecbuPlatformCompanyMapper.getListGreaterThanSortId(record);
    }

    @Override
    public EcbuPlatformCompany getObjectPassPcName(EcbuPlatformCompany record) {
        return ecbuPlatformCompanyMapper.getObjectPassPcName(record);
    }


    @Override
    public EcbuPlatformCompany getLatestObject(EcbuPlatformCompany record) {
        return ecbuPlatformCompanyMapper.getLatestObject(record);
    }

}
