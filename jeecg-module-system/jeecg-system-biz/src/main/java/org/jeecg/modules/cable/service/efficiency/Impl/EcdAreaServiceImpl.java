package org.jeecg.modules.cable.service.efficiency.Impl;

import org.jeecg.modules.cable.mapper.dao.efficiency.EcdAreaDao;
import org.jeecg.modules.cable.entity.efficiency.EcdArea;
import org.jeecg.modules.cable.service.efficiency.EcdAreaService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcdAreaServiceImpl implements EcdAreaService {
    @Resource
    EcdAreaDao ecdAreaDao;

    @Override
    public List<EcdArea> getList(EcdArea record) {
        return ecdAreaDao.getList(record);
    }

    @Override
    public long getCount(EcdArea record) {
        return ecdAreaDao.getCount(record);
    }

    @Override
    public int insert(EcdArea record) {
        return ecdAreaDao.insert(record);
    }

    @Override
    public int update(EcdArea record) {
        return ecdAreaDao.update(record);
    }

    @Override
    public EcdArea getObject(EcdArea record) {
        return ecdAreaDao.getObject(record);
    }

}
