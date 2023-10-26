package org.jeecg.modules.cable.service.user.impl;

import org.jeecg.modules.cable.mapper.dao.user.EcuNoticeDao;
import org.jeecg.modules.cable.entity.user.EcuNotice;
import org.jeecg.modules.cable.service.user.EcuNoticeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcuNoticeServiceImpl implements EcuNoticeService {
    @Resource
    EcuNoticeDao ecuNoticeDao;


    @Override
    public EcuNotice getObject(EcuNotice record) {
        return ecuNoticeDao.getObject(record);
    }

    @Override
    public List<EcuNotice> getList(EcuNotice record) {
        return ecuNoticeDao.getList(record);
    }

    @Override
    public long getCount(EcuNotice record) {
        return ecuNoticeDao.getCount(record);
    }

    @Override
    public Integer insert(EcuNotice record) {
        return ecuNoticeDao.insert(record);
    }

    @Override
    public Integer update(EcuNotice record) {
        return ecuNoticeDao.update(record);
    }

    @Override
    public Integer delete(EcuNotice record) {
        return ecuNoticeDao.delete(record);
    }
}
