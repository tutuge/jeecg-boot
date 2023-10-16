package org.jeecg.modules.cable.service.userCommon.Impl;

import org.jeecg.modules.cable.mapper.dao.userCommon.EcduTaxpointDao;
import org.jeecg.modules.cable.entity.userCommon.EcduTaxpoint;
import org.jeecg.modules.cable.service.userCommon.EcduTaxpointService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcduTaxpointServiceImpl implements EcduTaxpointService {
    @Resource
    EcduTaxpointDao ecduTaxpointDao;

    //getList
    @Override
    public List<EcduTaxpoint> getList(EcduTaxpoint record) {
        return ecduTaxpointDao.getList(record);
    }

    //getCount
    @Override
    public long getCount(EcduTaxpoint record) {
        return ecduTaxpointDao.getCount(record);
    }

    //getObject
    @Override
    public EcduTaxpoint getObject(EcduTaxpoint record) {
        return ecduTaxpointDao.getObject(record);
    }

    //insert
    @Override
    public int insert(EcduTaxpoint record) {
        return ecduTaxpointDao.insert(record);
    }

    //updateByPrimaryKeySelective
    @Override
    public int updateByPrimaryKeySelective(EcduTaxpoint record) {
        return ecduTaxpointDao.updateByPrimaryKeySelective(record);
    }

    //deletePassEcCompanyIdAndEcdtId
    @Override
    public int deletePassEcCompanyIdAndEcdtId(EcduTaxpoint record) {
        return ecduTaxpointDao.deletePassEcCompanyIdAndEcdtId(record);
    }

}
