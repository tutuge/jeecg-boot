package org.jeecg.modules.cable.mapper.dao.systemEcable.sys;

import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.systemEcable.EcbInsulation;

import java.util.List;

@Mapper
public interface EcbInsulationSysDao {
    EcbInsulation getObject(EcbInsulation record);

    List<EcbInsulation> getList(EcbInsulation record);

    long getCount(EcbInsulation record);

    int insert(EcbInsulation record);

    int update(EcbInsulation record);

    int delete(EcbInsulation record);
}
