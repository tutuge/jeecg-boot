package org.jeecg.modules.cable.service.userCommon.Impl;

import org.jeecg.modules.cable.mapper.dao.userCommon.EctImagesDao;
import org.jeecg.modules.cable.entity.userCommon.EctImages;
import org.jeecg.modules.cable.service.userCommon.EctImagesService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class EctImagesServiceImpl implements EctImagesService {
    @Resource
    EctImagesDao ectImagesDao;

    @Override
    public Integer insert(EctImages record) {
        return ectImagesDao.insert(record);
    }

    @Override
    public EctImages getObject(EctImages record) {
        return ectImagesDao.getObject(record);
    }

    @Override
    public Integer delete(EctImages record) {
        return ectImagesDao.delete(record);
    }

}
