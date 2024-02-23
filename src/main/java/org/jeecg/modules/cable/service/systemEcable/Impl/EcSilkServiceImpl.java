package org.jeecg.modules.cable.service.systemEcable.Impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;
import org.jeecg.modules.cable.entity.systemEcable.EcbMaterialType;
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
    EcSilkMapper silkMapper;
    @Resource
    private EcOfferService ecOfferService;
    @Resource
    private EcqLevelService ecqLevelService;

    @Override
    public List<EcSilk> getList(EcSilk record) {
        return silkMapper.getList(record);
    }


    @Override
    public EcSilk getObject(EcSilk record) {
        return silkMapper.getObject(record);
    }

    @Override
    public IPage<EcSilk> selectPage(Page<EcSilk> page, EcSilk ecSilk) {
        return silkMapper.select(page, ecSilk);
    }

    @Override
    public Map<String, Integer> silkModelMap() {
        List<EcSilk> ecSilkModels = silkMapper.selectList(Wrappers.emptyWrapper());
        Map<String, Integer> silkModelMap = ecSilkModels.stream().collect(Collectors.toMap(EcSilk::getAbbreviation, EcSilk::getEcsId));
        return silkModelMap;
    }

    @Override
    public void save(EcSilk ecSilk) {
        //判断型号系列下面是否已经有了成本库表数据
        EcqLevel ecquLevel = new EcqLevel();
        ecquLevel.setEcsId(ecSilk.getEcsId());
        List<EcqLevel> list = ecqLevelService.getList(ecquLevel);
        for (EcqLevel level : list) {
            EcOffer offer = new EcOffer();
            offer.setEcqlId(level.getEcqlId());
            Long count = ecOfferService.getCount(offer);
            if (count > 0) {
                throw new RuntimeException("当前型号系列下已有对应的成本库表详细数据，不可修改");
            }
        }
        List<EcbMaterialType> materialTypes = ecSilk.getMaterialTypesList();
        if (CollUtil.isNotEmpty(materialTypes)) {
            EcbMaterialType materialType = materialTypes.get(0);
            if (materialType.getMaterialType() != 1) {
                throw new RuntimeException("导体材料请务必放到最前面");
            }
        }
        ecSilk.setAddTime(new Date());
        silkMapper.insert(ecSilk);
    }

    @Override
    public void updateById(EcSilk ecSilk) {
        ecSilk.setUpdateTime(new Date());
        silkMapper.updateById(ecSilk);
    }

    @Override
    public void removeById(Integer ecsId) {
        silkMapper.deleteById(ecsId);
    }
}
