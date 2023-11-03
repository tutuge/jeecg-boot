package org.jeecg.modules.cable.service.userOffer.Impl;

import org.jeecg.modules.cable.mapper.dao.userOffer.EcuoAreaMapper;
import org.jeecg.modules.cable.entity.userOffer.EcuoArea;
import org.jeecg.modules.cable.service.userOffer.EcuoAreaService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcuoAreaServiceImpl implements EcuoAreaService {
    @Resource
    EcuoAreaMapper ecuoAreaMapper;

    @Override
    public List<EcuoArea> getList(EcuoArea record) {
        return ecuoAreaMapper.getList(record);
    }

    @Override
    public EcuoArea getObject(EcuoArea record) {
        return ecuoAreaMapper.getObject(record);
    }

    @Override
    public Integer insert(EcuoArea record) {
        return ecuoAreaMapper.insert(record);
    }

    @Override
    public Integer update(EcuoArea record) {
        return ecuoAreaMapper.update(record);
    }

}
