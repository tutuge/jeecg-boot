package org.jeecg.modules.cable.mapper.dao.userOffer;

import org.jeecg.modules.cable.entity.userOffer.EcuOffer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcuOfferDao {
    List<EcuOffer> getList(EcuOffer record);

    long getCount(EcuOffer record);

    EcuOffer getObject(EcuOffer record);

    int insert(EcuOffer record);

    int delete(EcuOffer record);

    int update(EcuOffer record);
}
