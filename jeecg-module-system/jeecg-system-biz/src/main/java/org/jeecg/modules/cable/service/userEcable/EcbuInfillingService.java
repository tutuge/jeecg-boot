package org.jeecg.modules.cable.service.userEcable;

import org.jeecg.modules.cable.entity.userEcable.EcbuInfilling;

import java.util.List;

public interface EcbuInfillingService {
    EcbuInfilling getObject(EcbuInfilling record);

    int insert(EcbuInfilling record);

    int update(EcbuInfilling record);

    List<EcbuInfilling> getList(EcbuInfilling record);

    int delete(EcbuInfilling record);
}
