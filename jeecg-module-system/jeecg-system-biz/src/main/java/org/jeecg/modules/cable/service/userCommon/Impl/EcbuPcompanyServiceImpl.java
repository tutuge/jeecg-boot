package org.jeecg.modules.cable.service.userCommon.Impl;

import org.jeecg.modules.cable.mapper.dao.userCommon.EcbuPcompanyDao;
import org.jeecg.modules.cable.entity.userCommon.EcbuPcompany;
import org.jeecg.modules.cable.service.userCommon.EcbuPcompanyService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbuPcompanyServiceImpl implements EcbuPcompanyService {
    @Resource
    EcbuPcompanyDao ecbuPcompanyDao;

    //getList
    @Override
    public List<EcbuPcompany> getList(EcbuPcompany record) {
        return ecbuPcompanyDao.getList(record);
    }

    //getCount
    @Override
    public long getCount(EcbuPcompany record) {
        return ecbuPcompanyDao.getCount(record);
    }

    //getObject
    @Override
    public EcbuPcompany getObject(EcbuPcompany record) {
        return ecbuPcompanyDao.getObject(record);
    }

    //insert
    @Override
    public int insert(EcbuPcompany record) {
        return ecbuPcompanyDao.insert(record);
    }

    //update
    @Override
    public int update(EcbuPcompany record) {
        return ecbuPcompanyDao.update(record);
    }

    @Override
    public int delete(EcbuPcompany record) {
        return ecbuPcompanyDao.delete(record);
    }

    //getListGreaterThanSortId 获取大于指定序号的数据列表
    @Override
    public List<EcbuPcompany> getListGreaterThanSortId(EcbuPcompany record) {
        return ecbuPcompanyDao.getListGreaterThanSortId(record);
    }

    //getObjectPassPcName
    @Override
    public EcbuPcompany getObjectPassPcName(EcbuPcompany record) {
        return ecbuPcompanyDao.getObjectPassPcName(record);
    }

    //getLatestObject
    @Override
    public EcbuPcompany getLatestObject(EcbuPcompany record) {
        return ecbuPcompanyDao.getLatestObject(record);
    }

}
