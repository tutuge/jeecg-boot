package org.jeecg.modules.cable.service.userOffer.Impl;

import org.jeecg.modules.cable.mapper.dao.userOffer.EcuoAreaDao;
import org.jeecg.modules.cable.entity.userOffer.EcuoArea;
import org.jeecg.modules.cable.service.userOffer.EcuoAreaService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcuoAreaServiceImpl implements EcuoAreaService {
    @Resource
    EcuoAreaDao ecuoAreaDao;

    @Override
    public List<EcuoArea> getList(EcuoArea record) {
        return ecuoAreaDao.getList(record);
    }

    @Override
    public EcuoArea getObject(EcuoArea record) {
        return ecuoAreaDao.getObject(record);
    }

    @Override
    public int insert(EcuoArea record) {
        return ecuoAreaDao.insert(record);
    }

    @Override
    public int update(EcuoArea record) {
        return ecuoAreaDao.update(record);
    }

}
