package org.jeecg.modules.cable.mapper.dao.userDelivery;

import org.jeecg.modules.cable.entity.userDelivery.EcbudMoney;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcbudMoneyMapper {
    List<EcbudMoney> getList(EcbudMoney record);

    long getCount(EcbudMoney record);

    EcbudMoney getObject(EcbudMoney record);

    Integer insert(EcbudMoney record);

    Integer update(EcbudMoney record);

    Integer delete(EcbudMoney record);

    //getListGreaterThanSortId 获取大于指定序号的数据列表
    List<EcbudMoney> getListGreaterThanSortId(EcbudMoney record);

    //getObjectPassProvinceName
    EcbudMoney getObjectPassProvinceName(EcbudMoney record);


    EcbudMoney getLatestObject(EcbudMoney record);
}
