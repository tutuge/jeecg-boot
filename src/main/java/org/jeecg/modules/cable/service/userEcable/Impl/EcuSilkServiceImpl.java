package org.jeecg.modules.cable.service.userEcable.Impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.entity.userEcable.EcbuMaterialType;
import org.jeecg.modules.cable.entity.userEcable.EcuSilk;
import org.jeecg.modules.cable.entity.userOffer.EcuOffer;
import org.jeecg.modules.cable.entity.userQuality.EcquLevel;
import org.jeecg.modules.cable.mapper.dao.userEcable.EcuSilkMapper;
import org.jeecg.modules.cable.service.userEcable.EcbuMaterialTypeService;
import org.jeecg.modules.cable.service.userEcable.EcuSilkService;
import org.jeecg.modules.cable.service.userOffer.EcuOfferService;
import org.jeecg.modules.cable.service.userQuality.EcquLevelService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EcuSilkServiceImpl implements EcuSilkService {
    @Resource
    EcuSilkMapper ecuSilkMapper;
    @Resource
    private EcbuMaterialTypeService ecbuMaterialTypeService;//材料类型
    @Resource
    private EcuOfferService ecuOfferService;
    @Resource
    private EcquLevelService ecquLevelService;

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
        validSort(ecuSilk);
        ecuSilk.setAddTime(new Date());
        ecuSilkMapper.insert(ecuSilk);
    }

    @Override
    public void updateById(EcuSilk record) {
        //判断型号系列下面是否已经有了成本库表数据
        EcquLevel ecquLevel = new EcquLevel();
        ecquLevel.setEcusId(record.getEcusId());
        List<EcquLevel> list = ecquLevelService.getList(ecquLevel);
        for (EcquLevel level : list) {
            EcuOffer offer = new EcuOffer();
            offer.setEcqulId(level.getEcqulId());
            Long count = ecuOfferService.getCount(offer);
            if (count > 0) {
                throw new RuntimeException("当前型号系列下已有对应的成本库表详细数据，不可修改");
            }
        }
        validSort(record);
        ecuSilkMapper.updateById(record);
    }

    @Override
    public List<EcuSilk> list(EcuSilk ecuSilk) {
        LambdaQueryWrapper<EcuSilk> like = Wrappers.lambdaQuery(EcuSilk.class)
                .eq(StrUtil.isNotBlank(ecuSilk.getAbbreviation()), EcuSilk::getAbbreviation, ecuSilk.getAbbreviation())
                .or().eq(StrUtil.isNotBlank(ecuSilk.getFullName()), EcuSilk::getFullName, ecuSilk.getFullName());
        return ecuSilkMapper.selectList(like);
    }

    private void validSort(EcuSilk ecuSilk) {
        List<EcbuMaterialType> materialTypes = ecuSilk.getMaterialTypesList();
        if (CollUtil.isNotEmpty(materialTypes)) {
            EcbuMaterialType materialType = materialTypes.get(0);
            if (materialType.getMaterialType() != 1) {
                throw new RuntimeException("导体材料请务必放到最前面");
            }
        }
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
