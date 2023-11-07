package org.jeecg.modules.cable.service.systemDelivery.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.jeecg.modules.cable.controller.systemEcable.SilkModel.vo.EcSilkModelVo;
import org.jeecg.modules.cable.entity.systemEcable.EcSilkModel;
import org.jeecg.modules.cable.mapper.dao.systemEcable.EcSilkModelMapper;
import org.jeecg.modules.cable.service.systemDelivery.EcSilkModelService;
import org.springframework.stereotype.Service;

@Service
public class EcSilkModelServiceImpl extends ServiceImpl<EcSilkModelMapper, EcSilkModel> implements EcSilkModelService {
    @Resource
    EcSilkModelMapper ecSilkModelMapper;

    @Override
    public IPage<EcSilkModelVo> selectPageData(Page<EcSilkModel> page, EcSilkModel ecSilkModel) {
        return ecSilkModelMapper.selectPageData(page,ecSilkModel);
    }

    @Override
    public EcSilkModelVo getVoById(Integer id) {
        return ecSilkModelMapper.getVoById(id);
    }
}
