package org.jeecg.modules.cable.service.userCommon.Impl;

import org.jeecg.modules.cable.mapper.dao.userCommon.EcducImagesMapper;
import org.jeecg.modules.cable.entity.userCommon.EcducImages;
import org.jeecg.modules.cable.service.userCommon.EcducImagesService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcducImagesServiceImpl implements EcducImagesService {
    @Resource
    EcducImagesMapper ecducImagesMapper;

    @Override
    public List<EcducImages> getList(EcducImages record) {
        return ecducImagesMapper.getList(record);
    }

    @Override
    public EcducImages getObject(EcducImages record) {
        return ecducImagesMapper.getObject(record);
    }

    @Override
    public Integer insert(EcducImages record) {
        return ecducImagesMapper.insert(record);
    }

    @Override
    public Integer delete(EcducImages record) {
        return ecducImagesMapper.delete(record);
    }
}
