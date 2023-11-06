package org.jeecg.modules.cable.service.systemEcable;

import org.jeecg.modules.cable.entity.systemEcable.EcbInfilling;

import java.util.List;

public interface EcbInfillingService {
    List<EcbInfilling> getList(EcbInfilling record);

    List<EcbInfilling> getListStart(EcbInfilling record);

    long getCount();

    EcbInfilling getObject(EcbInfilling record);
}
