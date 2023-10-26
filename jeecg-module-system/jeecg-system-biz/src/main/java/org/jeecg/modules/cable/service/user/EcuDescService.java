package org.jeecg.modules.cable.service.user;

import org.jeecg.modules.cable.entity.user.EcuDesc;

import java.util.List;

public interface EcuDescService {
    EcuDesc getObject(EcuDesc record);

    List<EcuDesc> getList(EcuDesc record);

    long getCount(EcuDesc record);

    Integer insert(EcuDesc record);

    Integer update(EcuDesc record);

    Integer delete(EcuDesc record);
}
