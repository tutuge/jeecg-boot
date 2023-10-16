package org.jeecg.modules.cable.service.systemEcable.Impl;

import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbSheathDao;
import org.jeecg.modules.cable.entity.systemEcable.EcbSheath;
import org.jeecg.modules.cable.service.systemEcable.EcbSheathService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbSheathServiceImpl implements EcbSheathService {
    @Resource
    EcbSheathDao ecbSheathDao;

    @Override
    public List<EcbSheath> getList(EcbSheath record) {//插入

        return ecbSheathDao.getList(record);
    }

    @Override
    public List<EcbSheath> getListStart(EcbSheath record) {
        return ecbSheathDao.getListStart(record);
    }

    @Override
    public long getCount() {
        return ecbSheathDao.getCount();
    }

    @Override
    public EcbSheath getObject(EcbSheath record) {
        return ecbSheathDao.getObject(record);
    }
}
