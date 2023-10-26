package org.jeecg.modules.cable.mapper.dao.userEcable;

import org.jeecg.modules.cable.entity.userEcable.EcbuMicatape;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcbuMicatapeDao {
    EcbuMicatape getObject(EcbuMicatape record);

    Integer insert(EcbuMicatape ecbuMicatape);

    Integer update(EcbuMicatape record);

    List<EcbuMicatape> getList(EcbuMicatape record);

    Integer delete(EcbuMicatape record);
}
