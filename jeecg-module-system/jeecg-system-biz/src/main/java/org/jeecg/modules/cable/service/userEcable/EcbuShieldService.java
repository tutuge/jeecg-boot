package org.jeecg.modules.cable.service.userEcable;

import org.jeecg.modules.cable.entity.userEcable.EcbuShield;

import java.util.List;

public interface EcbuShieldService {
    EcbuShield getObject(EcbuShield record);

    int insert(EcbuShield record);

    int update(EcbuShield record);

    List<EcbuShield> getList(EcbuShield record);

    int delete(EcbuShield record);
}
