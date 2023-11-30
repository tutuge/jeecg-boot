package org.jeecg.modules.cable.service.systemEcable.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;
import org.jeecg.modules.cable.mapper.dao.systemEcable.EcSilkMapper;
import org.jeecg.modules.cable.service.systemEcable.EcSilkService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EcSilkServiceImpl implements EcSilkService {
    @Resource
    EcSilkMapper silkMapper;

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
