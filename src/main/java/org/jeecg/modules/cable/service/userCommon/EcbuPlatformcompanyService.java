package org.jeecg.modules.cable.service.userCommon;

import org.jeecg.modules.cable.controller.userCommon.pcompany.vo.EcbuPCompanyVo;
import org.jeecg.modules.cable.entity.userCommon.EcbuPlatformCompany;

import java.util.List;

public interface EcbuPlatformcompanyService {
    List<EcbuPCompanyVo> getList(EcbuPlatformCompany record);

    long getCount(EcbuPlatformCompany record);

    EcbuPCompanyVo getObject(EcbuPlatformCompany record);

    Integer insert(EcbuPlatformCompany record);

    Integer update(EcbuPlatformCompany record);

    Integer delete(EcbuPlatformCompany record);


    List<EcbuPlatformCompany> getListGreaterThanSortId(EcbuPlatformCompany record);

    //getObjectPassPcName
    EcbuPlatformCompany getObjectPassPcName(EcbuPlatformCompany record);


    EcbuPlatformCompany getLatestObject(EcbuPlatformCompany record);
}
