package org.jeecg.modules.cable.service.certs.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.certs.EcuqCerts;
import org.jeecg.modules.cable.mapper.dao.certs.EcuqCertsMapper;
import org.jeecg.modules.cable.service.certs.EcuqCertsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcuqCertsServiceImpl implements EcuqCertsService {
    @Resource
    EcuqCertsMapper ecuqCertsMapper;

    @Override
    public EcuqCerts getObject(EcuqCerts record) {
        return ecuqCertsMapper.getObject(record);
    }

    @Override
    public List<EcuqCerts> getList(EcuqCerts record) {
        return ecuqCertsMapper.getList(record);
    }

    @Override
    public long getCount(EcuqCerts record) {
        return ecuqCertsMapper.getCount(record);
    }

    @Override
    public Integer insert(EcuqCerts record) {
        return ecuqCertsMapper.insert(record);
    }

    @Override
    public Integer update(EcuqCerts record) {
        return ecuqCertsMapper.update(record);
    }

    @Override
    public Integer delete(EcuqCerts record) {
        return ecuqCertsMapper.delete(record);
    }

}
