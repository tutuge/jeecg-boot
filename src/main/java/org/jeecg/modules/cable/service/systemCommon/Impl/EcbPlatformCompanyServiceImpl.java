package org.jeecg.modules.cable.service.systemCommon.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.controller.systemCommon.pcompany.vo.EcbPlatformCompanyVo;
import org.jeecg.modules.cable.entity.systemCommon.EcbPlatformCompany;
import org.jeecg.modules.cable.mapper.dao.systemCommon.EcbPlatformCompanyMapper;
import org.jeecg.modules.cable.service.systemCommon.EcbPlatformCompanyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbPlatformCompanyServiceImpl implements EcbPlatformCompanyService {
    @Resource
    EcbPlatformCompanyMapper ecbPlatformCompanyMapper;

    @Override
    public List<EcbPlatformCompanyVo> getList(EcbPlatformCompany record) {
        return ecbPlatformCompanyMapper.getList(record);
    }

    @Override
    public long getCount(EcbPlatformCompany record) {
        return ecbPlatformCompanyMapper.getCount(record);
    }

    @Override
    public EcbPlatformCompanyVo getObject(EcbPlatformCompany record) {
        return ecbPlatformCompanyMapper.getObject(record);
    }

    @Override
    public Integer insert(EcbPlatformCompany record) {
        return ecbPlatformCompanyMapper.insert(record);
    }

    @Override
    public Integer update(EcbPlatformCompany record) {
        return ecbPlatformCompanyMapper.updateById(record);
    }

    @Override
    public Integer delete(EcbPlatformCompany record) {
        return ecbPlatformCompanyMapper.deleteById(record);
    }
}
