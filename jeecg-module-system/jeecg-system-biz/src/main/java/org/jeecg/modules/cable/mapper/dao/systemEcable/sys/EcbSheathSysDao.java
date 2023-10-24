package org.jeecg.modules.cable.mapper.dao.systemEcable.sys;

import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.systemEcable.EcbSheath;

import java.util.List;

@Mapper
public interface EcbSheathSysDao {

    EcbSheath getObject(EcbSheath record);

    List<EcbSheath> getList(EcbSheath record);

    long getCount(EcbSheath record);

    int insert(EcbSheath record);

    int update(EcbSheath record);

    int delete(EcbSheath record);
}
