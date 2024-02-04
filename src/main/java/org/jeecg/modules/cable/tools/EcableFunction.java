package org.jeecg.modules.cable.tools;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.domain.ConductorComputeBo;
import org.jeecg.modules.cable.entity.hand.DeliveryObj;
import org.jeecg.modules.cable.entity.price.EcuQuoted;
import org.jeecg.modules.cable.entity.price.EcuqInput;
import org.jeecg.modules.cable.entity.systemOffer.EcOffer;
import org.jeecg.modules.cable.entity.userCommon.EcduCompany;
import org.jeecg.modules.cable.entity.userDelivery.EcbudDelivery;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报价计算
 */
@Slf4j
public class EcableFunction {
    ///**
    // * 计算导体的外径等信息
    // *
    // * @param ecOffer
    // * @return
    // */
    //public static ConductorComputeBo getConductorData(EcOffer ecOffer) {
    //    // 使用+号将两个值切分
    //    String[] areaArr = (ecOffer.getAreaStr()).split("\\+");
    //    String[] fireArr = areaArr[0].split("\\*");
    //    BigDecimal fireSilkNumber = ecOffer.getFireSilkNumber();// 粗芯丝号
    //    BigDecimal zeroSilkNumber = ecOffer.getZeroSilkNumber();// 细芯丝号
    //    BigDecimal fireRadius = BigDecimal.ZERO;// 火线直径
    //    BigDecimal zeroRadius = BigDecimal.ZERO;// 零线直径
    //    BigDecimal fireDiameter = BigDecimal.ZERO;// 粗芯外径
    //    BigDecimal zeroDiameter = BigDecimal.ZERO;// 细芯外径
    //    BigDecimal externalDiameter;// 导体外径
    //    if (fireArr.length == 2) {// 有一个*
    //        // 单根火线半径
    //        fireRadius = fireSilkNumber.divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP)
    //                .add(new BigDecimal(ecOffer.getFireMembrance()));
    //        // 单段火线直径
    //        fireDiameter = fireSilkNumber.multiply(getSilkPercent(ecOffer.getFireRootNumber()));
    //    }
    //    // 零线
    //    if (areaArr.length == 2) {
    //        // 单根零线半径
    //        zeroRadius = zeroSilkNumber.divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP)
    //                .add(new BigDecimal(ecOffer.getZeroMembrance()));
    //        // 单段零线直径
    //        zeroDiameter = (ecOffer.getZeroSilkNumber()).multiply(getSilkPercent(ecOffer.getZeroRootNumber()));
    //    }
    //    // 计算导体外径
    //    externalDiameter = getExternalDiameter(areaArr, fireDiameter, zeroDiameter);
    //    // 更新导体重量
    //    return new ConductorComputeBo(fireRadius, zeroRadius, fireDiameter, zeroDiameter, externalDiameter);
    //}

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

    /**
     * 对应丝号倍数
     *
     * @param rootNumber
     * @return
     */
    public static BigDecimal getSilkPercent(Integer rootNumber) {
        BigDecimal silkPercent;
        silkPercent = switch (rootNumber) {
            case 1 -> BigDecimal.ONE;
            case 2 -> new BigDecimal("2");
            case 3 -> new BigDecimal("2.16");
            case 4 -> new BigDecimal("2.42");
            case 5 -> new BigDecimal("2.70");
            case 6 -> new BigDecimal("3");
            case 7 -> new BigDecimal("3");
            case 8 -> new BigDecimal("3.45");
            case 9 -> new BigDecimal("3.8");
            case 10 -> new BigDecimal("4.0");
            case 11 -> new BigDecimal("4.0");
            case 12 -> new BigDecimal("4.16");
            case 13 -> new BigDecimal("4.41");
            case 14 -> new BigDecimal("4.41");
            case 15 -> new BigDecimal("4.7");
            case 16 -> new BigDecimal("4.7");
            case 17 -> new BigDecimal("5.0");
            case 18 -> new BigDecimal("5.0");
            case 19 -> new BigDecimal("5.0");
            case 20 -> new BigDecimal("5.33");
            case 21 -> new BigDecimal("5.33");
            case 22 -> new BigDecimal("5.67");
            case 23 -> new BigDecimal("5.67");
            case 24 -> new BigDecimal("6.00");
            case 25 -> new BigDecimal("6.00");
            case 26 -> new BigDecimal("6.00");
            case 27 -> new BigDecimal("6.15");
            case 28 -> new BigDecimal("6.41");
            case 29 -> new BigDecimal("6.41");
            case 30 -> new BigDecimal("6.41");
            case 31 -> new BigDecimal("6.70");
            case 32 -> new BigDecimal("6.70");
            case 33 -> new BigDecimal("6.70");
            case 34 -> new BigDecimal("7.00");
            case 35 -> new BigDecimal("7.00");
            case 36 -> new BigDecimal("7.00");
            case 37 -> new BigDecimal("7.00");
            case 38 -> new BigDecimal("7.33");
            case 39 -> new BigDecimal("7.33");
            case 40 -> new BigDecimal("7.33");
            case 41 -> new BigDecimal("7.67");
            case 42 -> new BigDecimal("7.67");
            case 43 -> new BigDecimal("7.67");
            case 44 -> new BigDecimal("8.00");
            case 45 -> new BigDecimal("8.00");
            case 46 -> new BigDecimal("8.00");
            case 47 -> new BigDecimal("8.00");
            case 48 -> new BigDecimal("8.15");
            case 52 -> new BigDecimal("8.41");
            case 61 -> new BigDecimal("9.00");
            default -> BigDecimal.ZERO;
        };
        return silkPercent;
    }

