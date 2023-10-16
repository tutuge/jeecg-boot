package org.jeecg.modules.cable.mapper.dao.userEcable;

import org.jeecg.modules.cable.entity.userEcable.EcbuInsulation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcbuInsulationDao {
    EcbuInsulation getObject(EcbuInsulation record);

    int insert(EcbuInsulation ecbuInsulation);

    int update(EcbuInsulation record);

    List<EcbuInsulation> getList(EcbuInsulation record);

    int delete(EcbuInsulation record);
}
