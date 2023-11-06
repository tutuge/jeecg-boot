package org.jeecg.modules.cable.service.user.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.modules.cable.mapper.dao.user.EcUserMapper;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcUserServiceImpl implements EcUserService {
    @Resource
    EcUserMapper ecUserMapper;

    @Override
    public EcUser getObject(EcUser record) {// 根据EcUser获取EcUser
        return ecUserMapper.getObject(record);
    }

    @Override
    public List<EcUser> getList(EcUser record) {
        return ecUserMapper.getList(record);
    }

    @Override
    public long getCount(EcUser record) {
        return ecUserMapper.getCount(record);
    }

    // insert
    @Override
    public Integer insert(EcUser record) {
        return ecUserMapper.insert(record);
    }

    // getObjectPassEcUsername
    @Override
    public EcUser getObjectPassEcUsername(EcUser record) {
        return ecUserMapper.getObjectPassEcUsername(record);
    }

    // getObjectPassEcPhone
    @Override
    public EcUser getObjectPassEcPhone(EcUser record) {
        return ecUserMapper.getObjectPassEcPhone(record);
    }

    // getObjectPassCode
    @Override
    public EcUser getObjectPassCode(EcUser record) {
        return ecUserMapper.getObjectPassCode(record);
    }

    @Override
    public Integer update(EcUser record) {
        return ecUserMapper.updateById(record);
    }

    @Override
    public EcUser getByUserId(Integer userId) {
        return ecUserMapper.selectOne(Wrappers.lambdaQuery(EcUser.class).eq(EcUser::getUserId, userId));
    }

}
