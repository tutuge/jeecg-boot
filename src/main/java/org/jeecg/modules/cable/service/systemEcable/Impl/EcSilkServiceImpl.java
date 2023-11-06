package org.jeecg.modules.cable.service.systemEcable.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;
import org.jeecg.modules.cable.mapper.dao.systemEcable.EcSilkMapper;
import org.jeecg.modules.cable.service.systemEcable.EcSilkService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcSilkServiceImpl extends ServiceImpl<EcSilkMapper, EcSilk> implements EcSilkService {
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
        return silkMapper.select(page,ecSilk);
    }
}
