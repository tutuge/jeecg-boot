package org.jeecg.modules.cable.service.efficiency;

import org.jeecg.modules.cable.entity.efficiency.EcduPcc;

public interface EcduPccService {
    //getObject
    EcduPcc getObject(EcduPcc record);

    //insert
    int insert(EcduPcc record);

    //deletePassEcCompanyIdAndTypeId
    int delete(EcduPcc record);
}
