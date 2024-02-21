package org.jeecg.modules.cable.service.userEcable.Impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import jakarta.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.redis.CacheUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.constants.CustomerCacheConstant;
import org.jeecg.modules.cable.controller.userEcable.SilkModel.vo.SilkModelVo;
import org.jeecg.modules.cable.domain.material.SilkModelBo;
import org.jeecg.modules.cable.entity.userEcable.EcbuMaterialType;
import org.jeecg.modules.cable.entity.userEcable.EcuSilkModel;
import org.jeecg.modules.cable.mapper.dao.userEcable.EcuSilkModelMapper;
import org.jeecg.modules.cable.service.userEcable.EcbuMaterialTypeService;
import org.jeecg.modules.cable.service.userEcable.EcuSilkModelService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EcuSilkModelServiceImpl implements EcuSilkModelService {
    @Resource
    EcuSilkModelMapper ecuSilkModelMapper;
    @Resource
    private EcbuMaterialTypeService ecbuMaterialTypeService;

    @Override
    public IPage<SilkModelVo> selectPage(Page<EcuSilkModel> page, EcuSilkModel ecuSilkModel) {

        IPage<SilkModelVo> iPage = ecuSilkModelMapper.selectPageData(page, ecuSilkModel);
        List<SilkModelVo> records = iPage.getRecords();
        convert(records);
        return iPage;
    }

    private void convert(List<SilkModelVo> records) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecCompanyId = sysUser.getEcCompanyId();
        //查询所有材料
        EcbuMaterialType type = new EcbuMaterialType();
        type.setStartType(true);
        type.setEcCompanyId(ecCompanyId);
        List<EcbuMaterialType> list = ecbuMaterialTypeService.getList(type);
        //循环处理
        for (SilkModelVo vo : records) {
            List<SilkModelBo> bos = new ArrayList<>();
            for (EcbuMaterialType mt : list) {
                bos.add(new SilkModelBo(mt.getId(), mt.getFullName(), false));
            }
            List<SilkModelBo> materialUseList = vo.getMaterialUseList();
            if (CollUtil.isNotEmpty(materialUseList)) {
                for (SilkModelBo b : bos) {
                    for (SilkModelBo bo : materialUseList) {
                        if (Objects.equals(b.getId(), bo.getId())) {
                            b.setUse(bo.getUse());
                        }
                    }
                }
                vo.setMaterialUseList(bos);
            } else {
                vo.setMaterialUseList(bos);
            }
        }
    }

    @Override
    public SilkModelVo getVoById(Integer id) {
        SilkModelVo voById = ecuSilkModelMapper.getVoById(id);
        if (ObjUtil.isNotNull(voById)) {
            ArrayList<SilkModelVo> silkModelVos = Lists.newArrayList(voById);
            convert(silkModelVos);
            return silkModelVos.get(0);
        }
        return null;
    }

    @Override
    public List<EcuSilkModel> listByIds(Set<Integer> ids) {
        LambdaQueryWrapper<EcuSilkModel> in = Wrappers.lambdaQuery(EcuSilkModel.class)
                .in(EcuSilkModel::getEcusmId, ids);
        return ecuSilkModelMapper.selectList(in);
    }

    @Cacheable(value = {CustomerCacheConstant.CUSTOMER_SILK_MODEL_CACHE}, key = "#silkModelId", unless = "#result == null ")
    @Override
    public EcuSilkModel getObjectById(Integer silkModelId) {
        return ecuSilkModelMapper.selectById(silkModelId);
    }

    @Override
    public void insert(EcuSilkModel ecuSilkModel) {
        ecuSilkModel.setAddTime(new Date());
        ecuSilkModelMapper.insert(ecuSilkModel);
    }

    @CacheEvict(value = {CustomerCacheConstant.CUSTOMER_SILK_MODEL_CACHE}, key = "#ecuSilkModel.ecusmId")
    @Override
    public boolean updateById(EcuSilkModel ecuSilkModel) {
        ecuSilkModel.setUpdateTime(new Date());
        return ecuSilkModelMapper.updateById(ecuSilkModel) > 0;
    }

    @CacheEvict(value = {CustomerCacheConstant.CUSTOMER_SILK_MODEL_CACHE}, key = "#id")
    @Override
    public void removeById(String id) {
        ecuSilkModelMapper.deleteById(id);
    }

    @Override
    public void removeByIds(List<String> list) {
        for (String id : list) {
            CacheUtils.evict(CustomerCacheConstant.CUSTOMER_SILK_MODEL_CACHE, id);
        }
        ecuSilkModelMapper.deleteBatchIds(list);
    }

    @Override
    public List<EcuSilkModel> list(QueryWrapper<EcuSilkModel> queryWrapper) {
        return ecuSilkModelMapper.selectList(queryWrapper);
    }

    @Override
    public Map<String, Integer> silkModelMap(Integer silkId, Integer ecCompanyId) {
        LambdaQueryWrapper<EcuSilkModel> eq = Wrappers.lambdaQuery(EcuSilkModel.class).eq(EcuSilkModel::getEcuSilkId, silkId);
        List<EcuSilkModel> ecSilkModels = ecuSilkModelMapper.selectList(eq);
        Map<String, Integer> silkModelMap = ecSilkModels.stream().collect(Collectors.toMap(EcuSilkModel::getFullName, EcuSilkModel::getEcusmId));
        return silkModelMap;
    }

    @Override
    public List<EcuSilkModel> queryByName(String name, Integer ecCompanyId) {

        LambdaQueryWrapper<EcuSilkModel> like = Wrappers.lambdaQuery(EcuSilkModel.class)
                .and(wrapper -> wrapper.like(EcuSilkModel::getAbbreviation, name)
                        .or()
                        .like(EcuSilkModel::getFullName, name))
                .eq(EcuSilkModel::getCompanyId, ecCompanyId);
        return ecuSilkModelMapper.selectList(like);
    }
}
