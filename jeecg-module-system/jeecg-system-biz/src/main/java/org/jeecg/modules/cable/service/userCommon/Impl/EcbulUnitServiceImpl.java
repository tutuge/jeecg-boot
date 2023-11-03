package org.jeecg.modules.cable.service.userCommon.Impl;

import org.jeecg.modules.cable.mapper.dao.userCommon.EcbulUnitMapper;
import org.jeecg.modules.cable.entity.userCommon.EcbulUnit;
import org.jeecg.modules.cable.service.userCommon.EcbulUnitService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbulUnitServiceImpl implements EcbulUnitService {
    @Resource
    EcbulUnitMapper ecbulUnitMapper;

    @Override
    public List<EcbulUnit> getList(EcbulUnit record) {
        return ecbulUnitMapper.getList(record);
    }

    @Override
    public long getCount(EcbulUnit record) {
        return ecbulUnitMapper.getCount(record);
    }

    @Override
    public EcbulUnit getObject(EcbulUnit record) {
        return ecbulUnitMapper.getObject(record);
    }

    @Override
    public Integer insert(EcbulUnit record) {
        return ecbulUnitMapper.insert(record);
    }

    @Override
    public Integer update(EcbulUnit record) {
        return ecbulUnitMapper.update(record);
    }

    //delete
    @Override
    public Integer delete(EcbulUnit record) {
        return ecbulUnitMapper.delete(record);
    }

    //getListGreaterThanSortId 获取大于指定序号的数据列表
    @Override
    public List<EcbulUnit> getListGreaterThanSortId(EcbulUnit record) {
        return ecbulUnitMapper.getListGreaterThanSortId(record);
    }

    //getObjectPassProvinceName
    @Override
    public EcbulUnit getObjectPassLengthName(EcbulUnit record) {
        return ecbulUnitMapper.getObjectPassLengthName(record);
    }

    //getLatestObject
    @Override
    public EcbulUnit getLatestObject(EcbulUnit record) {
        return ecbulUnitMapper.getLatestObject(record);
    }

}
