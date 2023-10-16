package org.jeecg.modules.cable.mapper.dao.userDelivery;

import org.jeecg.modules.cable.entity.userDelivery.EcbudModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EcbudModelDao {
    int insert(EcbudModel record);

    int update(EcbudModel record);

    EcbudModel getObject(EcbudModel record);

    int delete(EcbudModel record);

}
