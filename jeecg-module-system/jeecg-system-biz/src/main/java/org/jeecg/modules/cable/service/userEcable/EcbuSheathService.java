package org.jeecg.modules.cable.service.userEcable;

import org.jeecg.modules.cable.entity.userEcable.EcbuSheath;

import java.util.List;

public interface EcbuSheathService {
    EcbuSheath getObject(EcbuSheath record);

    int insert(EcbuSheath record);

    int update(EcbuSheath record);

    List<EcbuSheath> getList(EcbuSheath record);

    int delete(EcbuSheath record);
}
