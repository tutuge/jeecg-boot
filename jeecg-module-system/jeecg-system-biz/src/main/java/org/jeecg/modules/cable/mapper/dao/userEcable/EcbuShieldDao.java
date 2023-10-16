package org.jeecg.modules.cable.mapper.dao.userEcable;

import org.jeecg.modules.cable.entity.userEcable.EcbuShield;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcbuShieldDao {
    EcbuShield getObject(EcbuShield record);

    int insert(EcbuShield ecbuShield);

    int update(EcbuShield record);

    List<EcbuShield> getList(EcbuShield record);

    int delete(EcbuShield record);
}
