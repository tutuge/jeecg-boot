package org.jeecg.modules.cable.service.userCommon.Impl;

import org.jeecg.modules.cable.mapper.dao.userCommon.EcbuStoreDao;
import org.jeecg.modules.cable.entity.userCommon.EcbuStore;
import org.jeecg.modules.cable.service.userCommon.EcbuStoreService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbuStoreServiceImpl implements EcbuStoreService {
    @Resource
    EcbuStoreDao ecbuStoreDao;

    //getList
    @Override
    public List<EcbuStore> getList(EcbuStore record) {
        return ecbuStoreDao.getList(record);
    }

    //getCount
    @Override
    public long getCount(EcbuStore record) {
        return ecbuStoreDao.getCount(record);
    }

    //getObject
    @Override
    public EcbuStore getObject(EcbuStore record) {
        return ecbuStoreDao.getObject(record);
    }

    //getObjectPassStoreName
    @Override
    public EcbuStore getObjectPassStoreName(EcbuStore record) {
        return ecbuStoreDao.getObjectPassStoreName(record);
    }

    //insert
    @Override
    public Integer insert(EcbuStore record) {
        return ecbuStoreDao.insert(record);
    }

    @Override
    public Integer update(EcbuStore record) {
        return ecbuStoreDao.update(record);
    }

    //getLatestObject
    @Override
    public EcbuStore getLatestObject(EcbuStore record) {
        return ecbuStoreDao.getLatestObject(record);
    }

    @Override
    public Integer delete(EcbuStore record) {
        return ecbuStoreDao.delete(record);
    }

    //getListGreaterThanSortId 获取大于指定序号的数据列表
    @Override
    public List<EcbuStore> getListGreaterThanSortId(EcbuStore record) {
        return ecbuStoreDao.getListGreaterThanSortId(record);
    }

    //updateNotDefaultPassEcCompanyId
    @Override
    public Integer updateNotDefaultPassEcCompanyId(EcbuStore record) {
        return ecbuStoreDao.updateNotDefaultPassEcCompanyId(record);
    }

}
