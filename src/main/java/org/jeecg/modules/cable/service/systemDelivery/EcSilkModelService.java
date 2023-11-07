package org.jeecg.modules.cable.service.systemDelivery;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.cable.controller.systemEcable.SilkModel.vo.EcSilkModelVo;
import org.jeecg.modules.cable.entity.systemEcable.EcSilkModel;

public interface EcSilkModelService extends IService<EcSilkModel> {

    IPage<EcSilkModelVo> selectPageData(Page<EcSilkModel> page, EcSilkModel ecSilkModel);

    EcSilkModelVo getVoById(Integer id);
}
