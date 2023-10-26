package org.jeecg.modules.cable.mapper.dao.userEcable;

import org.jeecg.modules.cable.entity.userEcable.EcbuBag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcbuBagDao {
    EcbuBag getObject(EcbuBag record);

    Integer insert(EcbuBag ecbuBag);

    Integer update(EcbuBag record);

    List<EcbuBag> getList(EcbuBag record);

    Integer delete(EcbuBag record);
}
