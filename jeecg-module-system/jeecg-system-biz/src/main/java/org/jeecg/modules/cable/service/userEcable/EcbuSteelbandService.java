package org.jeecg.modules.cable.service.userEcable;

import org.jeecg.modules.cable.entity.userEcable.EcbuSteelband;

import java.util.List;

public interface EcbuSteelbandService {
    EcbuSteelband getObject(EcbuSteelband record);

    int insert(EcbuSteelband record);

    int update(EcbuSteelband record);

    List<EcbuSteelband> getList(EcbuSteelband record);

    int delete(EcbuSteelband record);
}
