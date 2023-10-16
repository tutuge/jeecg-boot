package org.jeecg.modules.cable.service.userDelivery;

import org.jeecg.modules.cable.entity.userDelivery.EcbudMoney;

import java.util.List;

public interface EcbudMoneyService {
    List<EcbudMoney> getList(EcbudMoney record);

    long getCount(EcbudMoney record);

    EcbudMoney getObject(EcbudMoney record);

    int insert(EcbudMoney record);

    int update(EcbudMoney record);

    int delete(EcbudMoney record);

    //getListGreaterThanSortId 获取大于指定序号的数据列表
    List<EcbudMoney> getListGreaterThanSortId(EcbudMoney record);

    //getObjectPassProvinceName
    EcbudMoney getObjectPassProvinceName(EcbudMoney record);

    //getLatestObject
    EcbudMoney getLatestObject(EcbudMoney record);
}
