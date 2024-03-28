package org.jeecg.modules.cable.service.userCommon.Impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.cable.entity.userCommon.EcuContract;
import org.jeecg.modules.cable.entity.userCommon.EcuContract;
import org.jeecg.modules.cable.mapper.dao.userCommon.EcuContractMapper;
import org.jeecg.modules.cable.mapper.dao.userCommon.EcuQualifiedMapper;
import org.jeecg.modules.cable.service.userCommon.EcuContractService;
import org.jeecg.modules.cable.service.userCommon.EcuQualifiedService;
import org.springframework.stereotype.Service;

@Service
public class EcuContractServiceImpl extends ServiceImpl<EcuContractMapper, EcuContract> implements EcuContractService {
    @Override
    public EcuContract getVoById(Integer id) {
        return baseMapper.selectById(id);
    }

    @Override
    public IPage<EcuContract> selectPage(Page<EcuContract> page, EcuContract ecuQualified) {
        LambdaQueryWrapper<EcuContract> query = new LambdaQueryWrapper<>();
        query.eq(ObjectUtil.isNotNull(ecuQualified.getEcCompanyId()), EcuContract::getEcCompanyId, ecuQualified.getEcCompanyId());
        return baseMapper.selectPage(page, query);
    }
}
