package org.jeecg.modules.cable.service.user;

import org.jeecg.modules.cable.entity.user.EcuCode;

public interface EcuCodeService {
    EcuCode getObject(EcuCode record);//根据ID查找

    Integer insert(EcuCode record);

    Integer update(EcuCode record);
}
