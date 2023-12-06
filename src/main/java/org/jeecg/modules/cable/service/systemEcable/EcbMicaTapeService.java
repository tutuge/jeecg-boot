package org.jeecg.modules.cable.service.systemEcable;

import cn.hutool.core.lang.Pair;
import org.jeecg.modules.cable.entity.systemEcable.EcbMicaTape;

import java.util.List;
import java.util.Map;

public interface EcbMicaTapeService {
    List<EcbMicaTape> getList(EcbMicaTape record);

    List<EcbMicaTape> getListStart(EcbMicaTape record);

    long getCount();

    EcbMicaTape getObject(EcbMicaTape record);

    Pair<Map<String, Integer>, Map<String, Integer>> getObjectPassMicaTape();
}
