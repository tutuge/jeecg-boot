package org.jeecg.modules.cable.mapper.dao.userDelivery;

import org.jeecg.modules.cable.entity.userDelivery.EcbudModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EcbudModelDao {
    Integer insert(EcbudModel record);

    Integer update(EcbudModel record);

    EcbudModel getObject(EcbudModel record);

    Integer delete(EcbudModel record);

}
