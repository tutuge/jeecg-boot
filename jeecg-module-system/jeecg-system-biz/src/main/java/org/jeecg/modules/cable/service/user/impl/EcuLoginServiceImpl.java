package org.jeecg.modules.cable.service.user.impl;

import org.jeecg.modules.cable.mapper.dao.user.EcuLoginDao;
import org.jeecg.modules.cable.entity.user.EcuLogin;
import org.jeecg.modules.cable.service.user.EcuLoginService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class EcuLoginServiceImpl implements EcuLoginService {
    @Resource
    EcuLoginDao ecuLoginDao;
    @Override
    public int insert(EcuLogin record){//插入

        return ecuLoginDao.insert(record);
    }

    @Override
    public EcuLogin getObject(EcuLogin record){//通过ecuId获取EcuLogin
        return ecuLoginDao.getObject(record);
    }
    @Override
    public int updateTokenPassEcuId(EcuLogin record){//通过ecuId获取EcuLogin
        return ecuLoginDao.updateTokenPassEcuId(record);
    }
}
