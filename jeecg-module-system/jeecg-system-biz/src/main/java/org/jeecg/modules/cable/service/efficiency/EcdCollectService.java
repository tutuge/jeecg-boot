package org.jeecg.modules.cable.service.efficiency;

import org.jeecg.modules.cable.entity.efficiency.EcdCollect;

import java.util.List;

public interface EcdCollectService {
    List<EcdCollect> getList(EcdCollect record);

    long getCount(EcdCollect record);

    int insert(EcdCollect record);

    int update(EcdCollect record);

    EcdCollect getObject(EcdCollect record);
}
