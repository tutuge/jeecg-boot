package org.jeecg.modules.cable.service.userOffer.Impl;

import org.jeecg.modules.cable.mapper.dao.userOffer.EcuoProgrammeDao;
import org.jeecg.modules.cable.entity.userOffer.EcuoProgramme;
import org.jeecg.modules.cable.service.userOffer.EcuoProgrammeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcuoProgrammeServiceImpl implements EcuoProgrammeService {
    @Resource
    EcuoProgrammeDao ecuoProgrammeDao;

    @Override
    public List<EcuoProgramme> getList(EcuoProgramme record) {
        return ecuoProgrammeDao.getList(record);
    }

    @Override
    public EcuoProgramme getObject(EcuoProgramme record) {
        return ecuoProgrammeDao.getObject(record);
    }

    @Override
    public Integer insert(EcuoProgramme record) {
        return ecuoProgrammeDao.insert(record);
    }

    @Override
    public Integer update(EcuoProgramme record) {
        return ecuoProgrammeDao.update(record);
    }

    @Override
    public Integer delete(EcuoProgramme record) {
        return ecuoProgrammeDao.delete(record);
    }

}
