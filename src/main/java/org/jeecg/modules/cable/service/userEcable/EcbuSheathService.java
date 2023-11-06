package org.jeecg.modules.cable.service.userEcable;

import org.jeecg.modules.cable.entity.userEcable.EcbuSheath;

import java.util.List;

public interface EcbuSheathService {
    EcbuSheath getObject(EcbuSheath record);

    Integer insert(EcbuSheath record);

    Integer update(EcbuSheath record);

    List<EcbuSheath> getList(EcbuSheath record);

    Integer delete(EcbuSheath record);
}
