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
    int insert(EcduTaxpoint record);

    //updateByPrimaryKeySelective
    int updateByPrimaryKeySelective(EcduTaxpoint record);

    //deletePassEcCompanyIdAndEcdtId
    int deletePassEcCompanyIdAndEcdtId(EcduTaxpoint record);
}
