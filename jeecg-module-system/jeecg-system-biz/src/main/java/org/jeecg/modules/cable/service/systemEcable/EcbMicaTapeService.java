package org.jeecg.modules.cable.service.systemEcable;

import org.jeecg.modules.cable.entity.systemEcable.EcbMicaTape;

import java.util.List;

public interface EcbMicaTapeService {
    List<EcbMicaTape> getList(EcbMicaTape record);

    List<EcbMicaTape> getListStart(EcbMicaTape record);

    long getCount();

    EcbMicaTape getObject(EcbMicaTape record);
}
