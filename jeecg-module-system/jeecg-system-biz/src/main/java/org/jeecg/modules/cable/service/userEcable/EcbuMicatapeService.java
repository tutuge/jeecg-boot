package org.jeecg.modules.cable.service.userEcable;

import org.jeecg.modules.cable.entity.userEcable.EcbuMicatape;

import java.util.List;

public interface EcbuMicatapeService {
    EcbuMicatape getObject(EcbuMicatape record);

    int insert(EcbuMicatape record);

    int update(EcbuMicatape record);

    List<EcbuMicatape> getList(EcbuMicatape record);

    int delete(EcbuMicatape record);
}
