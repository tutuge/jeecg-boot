package org.jeecg.modules.cable.service.systemCommon;

import org.jeecg.modules.cable.controller.systemCommon.platformCompany.vo.EcbPlatformCompanyVo;
import org.jeecg.modules.cable.entity.systemCommon.EcbPlatformCompany;

import java.util.List;

public interface EcbPlatformCompanyService {

    List<EcbPlatformCompanyVo> getList(EcbPlatformCompany record);

    long getCount(EcbPlatformCompany record);

    EcbPlatformCompanyVo getObject(EcbPlatformCompany record);

    Integer insert(EcbPlatformCompany record);
    //update
    Integer update(EcbPlatformCompany record);
    //delete
    Integer delete(EcbPlatformCompany record);
}
