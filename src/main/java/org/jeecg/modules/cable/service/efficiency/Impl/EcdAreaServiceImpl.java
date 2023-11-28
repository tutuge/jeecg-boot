package org.jeecg.modules.cable.service.efficiency.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.efficiency.EcdArea;
import org.jeecg.modules.cable.mapper.dao.efficiency.EcdAreaMapper;
import org.jeecg.modules.cable.service.efficiency.EcdAreaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcdAreaServiceImpl implements EcdAreaService {
    @Resource
    EcdAreaMapper ecdAreaMapper;

    @Override
    public List<EcdArea> getList(EcdArea record) {
        return ecdAreaMapper.getList(record);
    }

    @Override
    public long getCount(EcdArea record) {
        return ecdAreaMapper.getCount(record);
    }

    @Override
    public Integer insert(EcdArea record) {
        return ecdAreaMapper.insert(record);
    }

    @Override
    public Integer update(EcdArea record) {
        return ecdAreaMapper.updateRecord(record);
    }

    @Override
    public EcdArea getObject(EcdArea record) {
        return ecdAreaMapper.getObject(record);
    }

}
