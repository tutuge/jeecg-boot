package org.jeecg.modules.cable.service.userCommon.Impl;

import jakarta.annotation.Resource;
import org.jeecg.common.redis.CacheUtils;
import org.jeecg.modules.cable.constants.CustomerCacheConstant;
import org.jeecg.modules.cable.entity.userCommon.EcduCompany;
import org.jeecg.modules.cable.mapper.dao.userCommon.EcduCompanyMapper;
import org.jeecg.modules.cable.service.userCommon.EcduCompanyService;
import org.springframework.cache.annotation.Cacheable;
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


    @Cacheable(value = {CustomerCacheConstant.CUSTOMER_ECDU_COMPANY_CACHE}, key = "#companyId", unless = "#result == null ")
    @Override
    public EcduCompany getObjectByCompanyId(Integer companyId) {
        EcduCompany recordEcduCompany = new EcduCompany();
        recordEcduCompany.setEcCompanyId(companyId);
        recordEcduCompany.setDefaultType(true);
        return ecduCompanyMapper.getObject(recordEcduCompany);
    }


    @Override
    public Integer insert(EcduCompany record) {
        return ecduCompanyMapper.insert(record);
    }

    @Override
    public Integer update(EcduCompany record) {
        List<EcduCompany> list = ecduCompanyMapper.getList(record);
        for (EcduCompany  object: list){
            CacheUtils.evict(CustomerCacheConstant.CUSTOMER_ECDU_COMPANY_CACHE, object.getEcCompanyId());
        }
        return ecduCompanyMapper.updateByIdOrCompanyId(record);
    }

    @Override
    public Integer delete(EcduCompany record) {
        List<EcduCompany> list = ecduCompanyMapper.getList(record);
        for (EcduCompany company : list) {
            CacheUtils.evict(CustomerCacheConstant.CUSTOMER_ECDU_COMPANY_CACHE, company.getEcCompanyId());
        }
        return ecduCompanyMapper.deleteByIdOrCompanyId(record);
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
