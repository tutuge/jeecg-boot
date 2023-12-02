package org.jeecg.modules.cable.service.userDelivery;

import org.jeecg.modules.cable.entity.userDelivery.EcbudMoney;

import java.util.List;

public interface EcbudMoneyService {
    List<EcbudMoney> getList(EcbudMoney record);

    long getCount(EcbudMoney record);

    EcbudMoney getObject(EcbudMoney record);

    Integer insert(EcbudMoney record);

    Integer update(EcbudMoney record);

    Integer delete(EcbudMoney record);


    List<EcbudMoney> getListGreaterThanSortId(EcbudMoney record);

    //getObjectPassProvinceName
    EcbudMoney getObjectPassProvinceName(EcbudMoney record);


    EcbudMoney getLatestObject(EcbudMoney record);

    /**
     * @param ecbudId    快递ID
     * @param startType  是否启用
     * @param provinceId 省份ID
     * @return
     */
    EcbudMoney getPricePassEcbudIdAndProvinceIdAndWeight(Integer ecbudId, Boolean startType, Integer provinceId);

    void reduceSort(Integer ecbudId, Integer sortId);
}
