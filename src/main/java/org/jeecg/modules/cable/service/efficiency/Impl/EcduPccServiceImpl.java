package org.jeecg.modules.cable.service.efficiency.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.efficiency.EcduPcc;
import org.jeecg.modules.cable.mapper.dao.efficiency.EcduPccMapper;
import org.jeecg.modules.cable.service.efficiency.EcduPccService;
import org.springframework.stereotype.Service;

@Service
public class EcduPccServiceImpl implements EcduPccService {
    @Resource
    EcduPccMapper ecduPccMapper;


    @Override
    public EcduPcc getByTypeCompany(Integer typeId, Integer companyId) {
        LambdaQueryWrapper<EcduPcc> eq = Wrappers.lambdaQuery(EcduPcc.class)
                .eq(EcduPcc::getTypeId, typeId)
                .eq(EcduPcc::getEcCompanyId, companyId);
        return ecduPccMapper.selectOne(eq);
    }


    @Override
    public Integer insert(EcduPcc record) {
        return ecduPccMapper.insert(record);
    }

    //deletePassEcCompanyIdAndTypeId
    @Override
    public Integer deleteByTypeCompany(Integer typeId, Integer companyId) {
        LambdaQueryWrapper<EcduPcc> eq = Wrappers.lambdaQuery(EcduPcc.class)
                .eq(EcduPcc::getTypeId, typeId)
                .eq(EcduPcc::getEcCompanyId, companyId);
        return ecduPccMapper.delete(eq);
    }

}
