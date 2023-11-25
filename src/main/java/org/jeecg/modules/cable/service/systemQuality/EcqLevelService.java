package org.jeecg.modules.cable.service.systemQuality;


import org.jeecg.modules.cable.entity.systemQuality.EcqLevel;

import java.util.List;

public interface EcqLevelService {
    List<EcqLevel> getList(EcqLevel record);

    long getCount(EcqLevel record);

    EcqLevel getObject(EcqLevel record);

    Integer insert(EcqLevel record);

    Integer update(EcqLevel record);

    Integer delete(Integer ecqulId);
}
