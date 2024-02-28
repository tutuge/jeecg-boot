package org.jeecg.modules.cable.service.price;

import org.jeecg.modules.cable.entity.price.EcuqDesc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface EcuqDescService {
    List<EcuqDesc> getList(EcuqDesc record);

    EcuqDesc getObject(EcuqDesc record);

    Integer insert(EcuqDesc record);

    void deletePassEcuqiId(Integer ecuqiId);

    Integer update(EcuqDesc record);

    /**
     * 根据报价单上的设置给导体Id一致的报价单明细中的导体价格修改了
     *
     * @param ecuqId     报价单Id
     * @param ecbucId    导体Id
     * @param cunitPrice 导体价格
     */
    void updateConductorPriceById(Integer ecuqId, Integer ecbucId, BigDecimal cunitPrice);

    Set<Integer> getMaterialIdList();
}
