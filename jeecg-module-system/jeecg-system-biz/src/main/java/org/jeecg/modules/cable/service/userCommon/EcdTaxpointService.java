package org.jeecg.modules.cable.service.userCommon;

import org.jeecg.modules.cable.controller.userCommon.taxpoint.bo.TaxPointBaseBo;
import org.jeecg.modules.cable.entity.systemEcable.EcdTaxPoint;

import java.util.List;

public interface EcdTaxpointService {
    //getList
    List<EcdTaxPoint> getList(EcdTaxPoint record);

    //getCount
    long getCount(EcdTaxPoint record);

    //getObject
    EcdTaxPoint getObject(EcdTaxPoint record);

    void insert(EcdTaxPoint record);

    void update(EcdTaxPoint record);

    void delete(Integer ecdtId);
}
