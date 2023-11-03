package org.jeecg.modules.cable.service.userCommon.Impl;

import org.jeecg.modules.cable.mapper.dao.userCommon.EcduTaxPointMapper;
import org.jeecg.modules.cable.entity.userCommon.EcduTaxPoint;
import org.jeecg.modules.cable.service.userCommon.EcduTaxpointService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcduTaxpointServiceImpl implements EcduTaxpointService {
    @Resource
    EcduTaxPointMapper ecduTaxpointMapper;

    //getList
    @Override
    public List<EcduTaxPoint> getList(EcduTaxPoint record) {
        return ecduTaxpointMapper.getList(record);
    }

    //getCount
    @Override
    public long getCount(EcduTaxPoint record) {
        return ecduTaxpointMapper.getCount(record);
    }

    //getObject
    @Override
    public EcduTaxPoint getObject(EcduTaxPoint record) {
        return ecduTaxpointMapper.getObject(record);
    }

    //insert
    @Override
    public Integer insert(EcduTaxPoint record) {
        return ecduTaxpointMapper.insert(record);
    }

    //updateByPrimaryKeySelective
    @Override
    public Integer updateByPrimaryKeySelective(EcduTaxPoint record) {
        return ecduTaxpointMapper.updateByPrimaryKeySelective(record);
    }

    //deletePassEcCompanyIdAndEcdtId
    @Override
    public Integer deletePassEcCompanyIdAndEcdtId(EcduTaxPoint record) {
        return ecduTaxpointMapper.deletePassEcCompanyIdAndEcdtId(record);
    }

}
