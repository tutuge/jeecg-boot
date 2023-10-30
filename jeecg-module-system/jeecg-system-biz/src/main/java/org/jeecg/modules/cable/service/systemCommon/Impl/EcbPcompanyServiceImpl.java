package org.jeecg.modules.cable.service.systemCommon.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.systemCommon.EcbPcompany;
import org.jeecg.modules.cable.mapper.dao.systemCommon.EcbPcompanyMapper;
import org.jeecg.modules.cable.service.systemCommon.EcbPcompanyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbPcompanyServiceImpl implements EcbPcompanyService {
    @Resource
    EcbPcompanyMapper ecbPcompanyMapper;

    @Override
    public List<EcbPcompany> getList(EcbPcompany record) {
        return ecbPcompanyMapper.getList(record);
    }

    @Override
    public long getCount(EcbPcompany record) {
        return ecbPcompanyMapper.getCount(record);
    }

    @Override
    public EcbPcompany getObject(EcbPcompany record) {
        return ecbPcompanyMapper.getObject(record);
    }

    @Override
    public Integer insert(EcbPcompany record) {
        return ecbPcompanyMapper.insert(record);
    }

    @Override
    public Integer update(EcbPcompany record) {
        return ecbPcompanyMapper.updateById(record);
    }

    @Override
    public Integer delete(EcbPcompany record) {
        return ecbPcompanyMapper.deleteById(record);
    }
}
