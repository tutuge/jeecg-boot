package org.jeecg.modules.cable.service.quality;

import org.jeecg.modules.cable.entity.quality.EcquLevel;

import java.util.List;

public interface EcquLevelService {
    List<EcquLevel> getList(EcquLevel record);

    long getCount(EcquLevel record);

    EcquLevel getObject(EcquLevel record);

    int insert(EcquLevel record);

    int update(EcquLevel record);

    int delete(EcquLevel record);
}
