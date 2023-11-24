package org.jeecg.modules.cable.service.userEcable;

import org.jeecg.modules.cable.entity.userEcable.EcbuMicaTape;

import java.util.List;

public interface EcbuMicaTapeService {
    EcbuMicaTape getObject(EcbuMicaTape record);

    Integer insert(EcbuMicaTape record);

    Integer update(EcbuMicaTape record);

    List<EcbuMicaTape> getList(EcbuMicaTape record);

    Integer delete(EcbuMicaTape record);

    EcbuMicaTape getObjectById(Integer ecbumId);
}
