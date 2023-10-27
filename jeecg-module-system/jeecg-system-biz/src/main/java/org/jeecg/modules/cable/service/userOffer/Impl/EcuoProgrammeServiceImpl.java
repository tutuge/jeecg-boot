package org.jeecg.modules.cable.service.userOffer.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.userOffer.EcuoProgramme;
import org.jeecg.modules.cable.mapper.dao.userOffer.EcuoProgrammeMapper;
import org.jeecg.modules.cable.service.userOffer.EcuoProgrammeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcuoProgrammeServiceImpl implements EcuoProgrammeService {
    @Resource
    EcuoProgrammeMapper ecuoProgrammeMapper;

    @Override
    public List<EcuoProgramme> getList(EcuoProgramme record) {
        LambdaQueryWrapper<EcuoProgramme> eq = Wrappers.lambdaQuery(EcuoProgramme.class)
                .eq(EcuoProgramme::getEcCompanyId, record.getEcCompanyId());
        return ecuoProgrammeMapper.selectList(eq);
    }

    @Override
    public EcuoProgramme getObject(EcuoProgramme record) {
        return ecuoProgrammeMapper.getObject(record);
    }

    @Override
    public Integer insert(EcuoProgramme record) {
        return ecuoProgrammeMapper.insert(record);
    }

    @Override
    public Integer update(EcuoProgramme record) {
        return ecuoProgrammeMapper.updateById(record);
    }

    @Override
    public Integer delete(EcuoProgramme record) {
        return ecuoProgrammeMapper.deleteById(record);
    }

}
