package org.jeecg.modules.cable.service.userCommon;

import org.jeecg.modules.cable.entity.userCommon.EcduciPosition;

public interface EcduciPositionService {
    EcduciPosition getObject(EcduciPosition record);

    Integer insert(EcduciPosition record);

    Integer delete(EcduciPosition record);

    Integer update(EcduciPosition record);
}
