package org.jeecg.modules.cable.mapper.dao.systemEcable.sys;

import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.systemEcable.EcbShield;

import java.util.List;

@Mapper
public interface EcbShieldSysDao {

    EcbShield getObject(EcbShield record);
    List<EcbShield> getList(EcbShield record);
    long getCount(EcbShield record);
    Integer insert(EcbShield record);
    Integer update(EcbShield record);
    Integer delete(EcbShield record);
}
