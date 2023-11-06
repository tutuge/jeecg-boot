package org.jeecg.modules.cable.service.userCommon.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.controller.userCommon.taxpoint.bo.TaxPointBaseBo;
import org.jeecg.modules.cable.entity.systemEcable.EcdTaxPoint;
import org.jeecg.modules.cable.mapper.dao.userCommon.EcdTaxPointMapper;
import org.jeecg.modules.cable.service.userCommon.EcdTaxpointService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EcdTaxpointServiceImpl implements EcdTaxpointService {
    @Resource
    EcdTaxPointMapper ecdTaxPointMapper;


    @Override
    public List<EcdTaxPoint> getList(EcdTaxPoint record) {
        return ecdTaxPointMapper.getList(record);
    }

    //getCount
    @Override
    public long getCount(EcdTaxPoint record) {
        return ecdTaxPointMapper.getCount(record);
    }

    //getObject
    @Override
    public EcdTaxPoint getObject(EcdTaxPoint record) {
        return ecdTaxPointMapper.getObject(record);
    }

    @Override
    public void insert(EcdTaxPoint record) {
        record.setAddTime(new Date());
        ecdTaxPointMapper.insert(record);
    }

    @Override
    public void update(EcdTaxPoint record) {
        record.setUpdateTime(new Date());
        ecdTaxPointMapper.updateById(record);
    }

    @Override
    public void delete(Integer ecdtId) {
        ecdTaxPointMapper.deleteById(ecdtId);
    }

}
