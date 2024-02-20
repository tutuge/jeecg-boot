package org.jeecg.modules.cable.service.userEcable.Impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.entity.userEcable.EcbuMaterialType;
import org.jeecg.modules.cable.entity.userEcable.EcuSilk;
import org.jeecg.modules.cable.mapper.dao.userEcable.EcuSilkMapper;
import org.jeecg.modules.cable.service.userEcable.EcuSilkService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EcuSilkServiceImpl implements EcuSilkService {
    @Resource
    EcuSilkMapper ecuSilkMapper;

    @Override
    public List<EcuSilk> getList(EcuSilk record) {
        return ecuSilkMapper.getList(record);
    }


    @Override
    public EcuSilk getObject(EcuSilk record) {
        return ecuSilkMapper.getObject(record);
    }

    @Override
    public IPage<EcuSilk> selectPage(Page<EcuSilk> page, EcuSilk ecuSilk) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecCompanyId = sysUser.getEcCompanyId();
        ecuSilk.setCompanyId(ecCompanyId);
        return ecuSilkMapper.select(page, ecuSilk);
    }

    @Override
    public List<EcuSilk> getListByCompanyId(Integer ecCompanyId, Boolean startType) {
        LambdaQueryWrapper<EcuSilk> eq = Wrappers.lambdaQuery(EcuSilk.class)
                .eq(EcuSilk::getCompanyId, ecCompanyId)
                .eq(EcuSilk::getStartType, startType);
        return ecuSilkMapper.selectList(eq);
    }

    @Override
    public void insert(EcuSilk ecuSilk) {
        ecuSilkMapper.insert(ecuSilk);
    }

    @Override
    public void updateById(EcuSilk record) {
        ecuSilkMapper.updateById(record);
    }

    @Override
    public List<EcuSilk> list(EcuSilk ecuSilk) {
        LambdaQueryWrapper<EcuSilk> like = Wrappers.lambdaQuery(EcuSilk.class)
                .like(EcuSilk::getAbbreviation, ecuSilk.getAbbreviation())
                .or().like(EcuSilk::getFullName, ecuSilk.getFullName());
        return ecuSilkMapper.selectList(like);
    }

    @Override
    public void save(EcuSilk ecuSilk) {
        List<EcbuMaterialType> materialTypes = ecuSilk.getMaterialTypes();
        if (CollUtil.isNotEmpty(materialTypes)) {
            EcbuMaterialType materialType = materialTypes.get(0);
            if (materialType.getMaterialType() != 1) {
                throw new RuntimeException("导体材料请务必放到最前面");
            }
        }
        ecuSilk.setAddTime(new Date());
        ecuSilkMapper.insert(ecuSilk);
    }

    @Override
    public void removeById(EcuSilk record) {
        ecuSilkMapper.deleteById(record);
    }

    @Override
    public Map<String, Integer> silkModelMap(Integer ecCompanyId) {
        LambdaQueryWrapper<EcuSilk> eq = Wrappers.lambdaQuery(EcuSilk.class).eq(EcuSilk::getCompanyId, ecCompanyId);
        List<EcuSilk> ecSilkModels = ecuSilkMapper.selectList(eq);
        Map<String, Integer> silkModelMap = ecSilkModels.stream().collect(Collectors.toMap(EcuSilk::getAbbreviation, EcuSilk::getEcusId));
        return silkModelMap;
    }
}
