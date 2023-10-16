package org.jeecg.modules.cable.mapper.dao.user;

import org.jeecg.modules.cable.entity.user.EcuCode;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EcuCodeDao {

    EcuCode getObject(EcuCode record);//根据ID查找

    int insert(EcuCode record);

    int update(EcuCode record);

}
