package org.jeecg.modules.cable.service.userCommon;

import org.jeecg.modules.cable.entity.systemEcable.EcdTaxpoint;

import java.util.List;

public interface EcdTaxpointService {
    //getList
    List<EcdTaxpoint> getList(EcdTaxpoint record);

    //getCount
    long getCount(EcdTaxpoint record);

    //getObject
    EcdTaxpoint getObject(EcdTaxpoint record);
}
