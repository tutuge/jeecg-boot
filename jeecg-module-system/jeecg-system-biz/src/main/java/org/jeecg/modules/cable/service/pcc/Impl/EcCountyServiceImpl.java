package org.jeecg.modules.cable.service.pcc.Impl;

import org.jeecg.modules.cable.mapper.dao.pcc.EcCountyDao;
import org.jeecg.modules.cable.entity.pcc.EcCounty;
import org.jeecg.modules.cable.service.pcc.EcCountyService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcCountyServiceImpl implements EcCountyService {
    @Resource
    EcCountyDao ecCountyDao;

    @Override
    public List<EcCounty> getList(EcCounty record) {
        return ecCountyDao.getList(record);
    }

}
