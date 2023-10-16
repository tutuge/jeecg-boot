package org.jeecg.modules.cable.service.user;


import org.jeecg.common.system.vo.EcUser;

import java.util.List;

public interface EcUserService {
    EcUser getObject(EcUser record);//通过EcUser获取EcUser

    List<EcUser> getList(EcUser record);

    long getCount(EcUser record);

    int insert(EcUser record);

    //getObjectPassEcUsername
    EcUser getObjectPassEcUsername(EcUser record);

    //getObjectPassEcPhone
    EcUser getObjectPassEcPhone(EcUser record);

    //getObjectPassCode
    EcUser getObjectPassCode(EcUser record);

    int update(EcUser record);
}
