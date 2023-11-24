package org.jeecg.modules.cable.service.userDelivery;

import org.jeecg.modules.cable.entity.userDelivery.EcbudPrice;

import java.util.List;

public interface EcbudPriceService {
    List<EcbudPrice> getList(EcbudPrice record);

    long getCount(EcbudPrice record);

    EcbudPrice getObject(EcbudPrice record);

    Integer insert(EcbudPrice record);

    Integer update(EcbudPrice record);

    Integer delete(EcbudPrice record);

    
    List<EcbudPrice> getListGreaterThanSortId(EcbudPrice record);

    //getObjectPassProvinceName
    EcbudPrice getObjectPassProvinceName(EcbudPrice record);


    EcbudPrice getLatestObject(EcbudPrice record);

    EcbudPrice getPricePassEcbudIdAndProvinceIdAndWeight(Integer ecbudId, Boolean startType, Integer provinceId);
}
