package org.jeecg.modules.cable.service.userEcable.Impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
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

    public void save(EcuSilk ecuSilk) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        List<EcuSilk> list = list(ecuSilk);
        if (!list.isEmpty()) {
            throw new RuntimeException("当前名称已被占用");
        }
        EcuSilk object = getObject(null);
        Integer sortId = 1;
        if (ObjectUtil.isNotNull(object)) {
            sortId = object.getSortId();
        }
        ecuSilk.setSortId(sortId);
        ecuSilk.setStartType(true);
        ecuSilk.setEcuId(sysUser.getUserId());
        ecuSilk.setAddTime(new Date());
        ecuSilk.setUpdateTime(new Date());
        insert(ecuSilk);
    }

    @Override
    public void updateById(EcuSilk record) {
        validSort(record);
        List<EcuSilk> nameList = listNotEqPrimaryId(record);
        if (!nameList.isEmpty()) {
            throw new RuntimeException("当前名称已被占用");
        }
        Integer ecusId = record.getEcusId();
        EcuSilk ecuSilk = ecuSilkMapper.selectById(ecusId);
        List<EcbuMaterialType> typesList = ecuSilk.getMaterialTypesList();
        List<EcbuMaterialType> typesList1 = record.getMaterialTypesList();
        //判断材料顺序是否一致
        boolean change = false;
        if (CollUtil.isEmpty(typesList)) {
            change = true;
        } else if (CollUtil.isEmpty(typesList1)) {
            change = true;
        } else if (typesList.size() != typesList1.size()) {
            change = true;
        } else {
            for (int i = 0; i < typesList.size(); i++) {
                EcbuMaterialType type = typesList.get(i);
                EcbuMaterialType type1 = typesList1.get(i);
                if (!type.equals(type1)) {
                    change = true;
                    break;
                }
            }
        }
        if (change) {
            //判断型号系列下面是否已经有了成本库表数据
            EcquLevel ecquLevel = new EcquLevel();
            ecquLevel.setEcusId(ecusId);
            List<EcquLevel> list = ecquLevelService.getList(ecquLevel);
            for (EcquLevel level : list) {
                EcuOffer offer = new EcuOffer();
                offer.setEcqulId(level.getEcqulId());
                Long count = ecuOfferService.getCount(offer);
                if (count > 0) {
                    throw new RuntimeException("当前型号系列下已有对应的成本库表详细数据，不可修改材料顺序");
                }
            }
        }
        record.setUpdateTime(new Date());
        ecuSilkMapper.updateById(record);
    }

    private List<EcuSilk> listNotEqPrimaryId(EcuSilk ecuSilk) {
        //查询是否有名称重复了
        LambdaQueryWrapper<EcuSilk> like = Wrappers.lambdaQuery(EcuSilk.class)
                .eq(ObjectUtil.isNotNull(ecuSilk.getCompanyId()), EcuSilk::getCompanyId, ecuSilk.getCompanyId())
                .ne(ObjectUtil.isNotNull(ecuSilk.getEcusId()), EcuSilk::getEcusId, ecuSilk.getEcusId())
                .eq(StrUtil.isNotBlank(ecuSilk.getFullName()), EcuSilk::getFullName, ecuSilk.getFullName());
        return ecuSilkMapper.selectList(like);
    }

    @Override
    public List<EcuSilk> list(EcuSilk ecuSilk) {
        //查询是否有名称重复了
        LambdaQueryWrapper<EcuSilk> like = Wrappers.lambdaQuery(EcuSilk.class)
                .eq(ObjectUtil.isNotNull(ecuSilk.getCompanyId()), EcuSilk::getCompanyId, ecuSilk.getCompanyId())
                .eq(StrUtil.isNotBlank(ecuSilk.getFullName()), EcuSilk::getFullName, ecuSilk.getFullName());
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

    private void validNameDuplicate(EcuSilk ecuSilk) {

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
