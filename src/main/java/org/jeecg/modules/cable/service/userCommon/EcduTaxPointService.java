package org.jeecg.modules.cable.service.userCommon;

import org.jeecg.modules.cable.entity.userCommon.EcduTaxPoint;

import java.util.List;

public interface EcduTaxPointService {

    List<EcduTaxPoint> getList(EcduTaxPoint record);


    long getCount(EcduTaxPoint record);


    EcduTaxPoint getObject(EcduTaxPoint record);


    Integer insert(EcduTaxPoint record);

    //updateByPrimaryKeySelective
    Integer updateByPrimaryKeySelective(EcduTaxPoint record);

    //deletePassEcCompanyIdAndEcdtId
    Integer deletePassEcCompanyIdAndEcdtId(EcduTaxPoint record);
}
