package org.jeecg.modules.cable.service.pcc.Impl;

import org.jeecg.modules.cable.mapper.dao.pcc.EcCityMapper;
import org.jeecg.modules.cable.entity.pcc.EcCity;
import org.jeecg.modules.cable.service.pcc.EcCityService;
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
