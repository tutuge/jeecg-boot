package org.jeecg.modules.cable.mapper.dao.userEcable;

import org.jeecg.modules.cable.entity.userEcable.EcbuInfilling;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcbuInfillingDao {
    EcbuInfilling getObject(EcbuInfilling record);

    Integer insert(EcbuInfilling ecbuInfilling);

    Integer update(EcbuInfilling record);

    List<EcbuInfilling> getList(EcbuInfilling record);

    Integer delete(EcbuInfilling record);
}
