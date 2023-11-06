package org.jeecg.modules.cable.service.systemEcable;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;

import java.util.List;

public interface EcSilkService extends IService<EcSilk> {
    List<EcSilk> getList(EcSilk record);


    EcSilk getObject(EcSilk record);


    IPage<EcSilk> selectPage(Page<EcSilk> page, EcSilk ecSilk);
}
