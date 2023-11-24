package org.jeecg.modules.cable.service.userCommon;

import org.jeecg.modules.cable.entity.userCommon.EcduCompany;

import java.util.List;

public interface EcduCompanyService {
    List<EcduCompany> getList(EcduCompany record);

    long getCount(EcduCompany record);

    EcduCompany getObject(EcduCompany record);

    EcduCompany getObjectByCompanyId(Integer companyId);

    Integer insert(EcduCompany record);

    Integer update(EcduCompany record);

    Integer delete(EcduCompany record);

    
    List<EcduCompany> getListGreaterThanSortId(EcduCompany record);

    //getObjectPassAbbreviationAndFullName
    EcduCompany getObjectPassAbbreviationAndFullName(EcduCompany record);


    EcduCompany getLatestObject(EcduCompany record);
}
