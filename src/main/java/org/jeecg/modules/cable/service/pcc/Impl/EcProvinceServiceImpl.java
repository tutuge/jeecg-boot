package org.jeecg.modules.cable.service.pcc.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.cable.mapper.dao.pcc.EcProvinceMapper;
import org.jeecg.modules.cable.entity.pcc.EcProvince;
import org.jeecg.modules.cable.service.pcc.EcProvinceService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcProvinceServiceImpl extends ServiceImpl<EcProvinceMapper,EcProvince> implements EcProvinceService {
    @Resource
    EcProvinceMapper ecProvinceMapper;

    @Override
    public List<EcProvince> getList(EcProvince record) {
        return ecProvinceMapper.getList(record);
    }

    @Override
    public EcProvince getObject(EcProvince record)
    {
        return ecProvinceMapper.getObject(record);
    }

}
