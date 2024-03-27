package org.jeecg.modules.cable.service.userCommon.Impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.cable.entity.userCommon.EcuQualified;
import org.jeecg.modules.cable.mapper.dao.userCommon.EcuQualifiedMapper;
import org.jeecg.modules.cable.service.userCommon.EcuQualifiedService;
import org.springframework.stereotype.Service;

@Service
public class EcuQualifiedServiceImpl extends ServiceImpl<EcuQualifiedMapper, EcuQualified> implements EcuQualifiedService {
    @Override
    public EcuQualified getVoById(Integer id) {
        return baseMapper.selectById(id);
    }

    @Override
    public IPage<EcuQualified> selectPage(Page<EcuQualified> page, EcuQualified ecuQualified) {
        LambdaQueryWrapper<EcuQualified> query = new LambdaQueryWrapper<>();
        query.eq(ObjectUtil.isNotNull(ecuQualified.getEcCompanyId()), EcuQualified::getEcCompanyId, ecuQualified.getEcCompanyId());
        return baseMapper.selectPage(page, query);
    }
}
