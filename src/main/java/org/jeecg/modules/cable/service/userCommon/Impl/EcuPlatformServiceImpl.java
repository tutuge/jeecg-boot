package org.jeecg.modules.cable.service.userCommon.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.userCommon.platformCompany.vo.EcbuPCompanyVo;
import org.jeecg.modules.cable.entity.userCommon.EcbuPlatformCompany;
import org.jeecg.modules.cable.entity.userCommon.EcuPlatform;
import org.jeecg.modules.cable.mapper.dao.userCommon.EcuPlatformMapper;
import org.jeecg.modules.cable.service.userCommon.EcbuPlatformCompanyService;
import org.jeecg.modules.cable.service.userCommon.EcuPlatformService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcuPlatformServiceImpl implements EcuPlatformService {
    @Resource
    private EcuPlatformMapper ecuPlatformMapper;
    @Resource
    private EcbuPlatformCompanyService ecbuPlatformCompanyService;

    @Override
    public IPage<EcuPlatform> page(Page<EcuPlatform> page, QueryWrapper<EcuPlatform> queryWrapper) {
        return ecuPlatformMapper.selectPage(page, queryWrapper);
    }

    @Override
    public void save(EcuPlatform platform) {
        ecuPlatformMapper.insert(platform);
    }

    @Override
    public EcuPlatform getById(Integer platformId) {
        return ecuPlatformMapper.selectById(platformId);
    }

    @Override
    public boolean updateById(EcuPlatform platform) {
        return ecuPlatformMapper.updateById(platform) > 0;
    }

    @Override
    public void removeById(Integer id) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcbuPlatformCompany company = new EcbuPlatformCompany();
        company.setEcCompanyId(sysUser.getEcCompanyId());
        company.setPlatformId(id);
        List<EcbuPCompanyVo> list = ecbuPlatformCompanyService.getList(company);
        if (!list.isEmpty()) {
            throw new RuntimeException("当前数据正在被平台费率使用，无法删除");
        }
        ecuPlatformMapper.deleteById(id);
    }

    @Override
    public void removeByIds(List<String> list) {
        ecuPlatformMapper.deleteBatchIds(list);
    }

    @Override
    public List<EcuPlatform> list(QueryWrapper<EcuPlatform> queryWrapper) {
        return ecuPlatformMapper.selectList(queryWrapper);
    }

    @Override
    public EcuPlatform getOne(LambdaQueryWrapper<EcuPlatform> eq) {
        return ecuPlatformMapper.selectOne(eq);
    }
}
