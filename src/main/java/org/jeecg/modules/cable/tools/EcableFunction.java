package org.jeecg.modules.cable.tools;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.entity.hand.DeliveryObj;
import org.jeecg.modules.cable.entity.price.EcuQuoted;
import org.jeecg.modules.cable.entity.price.EcuqInput;
import org.jeecg.modules.cable.entity.userCommon.EcduCompany;
import org.jeecg.modules.cable.entity.userDelivery.EcbudDelivery;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * 报价计算
 */
@Slf4j
public class EcableFunction {

    // 获取开发票计算的单价
    public static BigDecimal getBillPercentData(EcuqInput ecuqInput, EcduCompany company, BigDecimal money) {
        BigDecimal billSingleMoney = BigDecimal.ZERO;// 开票单价
        BigDecimal billPercent = ecuqInput.getBillPercent();
        Integer billPercentType = company.getBillPercentType();
        if (billPercentType == 1) {// 算法1
            BigDecimal subtract = BigDecimal.ONE.subtract(billPercent);
            if (subtract.compareTo(BigDecimal.ZERO) == 0) {
                billSingleMoney = BigDecimal.ZERO;
            } else {
                billSingleMoney = money.divide(subtract, 16, RoundingMode.HALF_UP);
            }
        } else if (billPercentType == 2) {// 算法2
            billSingleMoney = money.multiply(BigDecimal.ONE.add(billPercent));// 开票单价
        }
        return billSingleMoney;
    }

    // getDeliveryData 获取快递数据
    public static DeliveryObj getDeliveryData(EcuQuoted ecuQuoted, List<DeliveryObj> listDeliveryPrice, EcbudDelivery dDelivery) {
        DeliveryObj objectDelivery = new DeliveryObj();
        if (!listDeliveryPrice.isEmpty()) {
            if (dDelivery == null) {
                objectDelivery = listDeliveryPrice.get(0);// 默认选最便宜的快递
            } else {
                if (ecuQuoted.getEcbudId() == 0) {
                    DeliveryObj deliveryObj = listDeliveryPrice.get((dDelivery.getSortId() - 1));
                    deliveryObj.setDSelect(true);
                    objectDelivery = deliveryObj;
                } else {
                    for (DeliveryObj deliveryObj : listDeliveryPrice) {
                        if (ecuQuoted.getEcbudId().equals(deliveryObj.getEcbudId())) {
                            objectDelivery = deliveryObj;
                        }
                    }
                }
            }
        }
        return objectDelivery;
    }
}
