package org.jeecg.modules.cable.service.systemCommon.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.cable.entity.systemCommon.EcArea;
import org.jeecg.modules.cable.mapper.dao.systemCommon.EcAreaMapper;
import org.jeecg.modules.cable.service.systemCommon.EcAreaService;
import org.springframework.stereotype.Service;

@Service
public class EcAreaServiceImpl extends ServiceImpl<EcAreaMapper, EcArea> implements EcAreaService {
    @Override
    public EcArea getByArea(String areaStr) {
        LambdaQueryWrapper<EcArea> eq = Wrappers.lambdaQuery(EcArea.class).eq(EcArea::getAreaStr, areaStr);
        return baseMapper.selectOne(eq);
    }
}
