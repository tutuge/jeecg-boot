package org.jeecg.modules.cable.mapper.dao.user;

import org.jeecg.modules.cable.entity.user.EcUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcUserDao {

    EcUser getObject(EcUser record);//根据ID查找

    List<EcUser> getList(EcUser record);//获取用户列表

    long getCount(EcUser record);

    //insert
    int insert(EcUser record);

    //getObjectPassEcUsername
    EcUser getObjectPassEcUsername(EcUser record);

    //getObjectPassEcPhone
    EcUser getObjectPassEcPhone(EcUser record);

    //getObjectPassCode
    EcUser getObjectPassCode(EcUser record);

    int update(EcUser record);

}
