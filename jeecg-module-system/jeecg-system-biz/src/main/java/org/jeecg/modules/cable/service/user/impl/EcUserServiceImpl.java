package org.jeecg.modules.cable.service.user.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.modules.cable.mapper.dao.user.EcUserDao;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcUserServiceImpl implements EcUserService {
    @Resource
    EcUserDao ecUserDao;

    @Override
    public EcUser getObject(EcUser record) {//根据EcUser获取EcUser

        return ecUserDao.getObject(record);
    }

    @Override
    public List<EcUser> getList(EcUser record) {
        return ecUserDao.getList(record);
    }

    @Override
    public long getCount(EcUser record) {
        return ecUserDao.getCount(record);
    }

    //insert
    @Override
    public Integer insert(EcUser record) {
        return ecUserDao.insert(record);
    }

    //getObjectPassEcUsername
    @Override
    public EcUser getObjectPassEcUsername(EcUser record) {
        return ecUserDao.getObjectPassEcUsername(record);
    }

    //getObjectPassEcPhone
    @Override
    public EcUser getObjectPassEcPhone(EcUser record) {
        return ecUserDao.getObjectPassEcPhone(record);
    }

    //getObjectPassCode
    @Override
    public EcUser getObjectPassCode(EcUser record) {
        return ecUserDao.getObjectPassCode(record);
    }

    @Override
    public Integer update(EcUser record) {
        return ecUserDao.update(record);
    }

    @Override
    public EcUser getByUserId(String userId) {
        return ecUserDao.selectOne(Wrappers.lambdaQuery(EcUser.class).eq(EcUser::getUserId, userId));
    }

}
