package org.jeecg.modules.cable.service.systemPcc.Impl;

import org.jeecg.modules.cable.mapper.dao.systemPcc.EcCityMapper;
import org.jeecg.modules.cable.entity.systemPcc.EcCity;
import org.jeecg.modules.cable.service.systemPcc.EcCityService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcCityServiceImpl implements EcCityService {
    @Resource
    EcCityMapper ecCityMapper;

    @Override
    public List<EcCity> getList(EcCity record) {
        return ecCityMapper.getList(record);
    }

}
