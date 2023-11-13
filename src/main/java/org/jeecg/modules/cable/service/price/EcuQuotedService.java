package org.jeecg.modules.cable.service.price;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.cable.entity.price.EcuQuoted;

import java.math.BigDecimal;
import java.util.List;

public interface EcuQuotedService extends IService<EcuQuoted> {

    List<EcuQuoted> getList(EcuQuoted record);


    Long getCount(EcuQuoted record);

    Long selectCount(EcuQuoted record);


    EcuQuoted getObject(EcuQuoted record);


    EcuQuoted getLatestObject(EcuQuoted record);


    Integer insert(EcuQuoted record);


    Integer deleteByPrimaryKey(Integer ecuqId);

    Integer update(EcuQuoted record);

    /**
     * @param ecuqId        报价单id
     * @param nbuptMoney    不开票总计
     * @param buptMoney     开票总计
     * @param deliveryMoney 快递金额
     * @param totalWeight   总重量
     */
    void dealMoney(Integer ecuqId, BigDecimal nbuptMoney, BigDecimal buptMoney, BigDecimal deliveryMoney, BigDecimal totalWeight);

}
