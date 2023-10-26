package org.jeecg.modules.cable.service.userOffer.Impl;

import org.jeecg.modules.cable.mapper.dao.userOffer.EcuoCoreDao;
import org.jeecg.modules.cable.entity.userOffer.EcuoCore;
import org.jeecg.modules.cable.service.userOffer.EcuoCoreService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcuoCoreServiceImpl implements EcuoCoreService {
    @Resource
    EcuoCoreDao ecuoCoreDao;

    @Override
    public List<EcuoCore> getList(EcuoCore record) {
        return ecuoCoreDao.getList(record);
    }

    @Override
    public EcuoCore getObject(EcuoCore record) {
        return ecuoCoreDao.getObject(record);
    }

    @Override
    public Integer insert(EcuoCore record) {
        return ecuoCoreDao.insert(record);
    }

    @Override
    public Integer update(EcuoCore record) {
        return ecuoCoreDao.update(record);
    }

}
