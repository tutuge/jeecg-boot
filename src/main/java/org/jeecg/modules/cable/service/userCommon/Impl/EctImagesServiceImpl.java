package org.jeecg.modules.cable.service.userCommon.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.userCommon.EctImages;
import org.jeecg.modules.cable.mapper.dao.userCommon.EctImagesMapper;
import org.jeecg.modules.cable.service.userCommon.EctImagesService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EctImagesServiceImpl implements EctImagesService {
    @Resource
    EctImagesMapper ectImagesMapper;

    @Override
    public Integer insert(EctImages record) {
        record.setAddTime(new Date());
        return ectImagesMapper.insert(record);
    }

    @Override
    public EctImages getObject(EctImages record) {
        return ectImagesMapper.getObject(record);
    }

    @Override
    public Integer delete(EctImages record) {
        return ectImagesMapper.deleteRecord(record);
    }

}
