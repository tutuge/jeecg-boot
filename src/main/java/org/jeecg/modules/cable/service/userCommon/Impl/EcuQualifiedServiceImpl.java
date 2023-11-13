package org.jeecg.modules.cable.service.userCommon.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.cable.controller.userCommon.qualified.vo.EcuQualifiedVo;
import org.jeecg.modules.cable.entity.userCommon.EcuQualified;
import org.jeecg.modules.cable.mapper.dao.userCommon.EcuQualifiedMapper;
import org.jeecg.modules.cable.service.userCommon.EcuQualifiedService;
import org.springframework.stereotype.Service;

@Service
public class EcuQualifiedServiceImpl extends ServiceImpl<EcuQualifiedMapper, EcuQualified> implements EcuQualifiedService {
    @Override
    public EcuQualifiedVo getVoById(Integer id) {
        return baseMapper.getVoById(id);
    }

    @Override
    public IPage<EcuQualifiedVo> selectPage(Page<EcuQualified> page, EcuQualified ecuQualified) {
        return baseMapper.selectPageData(page,ecuQualified);
    }
}
