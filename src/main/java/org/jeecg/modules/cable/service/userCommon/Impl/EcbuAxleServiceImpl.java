package org.jeecg.modules.cable.service.userCommon.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.userCommon.EcbuAxle;
import org.jeecg.modules.cable.mapper.dao.userCommon.EcbuAxleMapper;
import org.jeecg.modules.cable.service.userCommon.EcbuAxleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbuAxleServiceImpl implements EcbuAxleService {
    @Resource
    private EcbuAxleMapper ecbuAxleMapper;


    @Override
    public List<EcbuAxle> getList(EcbuAxle record) {
        return ecbuAxleMapper.getList(record);
    }


    @Override
    public long getCount(EcbuAxle record) {
        return ecbuAxleMapper.getCount(record);
    }


    @Override
    public EcbuAxle getObject(EcbuAxle record) {
        return ecbuAxleMapper.getObject(record);
    }


    @Override
    public Integer insert(EcbuAxle record) {
        return ecbuAxleMapper.insert(record);
    }

    //updateByPrimaryKeySelective
    @Override
    public Integer updateByPrimaryKeySelective(EcbuAxle record) {
        return ecbuAxleMapper.updateByPrimaryKeySelective(record);
    }


    @Override
    public Integer deleteByPrimaryKey(Integer ecbuaId) {
        return ecbuAxleMapper.deleteByPrimaryKey(ecbuaId);
    }


    @Override
    public List<EcbuAxle> getListGreaterThanSortId(EcbuAxle record) {
        return ecbuAxleMapper.getListGreaterThanSortId(record);
    }

    //getObjectPassAxleName
    @Override
    public EcbuAxle getObjectPassAxleName(EcbuAxle record) {
        return ecbuAxleMapper.getObjectPassAxleName(record);
    }


    @Override
    public EcbuAxle getLatestObject(EcbuAxle record) {
        return ecbuAxleMapper.getLatestObject(record);
    }

    @Override
    public EcbuAxle getById(Integer ecbuaId) {
        return ecbuAxleMapper.selectById(ecbuaId);
    }

}
