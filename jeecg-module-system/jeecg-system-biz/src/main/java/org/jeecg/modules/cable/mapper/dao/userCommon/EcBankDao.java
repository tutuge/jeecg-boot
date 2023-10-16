package org.jeecg.modules.cable.mapper.dao.userCommon;

import org.jeecg.modules.cable.entity.userCommon.EcBank;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EcBankDao {
    //getObject
    EcBank getObject(EcBank record);
}
