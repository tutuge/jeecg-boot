package org.jeecg.modules.cable.service.user;

import org.jeecg.modules.cable.entity.user.EcuCode;

public interface EcuCodeService {
    EcuCode getObject(EcuCode record);//根据ID查找

    int insert(EcuCode record);

    int update(EcuCode record);
}
