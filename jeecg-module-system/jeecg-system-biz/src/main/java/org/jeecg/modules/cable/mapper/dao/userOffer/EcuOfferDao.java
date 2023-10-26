package org.jeecg.modules.cable.mapper.dao.userOffer;

import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.userOffer.EcuOffer;

import java.util.List;

@Mapper
public interface EcuOfferDao {
    List<EcuOffer> getList(EcuOffer record);

    long getCount(EcuOffer record);

    EcuOffer getObject(EcuOffer record);

    Integer insert(EcuOffer record);

    Integer delete(EcuOffer record);

    Integer update(EcuOffer record);
}
