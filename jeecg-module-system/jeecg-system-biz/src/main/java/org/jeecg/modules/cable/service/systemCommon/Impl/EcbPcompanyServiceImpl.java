package org.jeecg.modules.cable.service.systemCommon.Impl;

import org.jeecg.modules.cable.mapper.dao.systemCommon.EcbPcompanyDao;
import org.jeecg.modules.cable.entity.systemCommon.EcbPcompany;
import org.jeecg.modules.cable.service.systemCommon.EcbPcompanyService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbPcompanyServiceImpl implements EcbPcompanyService {
    @Resource
    EcbPcompanyDao ecbPcompanyDao;

    @Override
    public List<EcbPcompany> getList(EcbPcompany record) {
        return ecbPcompanyDao.getList(record);
    }

    @Override
    public long getCount(EcbPcompany record) {
        return ecbPcompanyDao.getCount(record);
    }

    @Override
    public EcbPcompany getObject(EcbPcompany record) {
        return ecbPcompanyDao.getObject(record);
    }

    @Override
    public Integer insert(EcbPcompany record) {
        return ecbPcompanyDao.insert(record);
    }

    @Override
    public Integer update(EcbPcompany record) {
        return ecbPcompanyDao.update(record);
    }

    @Override
    public Integer delete(EcbPcompany record) {
        return ecbPcompanyDao.delete(record);
    }
}
