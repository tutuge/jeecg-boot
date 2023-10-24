package org.jeecg.modules.cable.service.systemEcable.Impl;

import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbSteelbandDao;
import org.jeecg.modules.cable.entity.systemEcable.EcbSteelband;
import org.jeecg.modules.cable.service.systemEcable.EcbSteelbandService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbSteelbandServiceImpl implements EcbSteelbandService {
    @Resource
    EcbSteelbandDao ecbSteelbandDao;

    @Override
    public List<EcbSteelband> getList(EcbSteelband record) {//插入
        return ecbSteelbandDao.getList(record);
    }

    @Override
    public List<EcbSteelband> getListStart(EcbSteelband record) {
        return ecbSteelbandDao.getListStart(record);
    }

    @Override
    public long getCount() {
        return ecbSteelbandDao.getCount();
    }

    @Override
    public EcbSteelband getObject(EcbSteelband record) {
        return ecbSteelbandDao.getObject(record);
    }
}
