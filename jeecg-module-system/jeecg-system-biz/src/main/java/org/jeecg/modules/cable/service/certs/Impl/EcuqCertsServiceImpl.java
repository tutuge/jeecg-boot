package org.jeecg.modules.cable.service.certs.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.certs.EcuqCerts;
import org.jeecg.modules.cable.mapper.dao.certs.EcuqCertsDao;
import org.jeecg.modules.cable.service.certs.EcuqCertsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcuqCertsServiceImpl implements EcuqCertsService {
    @Resource
    EcuqCertsDao ecuqCertsDao;

    @Override
    public EcuqCerts getObject(EcuqCerts record) {
        return ecuqCertsDao.getObject(record);
    }

    @Override
    public List<EcuqCerts> getList(EcuqCerts record) {
        return ecuqCertsDao.getList(record);
    }

    @Override
    public long getCount(EcuqCerts record) {
        return ecuqCertsDao.getCount(record);
    }

    @Override
    public int insert(EcuqCerts record) {
        return ecuqCertsDao.insert(record);
    }

    @Override
    public int update(EcuqCerts record) {
        return ecuqCertsDao.update(record);
    }

    @Override
    public int delete(EcuqCerts record) {
        return ecuqCertsDao.delete(record);
    }

}
