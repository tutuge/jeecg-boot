package org.jeecg.modules.cable.service.systemEcable.Impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.jeecg.modules.cable.domain.materialType.MaterialTypeBo;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;
import org.jeecg.modules.cable.entity.systemOffer.EcOffer;
import org.jeecg.modules.cable.entity.systemQuality.EcqLevel;
import org.jeecg.modules.cable.mapper.dao.systemEcable.EcSilkMapper;
import org.jeecg.modules.cable.service.price.EcOfferService;
import org.jeecg.modules.cable.service.systemEcable.EcSilkService;
import org.jeecg.modules.cable.service.systemQuality.EcqLevelService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EcSilkServiceImpl implements EcSilkService {
    @Resource
    EcSilkMapper ecSilkMapper;
    @Resource
    private EcOfferService ecOfferService;
    @Resource
    private EcqLevelService ecqLevelService;

    @Override
    public List<EcSilk> getList(EcSilk record) {
        return ecSilkMapper.getList(record);
    }


    @Override
    public EcSilk getObject(EcSilk record) {
        return ecSilkMapper.getObject(record);
    }

    @Override
    public IPage<EcSilk> selectPage(Page<EcSilk> page, EcSilk ecSilk) {
        return ecSilkMapper.select(page, ecSilk);
    }

    @Override
    public Map<String, Integer> silkModelMap() {
        List<EcSilk> ecSilkModels = ecSilkMapper.selectList(Wrappers.emptyWrapper());
        Map<String, Integer> silkModelMap = ecSilkModels.stream().collect(Collectors.toMap(EcSilk::getAbbreviation, EcSilk::getEcsId));
        return silkModelMap;
    }

    @Override
    public void save(EcSilk ecSilk) {
        validSort(ecSilk);
        ecSilk.setAddTime(new Date());
        ecSilkMapper.insert(ecSilk);
    }

    @Override
    public void updateById(EcSilk record) {
        validSort(record);
        Integer ecsId = record.getEcsId();
        EcSilk ecSilk = ecSilkMapper.getObject(record);
        List<MaterialTypeBo> typesList = ecSilk.getMaterialTypesList();
        List<MaterialTypeBo> typesList1 = record.getMaterialTypesList();
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
                MaterialTypeBo type = typesList.get(i);
                MaterialTypeBo type1 = typesList1.get(i);
                if (!type.equals(type1)) {
                    change = true;
                    break;
                }
            }
        }
        if (change) {
            //判断型号系列下面是否已经有了成本库表数据
            EcqLevel ecqLevel = new EcqLevel();
            ecqLevel.setEcsId(ecsId);
            List<EcqLevel> list = ecqLevelService.getList(ecqLevel);
            for (EcqLevel level : list) {
                EcOffer offer = new EcOffer();
                offer.setEcqlId(level.getEcqlId());
                Long count = ecOfferService.getCount(offer);
                if (count > 0) {
                    throw new RuntimeException("当前型号系列下已有对应的成本库表详细数据，不可修改是否分屏以及材料顺序");
                }
            }
        }
        record.setUpdateTime(new Date());
        ecSilkMapper.updateById(record);
    }

    private void validSort(EcSilk ecSilk) {
        List<MaterialTypeBo> materialTypes = ecSilk.getMaterialTypesList();
        if (CollUtil.isNotEmpty(materialTypes)) {
            MaterialTypeBo materialType = materialTypes.get(0);
            if (materialType.getMaterialType() != 1) {
                throw new RuntimeException("导体材料请务必放到最前面");
            }
        }
    }

    @Override
    public void removeById(Integer ecsId) {
        ecSilkMapper.deleteById(ecsId);
    }

    @Override
    public EcSilk getObjectById(Integer ecsId) {
        return ecSilkMapper.selectById(ecsId);
    }
}
