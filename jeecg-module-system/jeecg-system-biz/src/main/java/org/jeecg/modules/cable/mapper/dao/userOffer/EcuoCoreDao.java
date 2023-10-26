package org.jeecg.modules.cable.mapper.dao.userOffer;

import org.jeecg.modules.cable.entity.userOffer.EcuoCore;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcuoCoreDao {
    List<EcuoCore> getList(EcuoCore record);

    EcuoCore getObject(EcuoCore record);

    Integer insert(EcuoCore record);

    Integer update(EcuoCore record);
}
