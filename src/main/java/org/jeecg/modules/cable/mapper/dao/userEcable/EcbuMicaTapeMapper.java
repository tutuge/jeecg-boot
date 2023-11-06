package org.jeecg.modules.cable.mapper.dao.userEcable;

import org.jeecg.modules.cable.entity.userEcable.EcbuMicaTape;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcbuMicaTapeMapper {
    EcbuMicaTape getObject(EcbuMicaTape record);

    Integer insert(EcbuMicaTape ecbuMicatape);

    Integer update(EcbuMicaTape record);

    List<EcbuMicaTape> getList(EcbuMicaTape record);

    Integer delete(EcbuMicaTape record);
}
