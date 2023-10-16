package org.jeecg.modules.cable.mapper.dao.user;

import org.jeecg.modules.cable.entity.user.EcuLogin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EcuLoginDao {
    int insert(EcuLogin record);//插入

    EcuLogin getObject(EcuLogin record);//根据EcuLogin获取EcuLogin

    int updateTokenPassEcuId(EcuLogin record);//更新token
}
