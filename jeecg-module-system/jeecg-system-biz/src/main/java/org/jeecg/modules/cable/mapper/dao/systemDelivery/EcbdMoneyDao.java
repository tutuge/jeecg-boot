package org.jeecg.modules.cable.mapper.dao.systemDelivery;

import org.jeecg.modules.cable.entity.systemDelivery.EcbdMoney;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcbdMoneyDao {
    List<EcbdMoney> getList(EcbdMoney record);

    long getCount(EcbdMoney record);

    EcbdMoney getObject(EcbdMoney record);

    int insert(EcbdMoney record);

    int update(EcbdMoney record);
}
