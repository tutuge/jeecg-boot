package org.jeecg.modules.cable.service.userCommon.Impl;

import org.jeecg.modules.cable.mapper.dao.userCommon.EcbulUnitDao;
import org.jeecg.modules.cable.entity.userCommon.EcbulUnit;
import org.jeecg.modules.cable.service.userCommon.EcbulUnitService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbulUnitServiceImpl implements EcbulUnitService {
    @Resource
    EcbulUnitDao ecbulUnitDao;

    @Override
    public List<EcbulUnit> getList(EcbulUnit record) {
        return ecbulUnitDao.getList(record);
    }

    @Override
    public long getCount(EcbulUnit record) {
        return ecbulUnitDao.getCount(record);
    }

    @Override
    public EcbulUnit getObject(EcbulUnit record) {
        return ecbulUnitDao.getObject(record);
    }

    @Override
    public Integer insert(EcbulUnit record) {
        return ecbulUnitDao.insert(record);
    }

    @Override
    public Integer update(EcbulUnit record) {
        return ecbulUnitDao.update(record);
    }

    //delete
    @Override
    public Integer delete(EcbulUnit record) {
        return ecbulUnitDao.delete(record);
    }

    //getListGreaterThanSortId 获取大于指定序号的数据列表
    @Override
    public List<EcbulUnit> getListGreaterThanSortId(EcbulUnit record) {
        return ecbulUnitDao.getListGreaterThanSortId(record);
    }

    //getObjectPassProvinceName
    @Override
    public EcbulUnit getObjectPassLengthName(EcbulUnit record) {
        return ecbulUnitDao.getObjectPassLengthName(record);
    }

    //getLatestObject
    @Override
    public EcbulUnit getLatestObject(EcbulUnit record) {
        return ecbulUnitDao.getLatestObject(record);
    }

}
