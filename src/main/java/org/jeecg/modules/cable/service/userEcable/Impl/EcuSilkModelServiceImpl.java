package org.jeecg.modules.cable.service.userEcable.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.userEcable.EcuSilkModel;
import org.jeecg.modules.cable.mapper.dao.userEcable.EcuSilkModelMapper;
import org.jeecg.modules.cable.service.userEcable.EcuSilkModelService;
import org.springframework.stereotype.Service;

@Service
public class EcuSilkModelServiceImpl extends ServiceImpl<EcuSilkModelMapper, EcuSilkModel> implements EcuSilkModelService {
    @Resource
    EcuSilkModelMapper ecuSilkModelMapper;
}
