package org.jeecg.modules.cable.service.systemCommon.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.jeecg.modules.cable.controller.systemCommon.platformCompany.vo.EcbPlatformCompanyVo;
import org.jeecg.modules.cable.entity.systemCommon.EcPlatform;
import org.jeecg.modules.cable.entity.systemCommon.EcbPlatformCompany;
import org.jeecg.modules.cable.mapper.dao.systemCommon.EcPlatformMapper;
import org.jeecg.modules.cable.service.systemCommon.EcPlatformService;
import org.jeecg.modules.cable.service.systemCommon.EcbPlatformCompanyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcPlatformServiceImpl implements EcPlatformService {
    @Resource
    private EcPlatformMapper ecPlatformMapper;
    @Resource
    private EcbPlatformCompanyService ecbPlatformCompanyService;

    @Override
    public List<EcPlatform> getListStart() {
        LambdaQueryWrapper<EcPlatform> eq = Wrappers.lambdaQuery(EcPlatform.class)
                .eq(EcPlatform::getStartType, true);
        return ecPlatformMapper.selectList(eq);
    }

    @Override
    public IPage<EcPlatform> page(Page<EcPlatform> page, QueryWrapper<EcPlatform> queryWrapper) {
        return ecPlatformMapper.selectPage(page, queryWrapper);
    }

    @Override
    public void save(EcPlatform platform) {
        ecPlatformMapper.insert(platform);
    }

    @Override
    public EcPlatform getById(Integer platformId) {
        return ecPlatformMapper.selectById(platformId);
    }

    @Override
    public boolean updateById(EcPlatform platform) {
        return ecPlatformMapper.updateById(platform) > 0;
    }

    @Override
    public void removeById(Integer id) {
        EcbPlatformCompany company = new EcbPlatformCompany();
        company.setPlatformId(id);
        List<EcbPlatformCompanyVo> list = ecbPlatformCompanyService.getList(company);
        if (!list.isEmpty()) {
            throw new RuntimeException("当前数据正在被平台费率使用，无法删除");
        }
        ecPlatformMapper.deleteById(id);
    }

    @Override
    public void removeByIds(List<String> list) {
        ecPlatformMapper.deleteBatchIds(list);
    }

    @Override
    public List<EcPlatform> list(QueryWrapper<EcPlatform> queryWrapper) {
        return ecPlatformMapper.selectList(queryWrapper);
    }

    @Override
    public EcPlatform getOne(LambdaQueryWrapper<EcPlatform> eq) {
        return ecPlatformMapper.selectOne(eq);
    }
}