    /**
     * 外径的平均数
     *
     * @param fireSegment  粗芯段数
     * @param fireDiameter
     * @param zeroSegment  细芯段数
     * @param zeroDiameter
     * @return
     */
    public static BigDecimal getAverageDiameter(Integer fireSegment, BigDecimal fireDiameter,
                                                Integer zeroSegment, BigDecimal zeroDiameter) {
        BigDecimal averageDiameter = (new BigDecimal(fireSegment).multiply(fireDiameter)
                .add(new BigDecimal(zeroSegment).multiply(zeroDiameter)))
                .divide(new BigDecimal(fireSegment).add(new BigDecimal(zeroSegment)), 16, RoundingMode.HALF_UP);
        return averageDiameter;
    }

    //  获取多导体电缆的导体的计算外径
    public static BigDecimal getExternalDiameter(String[] areaArr, BigDecimal fireDiameter, BigDecimal zeroDiameter) {
        BigDecimal externalDiameter = BigDecimal.ZERO;// 外径
        BigDecimal averageDiameter;
        Integer fireNumber = Integer.parseInt(areaArr[0].split("\\*")[0]);// 粗芯段数
        if (areaArr.length == 1) {// 零线数为0时视为等圆
            externalDiameter = getSilkPercent(fireNumber).multiply(fireDiameter);
        } else {// 既有火线又有零线
            int zeroNumber = Integer.parseInt(areaArr[1].split("\\*")[0]);// 细芯段数
            if (fireNumber == 2 && zeroNumber == 1) {
                if (zeroDiameter.compareTo(fireDiameter.multiply(new BigDecimal("2"))
                        .divide(new BigDecimal("3"), 16, RoundingMode.HALF_UP)) < 1) {
                    externalDiameter = fireDiameter.multiply(new BigDecimal("2"));
                } else {
                    externalDiameter = new BigDecimal("2.16")
                            .multiply(getAverageDiameter(fireNumber, fireDiameter, zeroNumber, zeroDiameter));
                }
            } else if (fireNumber == 3 && zeroNumber == 1) {// 有一根小截面的四芯电缆
                externalDiameter = new BigDecimal("2.42")
                        .multiply(getAverageDiameter(fireNumber, fireDiameter, zeroNumber, zeroDiameter));
            } else if (fireNumber == 4 && zeroNumber == 1) {// 有一根小截面的五芯电缆
                externalDiameter = new BigDecimal("2.70")
                        .multiply(getAverageDiameter(fireNumber, fireDiameter, zeroNumber, zeroDiameter));
            } else if (fireNumber == 3 && zeroNumber == 2) {// 有两根小截面的五芯电缆 计算地线
                averageDiameter = getAverageDiameter(fireNumber, fireDiameter, zeroNumber, zeroDiameter);
                externalDiameter = new BigDecimal("2.70").multiply(averageDiameter);
            }
        }
        return externalDiameter;
    }
}
