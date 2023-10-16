package org.jeecg.modules.cable.mapper.dao.systemEcable;

import org.jeecg.modules.cable.entity.systemEcable.EcbShield;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcbShieldDao {
    List<EcbShield> getList(EcbShield record);//获取数据列表

    List<EcbShield> getListStart(EcbShield record);//获取启用列表

    long getCount();//获取总数

    EcbShield getObject(EcbShield record);//根据EcbShield获取EcbShield
}
