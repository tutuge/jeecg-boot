package org.jeecg.modules.cable.service.quality;

import org.jeecg.modules.cable.entity.quality.EcquLevel;

import java.util.List;

public interface EcquLevelService {
    List<EcquLevel> getList(EcquLevel record);

    long getCount(EcquLevel record);

    EcquLevel getObject(EcquLevel record);

    Integer insert(EcquLevel record);

    Integer update(EcquLevel record);

    Integer delete(EcquLevel record);
}
