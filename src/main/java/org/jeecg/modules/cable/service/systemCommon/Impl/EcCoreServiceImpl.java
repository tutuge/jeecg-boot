package org.jeecg.modules.cable.service.systemCommon.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.cable.entity.systemCommon.EcCore;
import org.jeecg.modules.cable.mapper.dao.systemCommon.EcCoreMapper;
import org.jeecg.modules.cable.service.systemCommon.EcCoreService;
import org.springframework.stereotype.Service;

@Service
public class EcCoreServiceImpl extends ServiceImpl<EcCoreMapper, EcCore> implements EcCoreService {
    @Override
    public EcCore getByCore(String coreStr) {
        LambdaQueryWrapper<EcCore> eq = Wrappers.lambdaQuery(EcCore.class).eq(EcCore::getCoreStr, coreStr);
        return baseMapper.selectOne(eq);
    }
}
