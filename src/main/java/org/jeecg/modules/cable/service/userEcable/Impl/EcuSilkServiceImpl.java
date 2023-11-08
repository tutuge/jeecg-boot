package org.jeecg.modules.cable.service.userEcable.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.userEcable.EcuSilk;
import org.jeecg.modules.cable.mapper.dao.userEcable.EcuSilkMapper;
import org.jeecg.modules.cable.service.userEcable.EcuSilkService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcuSilkServiceImpl extends ServiceImpl<EcuSilkMapper, EcuSilk> implements EcuSilkService {
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
}
