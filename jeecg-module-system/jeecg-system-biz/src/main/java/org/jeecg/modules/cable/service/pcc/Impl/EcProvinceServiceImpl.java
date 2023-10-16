package org.jeecg.modules.cable.service.pcc.Impl;

import org.jeecg.modules.cable.mapper.dao.pcc.EcProvinceDao;
import org.jeecg.modules.cable.entity.pcc.EcProvince;
import org.jeecg.modules.cable.service.pcc.EcProvinceService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcProvinceServiceImpl implements EcProvinceService {
    @Resource
    EcProvinceDao ecProvinceDao;
    //getList
    @Override
    public List<EcProvince> getList(EcProvince record) {
        return ecProvinceDao.getList(record);
    }
    //getObject
    @Override
    public EcProvince getObject(EcProvince record)
    {
        return ecProvinceDao.getObject(record);
    }

}
