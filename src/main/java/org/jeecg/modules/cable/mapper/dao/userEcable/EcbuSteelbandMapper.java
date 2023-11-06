package org.jeecg.modules.cable.mapper.dao.userEcable;

import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.userEcable.EcbuSteelband;

import java.util.List;

@Mapper
public interface EcbuSteelbandMapper {
    EcbuSteelband getObject(EcbuSteelband record);

    Integer insert(EcbuSteelband ecbuSteelband);

    Integer update(EcbuSteelband record);

    List<EcbuSteelband> getList(EcbuSteelband record);

    Integer delete(EcbuSteelband record);
}
