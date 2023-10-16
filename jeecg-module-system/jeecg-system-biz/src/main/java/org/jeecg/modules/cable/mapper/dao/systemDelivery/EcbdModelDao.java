package org.jeecg.modules.cable.mapper.dao.systemDelivery;

import org.jeecg.modules.cable.entity.systemDelivery.EcbdModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EcbdModelDao {
    EcbdModel getObject(EcbdModel record);

    int insert(EcbdModel record);
    int update(EcbdModel record);
}
