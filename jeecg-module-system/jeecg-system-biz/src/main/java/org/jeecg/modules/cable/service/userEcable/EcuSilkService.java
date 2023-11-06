package org.jeecg.modules.cable.service.userEcable;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.cable.entity.userEcable.EcuSilk;

import java.util.List;

public interface EcuSilkService extends IService<EcuSilk> {

    List<EcuSilk> getList(EcuSilk record);


    EcuSilk getObject(EcuSilk record);

    IPage<EcuSilk> selectPage(Page<EcuSilk> page, EcuSilk ecSilk);
}
