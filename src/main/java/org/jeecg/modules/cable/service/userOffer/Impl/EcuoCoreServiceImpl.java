package org.jeecg.modules.cable.service.userOffer.Impl;

import org.jeecg.modules.cable.mapper.dao.userOffer.EcuoCoreMapper;
import org.jeecg.modules.cable.entity.userOffer.EcuoCore;
import org.jeecg.modules.cable.service.userOffer.EcuoCoreService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcuoCoreServiceImpl implements EcuoCoreService {
    @Resource
    EcuoCoreMapper ecuoCoreMapper;

    @Override
    public List<EcuoCore> getList(EcuoCore record) {
        return ecuoCoreMapper.getList(record);
    }

    @Override
    public EcuoCore getObject(EcuoCore record) {
        return ecuoCoreMapper.getObject(record);
    }

    @Override
    public Integer insert(EcuoCore record) {
        return ecuoCoreMapper.insert(record);
    }

    @Override
    public Integer update(EcuoCore record) {
        return ecuoCoreMapper.updateById(record);
    }

}
