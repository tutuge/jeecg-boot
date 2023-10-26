package org.jeecg.modules.cable.service.efficiency;

import org.jeecg.modules.cable.entity.efficiency.EcdCollect;

import java.util.List;

public interface EcdCollectService {
    List<EcdCollect> getList(EcdCollect record);

    long getCount(EcdCollect record);

    Integer insert(EcdCollect record);

    Integer update(EcdCollect record);

    EcdCollect getObject(EcdCollect record);
}
