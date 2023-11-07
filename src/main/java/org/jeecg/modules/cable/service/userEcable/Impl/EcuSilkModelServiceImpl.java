package org.jeecg.modules.cable.service.userEcable.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.jeecg.modules.cable.controller.userEcable.SilkModel.vo.SilkModelVo;
import org.jeecg.modules.cable.entity.userEcable.EcuSilkModel;
import org.jeecg.modules.cable.mapper.dao.userEcable.EcuSilkModelMapper;
import org.jeecg.modules.cable.service.userEcable.EcuSilkModelService;
import org.springframework.stereotype.Service;

@Service
public class EcuSilkModelServiceImpl extends ServiceImpl<EcuSilkModelMapper, EcuSilkModel> implements EcuSilkModelService {
    @Resource
    EcuSilkModelMapper ecuSilkModelMapper;

    @Override
    public IPage<SilkModelVo> selectpage(Page<EcuSilkModel> page, EcuSilkModel ecuSilkModel) {
        return ecuSilkModelMapper.selectPageData(page, ecuSilkModel);
    }

    @Override
    public SilkModelVo getVoById(Integer id) {
        SilkModelVo voById = ecuSilkModelMapper.getVoById(id);
        return voById;
    }
}
