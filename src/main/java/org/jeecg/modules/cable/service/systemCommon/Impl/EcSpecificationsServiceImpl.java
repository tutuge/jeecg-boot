package org.jeecg.modules.cable.service.systemCommon.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.systemCommon.EcSpecifications;
import org.jeecg.modules.cable.mapper.dao.systemCommon.EcSpecificationsMapper;
import org.jeecg.modules.cable.service.systemCommon.EcSpecificationsService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EcSpecificationsServiceImpl extends ServiceImpl<EcSpecificationsMapper, EcSpecifications> implements EcSpecificationsService {
    @Resource
    private EcSpecificationsMapper specificationsMapper;

    @Override
    public boolean save(EcSpecifications entity) {
        entity.setAddTime(new Date());
        entity.setUpdateTime(new Date());
        baseMapper.insert(entity);
        return true;
    }

    @Override
    public List<EcSpecifications> selectListByName(Boolean special, String abbreviation) {
        LambdaQueryWrapper<EcSpecifications> eq = Wrappers.lambdaQuery(EcSpecifications.class)
                .eq(EcSpecifications::getSpecial, special)
                .eq(EcSpecifications::getAbbreviation, abbreviation);
        return specificationsMapper.selectList(eq);
    }

    @Override
    public void insert(Boolean special, String s0, String s1) {
        EcSpecifications specifications = new EcSpecifications();
        specifications.setSpecial(special);
        specifications.setAbbreviation(s0);
        specifications.setFullName(s1);
        specifications.setAddTime(new Date());
        specifications.setUpdateTime(new Date());
        specificationsMapper.insert(specifications);
    }

    @Override
    public void updateByName(boolean b, String s0, String s1) {
        LambdaUpdateWrapper<EcSpecifications> eq = Wrappers.lambdaUpdate(EcSpecifications.class)
                .set(EcSpecifications::getFullName, s1)
                .eq(EcSpecifications::getAbbreviation, s0)
                .eq(EcSpecifications::getSpecial, b);
        specificationsMapper.update(eq);
    }
}
