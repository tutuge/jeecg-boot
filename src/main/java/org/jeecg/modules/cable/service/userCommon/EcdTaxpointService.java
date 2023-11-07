package org.jeecg.modules.cable.service.userCommon;

import org.jeecg.modules.cable.controller.userCommon.taxpoint.bo.TaxPointBaseBo;
import org.jeecg.modules.cable.entity.systemEcable.EcdTaxPoint;

import java.util.List;

public interface EcdTaxpointService {

    List<EcdTaxPoint> getList(EcdTaxPoint record);


    long getCount(EcdTaxPoint record);


    EcdTaxPoint getObject(EcdTaxPoint record);

    void insert(EcdTaxPoint record);

    void update(EcdTaxPoint record);

    void delete(Integer ecdtId);
}
