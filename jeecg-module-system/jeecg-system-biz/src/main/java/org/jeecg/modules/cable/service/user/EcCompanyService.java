package org.jeecg.modules.cable.service.user;


import org.jeecg.modules.cable.entity.user.EcCompany;

public interface EcCompanyService {
    EcCompany getObject(EcCompany record);//通过EcUser获取EcUser

    int insert(EcCompany record);
}
