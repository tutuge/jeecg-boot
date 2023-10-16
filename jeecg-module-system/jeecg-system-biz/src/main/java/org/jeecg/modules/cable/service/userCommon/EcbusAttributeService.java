package org.jeecg.modules.cable.service.userCommon;

import org.jeecg.modules.cable.entity.userCommon.EcbusAttribute;

public interface EcbusAttributeService {
    EcbusAttribute getObject(EcbusAttribute record);

    int insert(EcbusAttribute record);

    int update(EcbusAttribute record);
}
