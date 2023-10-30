package org.jeecg.modules.cable.mapper.dao.user;

import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.user.EcuLogin;

@Mapper
public interface EcuLoginDao {
    Integer insert(EcuLogin record);// 插入

    EcuLogin getObject(EcuLogin record);// 根据EcuLogin获取EcuLogin

    Integer updateTokenPassEcuId(EcuLogin record);// 更新token
}
