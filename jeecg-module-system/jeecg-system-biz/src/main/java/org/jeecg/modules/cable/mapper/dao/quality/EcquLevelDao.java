package org.jeecg.modules.cable.mapper.dao.quality;

import org.jeecg.modules.cable.entity.quality.EcquLevel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcquLevelDao {
    List<EcquLevel> getList(EcquLevel record);

    long getCount(EcquLevel record);

    EcquLevel getObject(EcquLevel record);

    Integer insert(EcquLevel record);

    Integer update(EcquLevel record);

    Integer delete(EcquLevel record);
}
