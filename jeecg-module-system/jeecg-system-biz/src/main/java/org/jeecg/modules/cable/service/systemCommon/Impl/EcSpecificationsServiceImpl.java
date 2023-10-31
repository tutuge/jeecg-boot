package org.jeecg.modules.cable.service.systemCommon.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.cable.entity.systemCommon.EcSpecifications;
import org.jeecg.modules.cable.mapper.dao.systemCommon.EcSpecificationsMapper;
import org.jeecg.modules.cable.service.systemCommon.EcSpecificationsService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EcSpecificationsServiceImpl extends ServiceImpl<EcSpecificationsMapper, EcSpecifications> implements EcSpecificationsService {
    @Override
    public boolean save(EcSpecifications entity) {
        entity.setAddTime(new Date());
        entity.setUpdateTime(new Date());
        baseMapper.insert(entity);
        return true;
    }

}
