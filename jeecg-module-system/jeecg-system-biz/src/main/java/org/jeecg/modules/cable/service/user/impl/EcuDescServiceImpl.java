package org.jeecg.modules.cable.service.user.impl;

import org.jeecg.modules.cable.mapper.dao.user.EcuDescDao;
import org.jeecg.modules.cable.entity.user.EcuDesc;
import org.jeecg.modules.cable.service.user.EcuDescService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcuDescServiceImpl implements EcuDescService {
    @Resource
    EcuDescDao ecuDescDao;

    @Override
    public EcuDesc getObject(EcuDesc record) {
        return ecuDescDao.getObject(record);
    }

    @Override
    public List<EcuDesc> getList(EcuDesc record) {
        return ecuDescDao.getList(record);
    }

    @Override
    public long getCount(EcuDesc record) {
        return ecuDescDao.getCount(record);
    }

    @Override
    public int insert(EcuDesc record) {
        return ecuDescDao.insert(record);
    }

    @Override
    public int update(EcuDesc record) {
        return ecuDescDao.update(record);
    }

    @Override
    public int delete(EcuDesc record) {
        return ecuDescDao.delete(record);
    }
}
