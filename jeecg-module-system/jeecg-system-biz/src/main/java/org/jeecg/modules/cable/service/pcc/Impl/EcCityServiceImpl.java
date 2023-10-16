package org.jeecg.modules.cable.service.pcc.Impl;

import org.jeecg.modules.cable.mapper.dao.pcc.EcCityDao;
import org.jeecg.modules.cable.entity.pcc.EcCity;
import org.jeecg.modules.cable.service.pcc.EcCityService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcCityServiceImpl implements EcCityService {
    @Resource
    EcCityDao ecCityDao;

    @Override
    public List<EcCity> getList(EcCity record) {
        return ecCityDao.getList(record);
    }

}
