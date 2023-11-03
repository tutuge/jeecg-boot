package org.jeecg.modules.cable.mapper.dao.user;

import org.jeecg.modules.cable.entity.user.EcuCode;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EcuCodeMapper {

    EcuCode getObject(EcuCode record);//根据ID查找

    Integer insert(EcuCode record);

    Integer update(EcuCode record);

}
