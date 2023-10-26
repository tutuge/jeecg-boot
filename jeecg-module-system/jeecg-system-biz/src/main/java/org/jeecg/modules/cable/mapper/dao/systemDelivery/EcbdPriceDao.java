package org.jeecg.modules.cable.mapper.dao.systemDelivery;

import org.jeecg.modules.cable.entity.systemDelivery.EcbdPrice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcbdPriceDao {
    List<EcbdPrice> getList(EcbdPrice record);

    long getCount(EcbdPrice record);

    EcbdPrice getObject(EcbdPrice record);

    Integer insert(EcbdPrice record);

    Integer update(EcbdPrice record);
}
