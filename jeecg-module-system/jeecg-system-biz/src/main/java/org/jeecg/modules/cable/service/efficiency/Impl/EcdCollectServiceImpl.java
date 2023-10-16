package org.jeecg.modules.cable.service.efficiency.Impl;

import org.jeecg.modules.cable.mapper.dao.efficiency.EcdCollectDao;
import org.jeecg.modules.cable.entity.efficiency.EcdCollect;
import org.jeecg.modules.cable.service.efficiency.EcdCollectService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcdCollectServiceImpl implements EcdCollectService {
    @Resource
    EcdCollectDao ecdCollectDao;

    @Override
    public List<EcdCollect> getList(EcdCollect record) {
        return ecdCollectDao.getList(record);
    }

    @Override
    public long getCount(EcdCollect record) {
        return ecdCollectDao.getCount(record);
    }

    @Override
    public int insert(EcdCollect record) {
        return ecdCollectDao.insert(record);
    }

    @Override
    public int update(EcdCollect record) {
        return ecdCollectDao.update(record);
    }

    @Override
    public EcdCollect getObject(EcdCollect record) {
        return ecdCollectDao.getObject(record);
    }

}
