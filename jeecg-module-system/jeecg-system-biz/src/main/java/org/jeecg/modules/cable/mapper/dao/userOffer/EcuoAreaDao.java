package org.jeecg.modules.cable.mapper.dao.userOffer;

import org.jeecg.modules.cable.entity.userOffer.EcuoArea;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcuoAreaDao {
    List<EcuoArea> getList(EcuoArea record);

    EcuoArea getObject(EcuoArea record);

    Integer insert(EcuoArea record);

    Integer update(EcuoArea record);
}
