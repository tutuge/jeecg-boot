package org.jeecg.modules.cable.mapper.dao.userEcable;

import org.jeecg.modules.cable.entity.userEcable.EcbuShield;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcbuShieldMapper {
    EcbuShield getObject(EcbuShield record);

    Integer insert(EcbuShield ecbuShield);

    Integer update(EcbuShield record);

    List<EcbuShield> getList(EcbuShield record);

    Integer delete(EcbuShield record);
}
