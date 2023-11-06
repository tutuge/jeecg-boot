package org.jeecg.modules.cable.service.user;

import org.jeecg.modules.cable.entity.user.EcuLogin;

public interface EcuLoginService {

    Integer insert(EcuLogin record);//插入

    EcuLogin getObject(EcuLogin record);//根据EcuLogin获取EcuLogin

    Integer updateTokenPassEcuId(EcuLogin record);//更新token
}
