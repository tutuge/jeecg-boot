package org.jeecg.modules.cable.service.systemDelivery.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.systemEcable.EcSilkModel;
import org.jeecg.modules.cable.mapper.dao.systemEcable.EcSilkModelMapper;
import org.jeecg.modules.cable.service.systemDelivery.EcSilkModelService;
import org.springframework.stereotype.Service;

@Service
public class EcSilkModelServiceImpl extends ServiceImpl<EcSilkModelMapper, EcSilkModel> implements EcSilkModelService {
    @Resource
    EcSilkModelMapper ecSilkModelMapper;
}
