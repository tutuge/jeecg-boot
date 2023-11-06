package org.jeecg.modules.cable.service.userCommon;

import org.jeecg.modules.cable.entity.userCommon.EcduTaxPoint;

import java.util.List;

public interface EcduTaxpointService {

    List<EcduTaxPoint> getList(EcduTaxPoint record);

    //getCount
    long getCount(EcduTaxPoint record);

    //getObject
    EcduTaxPoint getObject(EcduTaxPoint record);

    //insert
    Integer insert(EcduTaxPoint record);

    //updateByPrimaryKeySelective
    Integer updateByPrimaryKeySelective(EcduTaxPoint record);

    //deletePassEcCompanyIdAndEcdtId
    Integer deletePassEcCompanyIdAndEcdtId(EcduTaxPoint record);
}
