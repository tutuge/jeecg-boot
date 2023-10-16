package org.jeecg.modules.cable.service.user;

import org.jeecg.modules.cable.entity.user.EcuData;

import java.util.List;

public interface EcuDataService {
    EcuData getObject(EcuData record);

    List<EcuData> getList(EcuData record);

    long getCount(EcuData record);

    //insert
    int insert(EcuData record);

    int update(EcuData record);
}
