package org.jeecg.modules.cable.service.userCommon.Impl;

import org.jeecg.modules.cable.mapper.dao.userCommon.EcducImagesDao;
import org.jeecg.modules.cable.entity.userCommon.EcducImages;
import org.jeecg.modules.cable.service.userCommon.EcducImagesService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcducImagesServiceImpl implements EcducImagesService {
    @Resource
    EcducImagesDao ecducImagesDao;

    @Override
    public List<EcducImages> getList(EcducImages record) {
        return ecducImagesDao.getList(record);
    }

    @Override
    public EcducImages getObject(EcducImages record) {
        return ecducImagesDao.getObject(record);
    }

    @Override
    public int insert(EcducImages record) {
        return ecducImagesDao.insert(record);
    }

    @Override
    public int delete(EcducImages record) {
        return ecducImagesDao.delete(record);
    }
}
