package org.jeecg.modules.cable.service.efficiency;

import org.jeecg.modules.cable.entity.efficiency.EcdArea;

import java.util.List;

public interface EcdAreaService {
    List<EcdArea> getList(EcdArea record);

    long getCount(EcdArea record);

    int insert(EcdArea record);

    int update(EcdArea record);

    EcdArea getObject(EcdArea record);
}
