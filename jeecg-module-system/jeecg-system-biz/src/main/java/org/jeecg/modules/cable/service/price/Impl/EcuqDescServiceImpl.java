package org.jeecg.modules.cable.service.price.Impl;

import org.jeecg.modules.cable.mapper.dao.price.EcuqDescDao;
import org.jeecg.modules.cable.entity.price.EcuqDesc;
import org.jeecg.modules.cable.service.price.EcuqDescService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcuqDescServiceImpl implements EcuqDescService {
    @Resource
    EcuqDescDao ecuqDescDao;

    //getList
    @Override
    public List<EcuqDesc> getList(EcuqDesc record) {
        return ecuqDescDao.getList(record);
    }

    //getObject
    @Override
    public EcuqDesc getObject(EcuqDesc record) {
        return ecuqDescDao.getObject(record);
    }

    //insert
    @Override
    public int insert(EcuqDesc record) {
        return ecuqDescDao.insert(record);
    }

    //deletePassEcuqiId
    @Override
    public void deletePassEcuqiId(int ecuqiId) {
        ecuqDescDao.deletePassEcuqiId(ecuqiId);
    }

    @Override
    public int update(EcuqDesc record) {
        return ecuqDescDao.update(record);
    }
}
