package org.jeecg.modules.cable.mapper.dao.systemEcable.sys;

import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.systemEcable.EcbSheath;

import java.util.List;

@Mapper
public interface EcbSheathSysDao {

    EcbSheath getObject(EcbSheath record);

    List<EcbSheath> getList(EcbSheath record);

    long getCount(EcbSheath record);

    Integer insert(EcbSheath record);

    Integer update(EcbSheath record);

    Integer delete(EcbSheath record);
}
