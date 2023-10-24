package org.jeecg.modules.cable.service.systemEcable.Impl;

import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbSteelbandDao;
import org.jeecg.modules.cable.entity.systemEcable.EcbSteelBand;
import org.jeecg.modules.cable.service.systemEcable.EcbSteelbandService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbSteelbandServiceImpl implements EcbSteelbandService {
    @Resource
    EcbSteelbandDao ecbSteelbandDao;

    @Override
    public List<EcbSteelBand> getList(EcbSteelBand record) {//插入
        return ecbSteelbandDao.getList(record);
    }

    @Override
    public List<EcbSteelBand> getListStart(EcbSteelBand record) {
        return ecbSteelbandDao.getListStart(record);
    }

    @Override
    public long getCount() {
        return ecbSteelbandDao.getCount();
    }

    @Override
    public EcbSteelBand getObject(EcbSteelBand record) {
        return ecbSteelbandDao.getObject(record);
    }
}
