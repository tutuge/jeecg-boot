package org.jeecg.modules.cable.service.userCommon;

import org.jeecg.modules.cable.entity.userCommon.EcduTaxpoint;

import java.util.List;

public interface EcduTaxpointService {
    //getList
    List<EcduTaxpoint> getList(EcduTaxpoint record);

    //getCount
    long getCount(EcduTaxpoint record);

    //getObject
    EcduTaxpoint getObject(EcduTaxpoint record);

    //insert
    Integer insert(EcduTaxpoint record);

    //updateByPrimaryKeySelective
    Integer updateByPrimaryKeySelective(EcduTaxpoint record);

    //deletePassEcCompanyIdAndEcdtId
    Integer deletePassEcCompanyIdAndEcdtId(EcduTaxpoint record);
}
