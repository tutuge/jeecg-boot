package org.jeecg.modules.cable.service.systemPcc.Impl;

import org.jeecg.modules.cable.mapper.dao.systemPcc.EcCountyMapper;
import org.jeecg.modules.cable.entity.systemPcc.EcCounty;
import org.jeecg.modules.cable.service.systemPcc.EcCountyService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcCountyServiceImpl implements EcCountyService {
    @Resource
    EcCountyMapper ecCountyMapper;

    @Override
    public List<EcCounty> getList(EcCounty record) {
        return ecCountyMapper.getList(record);
    }

}
