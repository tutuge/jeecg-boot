package org.jeecg.modules.cable.service.price;

import org.jeecg.modules.cable.entity.systemEcable.EcSilk;

import java.util.List;

public interface EcSilkService {
    List<EcSilk> getList(EcSilk record);

    //getObject
    EcSilk getObject(EcSilk record);
}
