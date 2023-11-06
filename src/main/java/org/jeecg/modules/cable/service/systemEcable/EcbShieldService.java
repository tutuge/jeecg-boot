package org.jeecg.modules.cable.service.systemEcable;

import org.jeecg.modules.cable.entity.systemEcable.EcbShield;

import java.util.List;

public interface EcbShieldService {
    List<EcbShield> getList(EcbShield record);

    List<EcbShield> getListStart(EcbShield record);

    long getCount();

    EcbShield getObject(EcbShield record);
}
