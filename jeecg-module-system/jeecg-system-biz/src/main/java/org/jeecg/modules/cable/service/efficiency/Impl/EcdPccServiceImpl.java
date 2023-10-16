package org.jeecg.modules.cable.service.efficiency.Impl;

import org.jeecg.modules.cable.mapper.dao.efficiency.EcdPccDao;
import org.jeecg.modules.cable.entity.efficiency.EcdPcc;
import org.jeecg.modules.cable.service.efficiency.EcdPccService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class EcdPccServiceImpl implements EcdPccService {
    @Resource
    EcdPccDao ecdPccDao;

    @Override
    public EcdPcc getObject(EcdPcc record) {
        return ecdPccDao.getObject(record);
    }

    @Override
    public int insert(EcdPcc record) {
        return ecdPccDao.insert(record);
    }

    @Override
    public int delete(EcdPcc record) {
        return ecdPccDao.delete(record);
    }

}
