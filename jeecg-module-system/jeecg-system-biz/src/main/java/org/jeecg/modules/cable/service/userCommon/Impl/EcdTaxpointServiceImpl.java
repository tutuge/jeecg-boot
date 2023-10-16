package org.jeecg.modules.cable.service.userCommon.Impl;

import org.jeecg.modules.cable.mapper.dao.userCommon.EcdTaxpointDao;
import org.jeecg.modules.cable.entity.systemEcable.EcdTaxpoint;
import org.jeecg.modules.cable.service.userCommon.EcdTaxpointService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcdTaxpointServiceImpl implements EcdTaxpointService {
    @Resource
    EcdTaxpointDao ecdTaxpointDao;

    //getList
    @Override
    public List<EcdTaxpoint> getList(EcdTaxpoint record) {
        return ecdTaxpointDao.getList(record);
    }

    //getCount
    @Override
    public long getCount(EcdTaxpoint record) {
        return ecdTaxpointDao.getCount(record);
    }

    //getObject
    @Override
    public EcdTaxpoint getObject(EcdTaxpoint record) {
        return ecdTaxpointDao.getObject(record);
    }

}
