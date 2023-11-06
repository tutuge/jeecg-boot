package org.jeecg.modules.cable.service.efficiency;

import org.jeecg.modules.cable.entity.efficiency.EcdPcc;

public interface EcdPccService {
    EcdPcc getObject(EcdPcc record);

    Integer insert(EcdPcc record);

    //deletePassTypeId
    Integer delete(EcdPcc record);
}
