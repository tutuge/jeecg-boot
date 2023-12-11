package org.jeecg.modules.cable.service.systemCommon.Impl;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.systemCommon.EcblUnit;
import org.jeecg.modules.cable.mapper.dao.systemCommon.EcblUnitMapper;
import org.jeecg.modules.cable.service.systemCommon.EcblUnitService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcblUnitServiceImpl implements EcblUnitService {
    @Resource
    private EcblUnitMapper ecblUnitMapper;

    @Override
    public List<EcblUnit> getList(EcblUnit record) {
        return ecblUnitMapper.getList(record);
    }

    @Override
    public long getCount(EcblUnit record) {
        return ecblUnitMapper.getCount(record);
    }

    @Override
    public EcblUnit getObject(EcblUnit record) {
        return ecblUnitMapper.getObject(record);
    }

    @Override
    public Integer insert(EcblUnit record) {
        return ecblUnitMapper.insert(record);
    }

    @Override
    public Integer update(EcblUnit record) {
        return ecblUnitMapper.updateById(record);
    }

    @Override
    public Integer delete(EcblUnit record) {
        return ecblUnitMapper.deleteById(record);
    }

    @Override
    public void updateDefault(EcblUnit record) {
        LambdaUpdateWrapper<EcblUnit> set = Wrappers.lambdaUpdate(EcblUnit.class)
                .eq(ObjUtil.isNotNull(record.getEcbluId()), EcblUnit::getEcbluId, record.getEcbluId())
                .set(EcblUnit::getDefaultType, record.getDefaultType());
        ecblUnitMapper.update(set);
    }
}
