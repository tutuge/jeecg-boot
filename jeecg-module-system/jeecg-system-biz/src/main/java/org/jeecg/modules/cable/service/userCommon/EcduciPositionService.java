package org.jeecg.modules.cable.service.userCommon;

import org.jeecg.modules.cable.entity.userCommon.EcduciPosition;

public interface EcduciPositionService {
    EcduciPosition getObject(EcduciPosition record);

    int insert(EcduciPosition record);

    int delete(EcduciPosition record);

    int update(EcduciPosition record);
}
