package org.jeecg.modules.cable.mapper.dao.systemOffer;

import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.systemOffer.EcOffer;

import java.util.List;

@Mapper
public interface EcOfferDao {
    List<EcOffer> getList(EcOffer record);
}
