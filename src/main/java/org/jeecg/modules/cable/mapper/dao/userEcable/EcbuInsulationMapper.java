package org.jeecg.modules.cable.mapper.dao.userEcable;

import org.jeecg.modules.cable.entity.userEcable.EcbuInsulation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcbuInsulationMapper {
    EcbuInsulation getObject(EcbuInsulation record);

    Integer insert(EcbuInsulation ecbuInsulation);

    Integer update(EcbuInsulation record);

    List<EcbuInsulation> getList(EcbuInsulation record);

    Integer delete(EcbuInsulation record);
}
