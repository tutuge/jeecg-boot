package org.jeecg.modules.cable.service.efficiency;

import org.jeecg.modules.cable.entity.efficiency.EcduPcc;

public interface EcduPccService {
    //getObject
    EcduPcc getObject(EcduPcc record);

    //insert
    Integer insert(EcduPcc record);

    //deletePassEcCompanyIdAndTypeId
    Integer delete(EcduPcc record);
}
