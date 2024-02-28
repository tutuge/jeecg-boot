package org.jeecg.modules.cable.service.systemDelivery.Impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.jeecg.modules.cable.controller.systemEcable.SilkModel.vo.EcSilkModelVo;
import org.jeecg.modules.cable.domain.material.SilkModelBo;
import org.jeecg.modules.cable.entity.systemEcable.EcSilkModel;
import org.jeecg.modules.cable.entity.systemEcable.EcbMaterialType;
import org.jeecg.modules.cable.mapper.dao.systemEcable.EcSilkModelMapper;
import org.jeecg.modules.cable.service.systemDelivery.EcSilkModelService;
import org.jeecg.modules.cable.service.systemDelivery.EcbMaterialTypeService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EcSilkModelServiceImpl implements EcSilkModelService {
    @Resource
    EcSilkModelMapper ecSilkModelMapper;
    @Resource
    private EcbMaterialTypeService ecbMaterialTypeService;

    @Override
    public IPage<EcSilkModelVo> selectPageData(Page<EcSilkModel> page, EcSilkModel ecSilkModel) {
        IPage<EcSilkModelVo> iPage = ecSilkModelMapper.selectPageData(page, ecSilkModel);
        List<EcSilkModelVo> records = iPage.getRecords();
        convert(records);
        return iPage;
    }

    private void convert(List<EcSilkModelVo> records) {
        //查询所有材料类型
        EcbMaterialType type = new EcbMaterialType();
        type.setStartType(true);
        List<EcbMaterialType> list = ecbMaterialTypeService.getList(type);
        //循环处理
        for (EcSilkModelVo vo : records) {
            List<SilkModelBo> bos = new ArrayList<>();
            for (EcbMaterialType mt : list) {
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
    public EcSilkModelVo getVoById(Integer id) {
        return ecSilkModelMapper.getVoById(id);
    }

    @Override
    public List<EcSilkModel> selectListBySilkId(Integer ecsId) {
        LambdaQueryWrapper<EcSilkModel> eq = Wrappers.lambdaQuery(EcSilkModel.class)
                .eq(EcSilkModel::getEcSilkId, ecsId);
        return ecSilkModelMapper.selectList(eq);
    }

    @Override
    public void save(EcSilkModel ecSilkModel) {
        ecSilkModel.setAddTime(new Date());
        ecSilkModelMapper.insert(ecSilkModel);
    }

    @Override
    public EcSilkModel getById(Integer ecSilkId) {
        return ecSilkModelMapper.selectById(ecSilkId);
    }

    @Override
    public boolean updateById(EcSilkModel ecSilkModel) {
        ecSilkModel.setUpdateTime(new Date());
        return ecSilkModelMapper.updateById(ecSilkModel) > 0;
    }

    @Override
    public void removeById(Integer id) {
        ecSilkModelMapper.deleteById(id);
    }

    @Override
    public List<EcSilkModel> list(QueryWrapper<EcSilkModel> queryWrapper) {
        return ecSilkModelMapper.selectList(queryWrapper);
    }

    @Override
    public void removeByIds(List<String> list) {
        ecSilkModelMapper.deleteBatchIds(list);
    }

    @Override
    public Map<String, Integer> silkModelMap(Integer silkId) {
        LambdaQueryWrapper<EcSilkModel> eq = Wrappers.lambdaQuery(EcSilkModel.class).eq(EcSilkModel::getEcSilkId, silkId);
        List<EcSilkModel> ecSilkModels = ecSilkModelMapper.selectList(eq);
        Map<String, Integer> silkModelMap = ecSilkModels.stream().collect(Collectors.toMap(EcSilkModel::getFullName, EcSilkModel::getEcsmId));
        return silkModelMap;
    }
}
