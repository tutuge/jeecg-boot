package org.jeecg.modules.cable.service.userCommon.Impl;

import org.jeecg.modules.cable.mapper.dao.userCommon.EcbuAxleDao;
import org.jeecg.modules.cable.entity.userCommon.EcbuAxle;
import org.jeecg.modules.cable.service.userCommon.EcbuAxleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbuAxleServiceImpl implements EcbuAxleService {
    @Resource
    EcbuAxleDao ecbuAxleDao;

    //getList
    @Override
    public List<EcbuAxle> getList(EcbuAxle record) {
        return ecbuAxleDao.getList(record);
    }

    //getCount
    @Override
    public long getCount(EcbuAxle record) {
        return ecbuAxleDao.getCount(record);
    }

    //getObject
    @Override
    public EcbuAxle getObject(EcbuAxle record) {
        return ecbuAxleDao.getObject(record);
    }

    //insert
    @Override
    public Integer insert(EcbuAxle record) {
        return ecbuAxleDao.insert(record);
    }

    //updateByPrimaryKeySelective
    @Override
    public Integer updateByPrimaryKeySelective(EcbuAxle record) {
        return ecbuAxleDao.updateByPrimaryKeySelective(record);
    }

    //deleteByPrimaryKey
    @Override
    public Integer deleteByPrimaryKey(Integer ecbuaId) {
        return ecbuAxleDao.deleteByPrimaryKey(ecbuaId);
    }

    //getListGreaterThanSortId 获取大于指定序号的数据列表
    @Override
    public List<EcbuAxle> getListGreaterThanSortId(EcbuAxle record) {
        return ecbuAxleDao.getListGreaterThanSortId(record);
    }

    //getObjectPassAxleName
    @Override
    public EcbuAxle getObjectPassAxleName(EcbuAxle record) {
        return ecbuAxleDao.getObjectPassAxleName(record);
    }

    //getLatestObject
    @Override
    public EcbuAxle getLatestObject(EcbuAxle record) {
        return ecbuAxleDao.getLatestObject(record);
    }

}
