package org.jeecg.modules.cable.mapper.dao.systemEcable.sys;

import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.systemEcable.EcbMicatape;

import java.util.List;

@Mapper
public interface EcbMicatapeSysDao {

    EcbMicatape getObject(EcbMicatape record);

    List<EcbMicatape> getList(EcbMicatape record);

    long getCount(EcbMicatape record);

    int insert(EcbMicatape record);

    int update(EcbMicatape record);

    int delete(EcbMicatape record);
}
