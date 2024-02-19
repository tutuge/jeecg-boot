package org.jeecg.modules.cable.service.userCommon;

import org.jeecg.modules.cable.entity.systemCommon.EcdTaxPoint;

import java.util.List;

public interface EcdTaxPointService {

    List<EcdTaxPoint> selectList(EcdTaxPoint record);


    long getCount(EcdTaxPoint record);


    EcdTaxPoint getObject(EcdTaxPoint record);

    void insert(EcdTaxPoint record);

    void update(EcdTaxPoint record);

    void delete(Integer ecdtId);
}
