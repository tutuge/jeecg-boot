package org.jeecg.modules.cable.service.userEcable;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.cable.controller.userEcable.SilkModel.vo.SilkModelVo;
import org.jeecg.modules.cable.entity.userEcable.EcuSilkModel;

public interface EcuSilkModelService extends IService<EcuSilkModel> {

    IPage<SilkModelVo> selectpage(Page<EcuSilkModel> page, EcuSilkModel ecuSilkModel);

    SilkModelVo getVoById(Integer id);
}
