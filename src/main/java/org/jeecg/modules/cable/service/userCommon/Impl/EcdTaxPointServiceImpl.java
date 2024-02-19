package org.jeecg.modules.cable.service.userCommon.Impl;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.systemCommon.EcdTaxPoint;
import org.jeecg.modules.cable.mapper.dao.userCommon.EcdTaxPointMapper;
import org.jeecg.modules.cable.service.userCommon.EcdTaxPointService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EcdTaxPointServiceImpl implements EcdTaxPointService {
    @Resource
    EcdTaxPointMapper ecdTaxPointMapper;

    @Override
    public List<EcdTaxPoint> selectList(EcdTaxPoint record) {
        LambdaQueryWrapper<EcdTaxPoint> eq = Wrappers.lambdaQuery(EcdTaxPoint.class)
                .eq(ObjUtil.isNotNull(record.getStartType()), EcdTaxPoint::getStartType, record.getStartType());
        return ecdTaxPointMapper.selectList(eq);
    }


    @Override
    public long getCount(EcdTaxPoint record) {
        return ecdTaxPointMapper.getCount(record);
    }


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
