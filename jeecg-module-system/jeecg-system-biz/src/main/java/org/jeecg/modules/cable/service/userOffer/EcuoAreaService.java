package org.jeecg.modules.cable.service.userOffer;

import org.jeecg.modules.cable.entity.userOffer.EcuoArea;

import java.util.List;

public interface EcuoAreaService {
    List<EcuoArea> getList(EcuoArea record);

    EcuoArea getObject(EcuoArea record);

    int insert(EcuoArea record);

    int update(EcuoArea record);
}
