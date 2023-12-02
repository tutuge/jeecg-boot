package org.jeecg.modules.cable.service.user.impl;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.user.EcuNotice;
import org.jeecg.modules.cable.mapper.dao.user.EcuNoticeMapper;
import org.jeecg.modules.cable.service.user.EcuNoticeService;
import org.springframework.stereotype.Service;

import java.util.Date;
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
        record.setCreateTime(new Date());
        return ecuNoticeMapper.insert(record);
    }

    @Override
    public Integer update(EcuNotice record) {
        record.setUpdateTime(new Date());
        LambdaUpdateWrapper<EcuNotice> eq = Wrappers.lambdaUpdate(EcuNotice.class)
                .eq(ObjUtil.isNotNull(record.getEcuId()), EcuNotice::getEcuId, record.getEcuId())
                .eq(ObjUtil.isNotNull(record.getEcunId()), EcuNotice::getEcunId, record.getEcunId());
        return ecuNoticeMapper.update(record, eq);
    }

    @Override
    public Integer delete(EcuNotice record) {
        return ecuNoticeMapper.delete(record);
    }

    @Override
    public EcuNotice getObjectForQuoted(Integer ecuId) {
        EcuNotice notice = new EcuNotice();
        notice.setEcuId(ecuId);
        notice.setStartType(true);
        List<EcuNotice> list = ecuNoticeMapper.getList(notice);
        EcuNotice res = null;
        for (EcuNotice ecuNotice : list) {
            if (ecuNotice.getDefaultType()) {
                res = ecuNotice;
                break;
            }
        }
        if (ObjUtil.isNull(res) && !list.isEmpty()) {
            res = list.get(0);
        }
        return res;
    }
}
