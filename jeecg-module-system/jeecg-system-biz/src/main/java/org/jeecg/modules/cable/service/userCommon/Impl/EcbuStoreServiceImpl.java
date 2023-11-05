package org.jeecg.modules.cable.service.userCommon.Impl;

import org.jeecg.modules.cable.mapper.dao.userCommon.EcbuStoreMapper;
import org.jeecg.modules.cable.entity.userCommon.EcbuStore;
import org.jeecg.modules.cable.service.userCommon.EcbuStoreService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbuStoreServiceImpl implements EcbuStoreService {
    @Resource
    EcbuStoreMapper ecbuStoreMapper;


    @Override
    public List<EcbuStore> getList(EcbuStore record) {
        return ecbuStoreMapper.getList(record);
    }

    //getCount
    @Override
    public long getCount(EcbuStore record) {
        return ecbuStoreMapper.getCount(record);
    }

    //getObject
    @Override
    public EcbuStore getObject(EcbuStore record) {
        return ecbuStoreMapper.getObject(record);
    }

    //getObjectPassStoreName
    @Override
    public EcbuStore getObjectPassStoreName(EcbuStore record) {
        return ecbuStoreMapper.getObjectPassStoreName(record);
    }

    //insert
    @Override
    public Integer insert(EcbuStore record) {
        return ecbuStoreMapper.insert(record);
    }

    @Override
    public Integer update(EcbuStore record) {
        return ecbuStoreMapper.update(record);
    }

    //getLatestObject
    @Override
    public EcbuStore getLatestObject(EcbuStore record) {
        return ecbuStoreMapper.getLatestObject(record);
    }

    @Override
    public Integer delete(EcbuStore record) {
        return ecbuStoreMapper.delete(record);
    }

    //getListGreaterThanSortId 获取大于指定序号的数据列表
    @Override
    public List<EcbuStore> getListGreaterThanSortId(EcbuStore record) {
        return ecbuStoreMapper.getListGreaterThanSortId(record);
    }

    //updateNotDefaultPassEcCompanyId
    @Override
    public Integer updateNotDefaultPassEcCompanyId(EcbuStore record) {
        return ecbuStoreMapper.updateNotDefaultPassEcCompanyId(record);
    }

}
