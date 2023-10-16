package org.jeecg.modules.cable.service.efficiency;

import org.jeecg.modules.cable.entity.efficiency.EcdPcc;

public interface EcdPccService {
    EcdPcc getObject(EcdPcc record);

    int insert(EcdPcc record);

    //deletePassTypeId
    int delete(EcdPcc record);
}
