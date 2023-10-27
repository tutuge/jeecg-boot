package org.jeecg.modules.cable.service.price;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;

import java.util.List;

public interface EcSilkService extends IService<EcSilk> {
    List<EcSilk> getList(EcSilk record);

    // getObject
    EcSilk getObject(EcSilk record);
}
