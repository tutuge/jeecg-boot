package org.jeecg.modules.cable.service.price;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.cable.controller.systemEcable.silk.vo.SilkVo;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;

import java.util.List;

public interface EcSilkService extends IService<EcSilk> {
    List<EcSilk> getList(EcSilk record);


    SilkVo getObject(EcSilk record);


    IPage<SilkVo> selectPage(Page<EcSilk> page, EcSilk ecSilk);
}
