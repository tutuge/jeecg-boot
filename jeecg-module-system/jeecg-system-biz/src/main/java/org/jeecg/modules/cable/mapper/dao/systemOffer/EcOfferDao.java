package org.jeecg.modules.cable.mapper.dao.systemOffer;

import org.jeecg.modules.cable.entity.systemOffer.EcOffer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcOfferDao {
    List<EcOffer> getList(EcOffer record);
}
