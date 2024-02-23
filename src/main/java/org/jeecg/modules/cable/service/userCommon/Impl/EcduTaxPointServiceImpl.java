package org.jeecg.modules.cable.service.userCommon.Impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.userCommon.EcduTaxPoint;
import org.jeecg.modules.cable.mapper.dao.userCommon.EcduTaxPointMapper;
import org.jeecg.modules.cable.service.userCommon.EcduTaxPointService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcduTaxPointServiceImpl implements EcduTaxPointService {
    @Resource
    private EcduTaxPointMapper ecduTaxPointMapper;


    @Override
    public List<EcduTaxPoint> getList(EcduTaxPoint record) {
        return ecduTaxPointMapper.getList(record);
    }


    @Override
    public long getCount(EcduTaxPoint record) {
        return ecduTaxPointMapper.getCount(record);
    }


    @Override
    public EcduTaxPoint getObject(EcduTaxPoint record) {
        return ecduTaxPointMapper.getObject(record);
    }


    @Override
    public Integer insert(EcduTaxPoint record) {
        return ecduTaxPointMapper.insert(record);
    }


    @Override
    public Integer updateByPrimaryKeySelective(EcduTaxPoint record) {
        return ecduTaxPointMapper.updateById(record);
    }

    //deletePassEcCompanyIdAndEcdtId
    @Override
    public Integer deletePassEcCompanyIdAndEcdtId(EcduTaxPoint record) {
        return ecduTaxPointMapper.deletePassEcCompanyIdAndEcdtId(record);
    }

    @Override
    public void deletePassEcCompanyId(Integer ecCompanyId) {
        ecduTaxPointMapper.delete(Wrappers.lambdaQuery(EcduTaxPoint.class).eq(EcduTaxPoint::getEcCompanyId, ecCompanyId));
    }

}
