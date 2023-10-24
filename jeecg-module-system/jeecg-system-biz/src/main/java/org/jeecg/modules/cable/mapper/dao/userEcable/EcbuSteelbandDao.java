package org.jeecg.modules.cable.mapper.dao.userEcable;

import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.userEcable.EcbuSteelband;

import java.util.List;

@Mapper
public interface EcbuSteelbandDao {
    EcbuSteelband getObject(EcbuSteelband record);

    int insert(EcbuSteelband ecbuSteelband);

    int update(EcbuSteelband record);

    List<EcbuSteelband> getList(EcbuSteelband record);

    int delete(EcbuSteelband record);
}
