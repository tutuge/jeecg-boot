package org.jeecg.modules.cable.service.user.impl;

import org.jeecg.modules.cable.mapper.dao.user.EcuNoticeMapper;
import org.jeecg.modules.cable.entity.user.EcuNotice;
import org.jeecg.modules.cable.service.user.EcuNoticeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcuNoticeServiceImpl implements EcuNoticeService {
    @Resource
    EcuNoticeMapper ecuNoticeMapper;


    @Override
    public EcuNotice getObject(EcuNotice record) {
        return ecuNoticeMapper.getObject(record);
    }

    @Override
    public List<EcuNotice> getList(EcuNotice record) {
        return ecuNoticeMapper.getList(record);
    }

    @Override
    public long getCount(EcuNotice record) {
        return ecuNoticeMapper.getCount(record);
    }

    @Override
    public Integer insert(EcuNotice record) {
        return ecuNoticeMapper.insert(record);
    }

    @Override
    public Integer update(EcuNotice record) {
        return ecuNoticeMapper.update(record);
    }

    @Override
    public Integer delete(EcuNotice record) {
        return ecuNoticeMapper.delete(record);
    }
}
