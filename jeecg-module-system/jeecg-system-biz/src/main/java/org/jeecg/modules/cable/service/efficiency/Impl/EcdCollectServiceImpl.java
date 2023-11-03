package org.jeecg.modules.cable.service.efficiency.Impl;

import org.jeecg.modules.cable.mapper.dao.efficiency.EcdCollectMapper;
import org.jeecg.modules.cable.entity.efficiency.EcdCollect;
import org.jeecg.modules.cable.service.efficiency.EcdCollectService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcdCollectServiceImpl implements EcdCollectService {
    @Resource
    EcdCollectMapper ecdCollectMapper;

    @Override
    public List<EcdCollect> getList(EcdCollect record) {
        return ecdCollectMapper.getList(record);
    }

    @Override
    public long getCount(EcdCollect record) {
        return ecdCollectMapper.getCount(record);
    }

    @Override
    public Integer insert(EcdCollect record) {
        return ecdCollectMapper.insert(record);
    }

    @Override
    public Integer update(EcdCollect record) {
        return ecdCollectMapper.update(record);
    }

    @Override
    public EcdCollect getObject(EcdCollect record) {
        return ecdCollectMapper.getObject(record);
    }

}
