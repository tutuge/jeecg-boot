package org.jeecg.modules.cable.service.userEcable;

import org.jeecg.modules.cable.entity.userEcable.EcbuShield;

import java.util.List;

public interface EcbuShieldService {
    EcbuShield getObject(EcbuShield record);

    Integer insert(EcbuShield record);

    Integer update(EcbuShield record);

    List<EcbuShield> getList(EcbuShield record);

    Integer deleteByEcCompanyId(EcbuShield record);

    EcbuShield getObjectById(Integer ecbusbId);
}
