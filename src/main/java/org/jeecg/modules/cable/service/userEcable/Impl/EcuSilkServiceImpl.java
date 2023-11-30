package org.jeecg.modules.cable.service.userEcable.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.userEcable.EcuSilk;
import org.jeecg.modules.cable.mapper.dao.userEcable.EcuSilkMapper;
import org.jeecg.modules.cable.service.userEcable.EcuSilkService;
import org.springframework.stereotype.Service;

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
