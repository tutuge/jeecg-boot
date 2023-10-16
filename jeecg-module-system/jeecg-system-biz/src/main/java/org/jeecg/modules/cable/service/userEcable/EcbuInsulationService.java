package org.jeecg.modules.cable.service.userEcable;

import org.jeecg.modules.cable.entity.userEcable.EcbuInsulation;

import java.util.List;

public interface EcbuInsulationService {
    EcbuInsulation getObject(EcbuInsulation record);

    int insert(EcbuInsulation record);

    int update(EcbuInsulation record);

    List<EcbuInsulation> getList(EcbuInsulation record);

    int delete(EcbuInsulation record);
}
