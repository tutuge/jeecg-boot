package org.jeecg.modules.cable.service.price.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.price.EcuqDesc;
import org.jeecg.modules.cable.mapper.dao.price.EcuqDescMapper;
import org.jeecg.modules.cable.service.price.EcuqDescService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class EcuqDescServiceImpl implements EcuqDescService {
    @Resource
    private EcuqDescMapper ecuqDescMapper;


    @Override
    public List<EcuqDesc> getList(EcuqDesc record) {
        return ecuqDescMapper.getList(record);
    }


    @Override
    public EcuqDesc getObject(EcuqDesc record) {
        return ecuqDescMapper.getObject(record);
    }


    @Override
    public Integer insert(EcuqDesc record) {
        record.setAddTime(new Date());
        return ecuqDescMapper.insert(record);
    }

    //deletePassEcuqiId
    @Override
    public void deletePassEcuqiId(Integer ecuqiId) {
        ecuqDescMapper.deletePassEcuqiId(ecuqiId);
    }

    @Override
    public Integer update(EcuqDesc record) {
        record.setUpdateTime(new Date());
        return ecuqDescMapper.updateRecord(record);
    }

    @Override
    public void updateConductorPriceById(Integer ecuqId, Integer ecbucId, BigDecimal cunitPrice) {
        ecuqDescMapper.updateConductorPriceById(ecuqId, ecbucId, cunitPrice);
    }
}
