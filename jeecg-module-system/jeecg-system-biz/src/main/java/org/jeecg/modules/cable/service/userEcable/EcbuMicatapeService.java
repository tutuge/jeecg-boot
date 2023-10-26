package org.jeecg.modules.cable.service.userEcable;

import org.jeecg.modules.cable.entity.userEcable.EcbuMicatape;

import java.util.List;

public interface EcbuMicatapeService {
    EcbuMicatape getObject(EcbuMicatape record);

    Integer insert(EcbuMicatape record);

    Integer update(EcbuMicatape record);

    List<EcbuMicatape> getList(EcbuMicatape record);

    Integer delete(EcbuMicatape record);
}
