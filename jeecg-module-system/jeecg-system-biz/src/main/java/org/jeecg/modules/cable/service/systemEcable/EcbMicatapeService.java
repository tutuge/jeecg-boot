package org.jeecg.modules.cable.service.systemEcable;

import org.jeecg.modules.cable.entity.systemEcable.EcbMicatape;

import java.util.List;

public interface EcbMicatapeService {
    List<EcbMicatape> getList(EcbMicatape record);

    List<EcbMicatape> getListStart(EcbMicatape record);

    long getCount();

    EcbMicatape getObject(EcbMicatape record);
}
