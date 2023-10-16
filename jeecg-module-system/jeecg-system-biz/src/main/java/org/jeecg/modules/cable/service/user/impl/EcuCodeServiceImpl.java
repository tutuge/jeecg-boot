package org.jeecg.modules.cable.service.user.impl;

import org.jeecg.modules.cable.mapper.dao.user.EcuCodeDao;
import org.jeecg.modules.cable.entity.user.EcuCode;
import org.jeecg.modules.cable.service.user.EcuCodeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class EcuCodeServiceImpl implements EcuCodeService {
    @Resource
    EcuCodeDao ecuCodeDao;

    @Override
    public EcuCode getObject(EcuCode record) {

        return ecuCodeDao.getObject(record);
    }

    @Override
    public int insert(EcuCode record) {
        return ecuCodeDao.insert(record);
    }

    //update
    @Override
    public int update(EcuCode record) {
        return ecuCodeDao.update(record);
    }

}
