package org.jeecg.modules.cable.service.userEcable.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.jeecg.common.redis.CacheUtils;
import org.jeecg.modules.cable.constants.CustomerCacheConstant;
import org.jeecg.modules.cable.controller.userEcable.SilkModel.vo.SilkModelVo;
import org.jeecg.modules.cable.entity.userEcable.EcuSilkModel;
import org.jeecg.modules.cable.mapper.dao.userEcable.EcuSilkModelMapper;
import org.jeecg.modules.cable.service.userEcable.EcuSilkModelService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EcuSilkModelServiceImpl implements EcuSilkModelService {
    @Resource
    EcuSilkModelMapper ecuSilkModelMapper;

    @Override
    public IPage<SilkModelVo> selectPage(Page<EcuSilkModel> page, EcuSilkModel ecuSilkModel) {
        return ecuSilkModelMapper.selectPageData(page, ecuSilkModel);
    }

    @Override
    public SilkModelVo getVoById(Integer id) {
        SilkModelVo voById = ecuSilkModelMapper.getVoById(id);
        return voById;
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
}
