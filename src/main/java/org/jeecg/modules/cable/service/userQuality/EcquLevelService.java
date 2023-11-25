package org.jeecg.modules.cable.service.userQuality;

import org.jeecg.modules.cable.entity.userQuality.EcquLevel;

import java.util.List;

public interface EcquLevelService {
    List<EcquLevel> getList(EcquLevel record);

    long getCount(EcquLevel record);

    EcquLevel getObject(EcquLevel record);

    Integer insert(EcquLevel record);

    Integer update(EcquLevel record);

    Integer delete(Integer ecqulId);
}
