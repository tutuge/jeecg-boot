package org.jeecg.modules.cable.service.price.Impl;

import org.jeecg.modules.cable.mapper.dao.price.EcuqDescMapper;
import org.jeecg.modules.cable.entity.price.EcuqDesc;
import org.jeecg.modules.cable.service.price.EcuqDescService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcuqDescServiceImpl implements EcuqDescService {
    @Resource
    EcuqDescMapper ecuqDescMapper;


    @Override
    public List<EcuqDesc> getList(EcuqDesc record) {
        return ecuqDescMapper.getList(record);
    }

    //getObject
    @Override
    public EcuqDesc getObject(EcuqDesc record) {
        return ecuqDescMapper.getObject(record);
    }

    //insert
    @Override
    public Integer insert(EcuqDesc record) {
        return ecuqDescMapper.insert(record);
    }

    //deletePassEcuqiId
    @Override
    public void deletePassEcuqiId(Integer ecuqiId) {
        ecuqDescMapper.deletePassEcuqiId(ecuqiId);
    }

    @Override
    public Integer update(EcuqDesc record) {
        return ecuqDescMapper.update(record);
    }
}
