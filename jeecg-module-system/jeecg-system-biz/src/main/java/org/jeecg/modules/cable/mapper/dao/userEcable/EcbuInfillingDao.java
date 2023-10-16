package org.jeecg.modules.cable.mapper.dao.userEcable;

import org.jeecg.modules.cable.entity.userEcable.EcbuInfilling;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcbuInfillingDao {
    EcbuInfilling getObject(EcbuInfilling record);

    int insert(EcbuInfilling ecbuInfilling);

    int update(EcbuInfilling record);

    List<EcbuInfilling> getList(EcbuInfilling record);

    int delete(EcbuInfilling record);
}
