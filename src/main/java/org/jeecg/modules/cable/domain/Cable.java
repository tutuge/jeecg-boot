package org.jeecg.modules.cable.domain;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.jeecg.modules.cable.tools.EcableFunction.getAverageDiameter;
import static org.jeecg.modules.cable.tools.EcableFunction.getSilkPercent;

@Schema(description = "电缆对象，数据与计算")
public class Cable {
    /**
     * 电缆规格
     */
    private String areaStr;
    /**
     * 粗芯导体数量
     */
    private Integer fireNumber = 0;

    /**
     * 细芯导体数量
     */
    private Integer zeroNumber = 0;

    /**
     * 单位长度材料的重量
     */
    @Getter
    private BigDecimal unitWeight = BigDecimal.ZERO;

    /**
     * 单位长度材料的金额
     */
    @Getter
    private BigDecimal unitMoney = BigDecimal.ZERO;

    /**
     * 电缆参数长度
     */
    private BigDecimal length = BigDecimal.ONE;

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public Cable(String areaStr) {
        this.setAreaStr(areaStr);
    }

    //计算粗芯细芯导体数量
    private void setAreaStr(String areaStr) {
        this.areaStr = areaStr;
        if (StrUtil.isNotBlank(areaStr)) {
            String[] areaArr = areaStr.split("\\+");
            String[] fireArr = areaArr[0].split("\\*");
            this.fireNumber = Integer.valueOf(fireArr[0]);
            if (areaArr.length == 2) {
                String[] split = areaArr[1].split("\\*");
                if (split.length == 2) {
                    this.zeroNumber = Integer.valueOf(split[0]);
                }
            }
        } else {
            throw new RuntimeException("规格不得为空");
        }
    }

    //--------------------导体相关-------------

    /**
     * 导体
     */
    private ConductorMaterial conductorMaterial;

    /**
     * 设置导体数据
     *
     * @param conductorDensity   导体密度
     * @param conductorUnitPrice 导体单价
     * @param fireRootNumber     粗芯根数
     * @param zeroRootNumber     细芯根数
     * @param fireSilkNumber     粗芯丝号 也就是火线直径
     * @param zeroSilkNumber     细芯丝号 也就是零线直径
     * @param fireStrand         粗芯绞合系数
     * @param zeroStrand         细芯绞合系数
     */
    public void setConductorMaterial(BigDecimal conductorDensity, BigDecimal conductorUnitPrice,
                                     Integer fireRootNumber, Integer zeroRootNumber,
                                     BigDecimal fireSilkNumber, BigDecimal zeroSilkNumber,
                                     BigDecimal fireStrand, BigDecimal zeroStrand,
                                     BigDecimal conductorReduction) {

        ConductorMaterial conductorMaterial = new ConductorMaterial();
        conductorMaterial.setConductorDensity(conductorDensity);
        conductorMaterial.setConductorUnitPrice(conductorUnitPrice);
        conductorMaterial.setFireRootNumber(fireRootNumber);
        conductorMaterial.setZeroRootNumber(zeroRootNumber);
        conductorMaterial.setFireSilkNumber(fireSilkNumber);
        conductorMaterial.setZeroSilkNumber(zeroSilkNumber);
        conductorMaterial.setFireStrand(fireStrand);
        conductorMaterial.setZeroStrand(zeroStrand);
        conductorMaterial.setConductorReduction(conductorReduction);

        BigDecimal zeroRadius = BigDecimal.ZERO;//零线半径
        BigDecimal zeroWeight = BigDecimal.ZERO;//细芯重量
        BigDecimal zeroMoney = BigDecimal.ZERO;//细芯金额
        BigDecimal zeroDiameter = BigDecimal.ZERO;//细芯外径

        //火线半径
        BigDecimal fireRadius = fireSilkNumber.divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP);
        //.add(new BigDecimal(fireMembrance));
        //火线面积
        BigDecimal fireArea = fireRadius.multiply(fireRadius).multiply(BigDecimal.valueOf(Math.PI));
        //折扣之后再次计算出的火线导体半径
        fireRadius = reduce(fireArea, conductorReduction);
        //粗芯重量
        BigDecimal fireWeight = fireRadius.multiply(fireRadius)
                .multiply(BigDecimal.valueOf(Math.PI))
                .multiply(new BigDecimal(fireRootNumber))
                .multiply(fireStrand)//绞合系数
                .multiply(new BigDecimal(fireNumber))//有几根火线
                .divide(BigDecimal.valueOf(1000D), 16, RoundingMode.HALF_UP) //转换为平方厘米
                .multiply(conductorDensity).multiply(length);
        //粗芯金额
        BigDecimal fireMoney = fireWeight.multiply(conductorUnitPrice);
        //单段火线外径 = 半径*2
        BigDecimal fireDiameter = fireRadius.multiply(BigDecimal.valueOf(2D)).multiply(getSilkPercent(fireRootNumber));

        //零线
        if (zeroNumber > 0) {
            //单根零线数据
            zeroRadius = zeroSilkNumber.divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP);
            //.add(new BigDecimal(zeroMembrance));
            //截面面积
            BigDecimal zeroArea = zeroRadius.multiply(zeroRadius).multiply(BigDecimal.valueOf(Math.PI));
            //折扣之后再次计算出的零线半径
            zeroRadius = reduce(zeroArea, conductorReduction);
            zeroWeight = zeroRadius.multiply(zeroRadius)
                    .multiply(BigDecimal.valueOf(Math.PI))
                    .multiply(new BigDecimal(zeroRootNumber))
                    .multiply(zeroStrand) //绞合系数
                    .multiply(new BigDecimal(zeroNumber))//细芯数量
                    .divide(BigDecimal.valueOf(1000D), 16, RoundingMode.HALF_UP) //转换为平方厘米
                    .multiply(conductorDensity).multiply(length);
            zeroMoney = zeroWeight.multiply(conductorUnitPrice);
            //单段零线外径
            zeroDiameter = zeroRadius.multiply(BigDecimal.valueOf(2D)).multiply(getSilkPercent(zeroRootNumber));
        }
        //计算导体加权后的外径
        BigDecimal externalDiameter = getExternalDiameter(fireDiameter, zeroDiameter);
        BigDecimal conductorWeight = fireWeight.add(zeroWeight);
        BigDecimal conductorMoney = fireMoney.add(zeroMoney);
        //直径半径
        conductorMaterial.setFireRadius(fireRadius);
        conductorMaterial.setZeroRadius(zeroRadius);
        conductorMaterial.setFireDiameter(fireDiameter);
        conductorMaterial.setZeroDiameter(zeroDiameter);
        conductorMaterial.setExternalDiameter(externalDiameter);
        //火线零线内部直径
        this.fireInternalDiameter = fireDiameter;
        this.zeroInternalDiameter = zeroDiameter;
        //重量金额
        conductorMaterial.setFireWeight(fireWeight);
        conductorMaterial.setZeroWeight(zeroWeight);
        conductorMaterial.setFireMoney(fireMoney);
        conductorMaterial.setZeroMoney(zeroMoney);
        conductorMaterial.setConductorWeight(conductorWeight);
        conductorMaterial.setConductorMoney(conductorMoney);

        this.unitWeight = this.unitWeight.add(conductorWeight);
        this.unitMoney = this.unitMoney.add(conductorMoney);
        this.conductorMaterial = conductorMaterial;
    }


    /**
     * 截面积打折
     *
     * @param area
     * @param reduction
     * @return
     */
    public static BigDecimal reduce(BigDecimal area, BigDecimal reduction) {
        //给截面积打折
        BigDecimal divide = area.multiply(reduction).divide(BigDecimal.valueOf(Math.PI), 16, RoundingMode.HALF_UP);
        //半径
        return sqrt(divide, 16);
    }


    public static BigDecimal sqrt(BigDecimal value, int scale) {
        BigDecimal num2 = BigDecimal.valueOf(2);
        int precision = 100;
        MathContext mc = new MathContext(precision, RoundingMode.HALF_UP);
        BigDecimal deviation = value;
        int cnt = 0;
        while (cnt < precision) {
            deviation = (deviation.add(value.divide(deviation, mc))).divide(num2, mc);
            cnt++;
        }
        deviation = deviation.setScale(scale, RoundingMode.HALF_UP);
        return deviation;
    }

    //  获取多导体电缆的导体的计算外径
    public BigDecimal getExternalDiameter(BigDecimal fireDiameter, BigDecimal zeroDiameter) {
        BigDecimal externalDiameter = BigDecimal.ZERO;// 外径
        BigDecimal averageDiameter;
        //Integer fireNumber = Integer.parseInt(areaArr[0].split("\\*")[0]);// 粗芯段数
        if (zeroNumber == 0) {// 零线数为0时视为等圆
            externalDiameter = getSilkPercent(fireNumber).multiply(fireDiameter);
        } else {// 既有火线又有零线
            //int zeroNumber = Integer.parseInt(areaArr[1].split("\\*")[0]);// 细芯段数
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

    //获取导体数据
    public ConductorMaterial getConductorMaterial() {
        return conductorMaterial;
    }

    //--------------------导体到填充物------------------------

    //火线零线内部直径
    private BigDecimal fireInternalDiameter;
    private BigDecimal zeroInternalDiameter;


    /**
     * 内部材料
     */
    private final List<InternalMaterial> internalMaterials = new ArrayList<>();

    public List<InternalMaterial> getInternalMaterial() {
        return internalMaterials;
    }

    public void addInternalMaterial(BigDecimal density, BigDecimal unitPrice, BigDecimal factor,
                                    BigDecimal fireThickness, BigDecimal zeroThickness) {
        InternalMaterial internalMaterial = new InternalMaterial();
        internalMaterial.setDensity(density);
        internalMaterial.setUnitPrice(unitPrice);
        internalMaterial.setFactor(factor);
        internalMaterial.setFireThickness(fireThickness);
        internalMaterial.setZeroThickness(zeroThickness);

        BigDecimal zeroInternalRadius = BigDecimal.ZERO;//细芯材料半径
        BigDecimal zeroInternalWeight = BigDecimal.ZERO;//细芯材料重量
        BigDecimal zeroInternalMoney = BigDecimal.ZERO;//细芯材料金额
        //火线材料半径 = 火线半径 + 火线材料厚度
        BigDecimal fireRadius = fireInternalDiameter.divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP);
        BigDecimal fireInternalRadius = fireRadius.add(fireThickness.multiply(factor));
        //粗芯当前材料重量(KG)
        BigDecimal fireInternalWeight = fireInternalRadius.multiply(fireInternalRadius)
                .subtract(fireRadius.multiply(fireRadius))
                .multiply(BigDecimal.valueOf(Math.PI))
                .multiply(density)
                .divide(BigDecimal.valueOf(1000D), 16, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(fireNumber)).multiply(length);
        BigDecimal fireInternalMoney = fireInternalWeight.multiply(unitPrice);//粗芯材料金额
        //零线材料
        if (zeroNumber > 0) {
            BigDecimal zeroRadius = zeroInternalDiameter.divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP);
            zeroInternalRadius = zeroRadius.add(zeroThickness.multiply(factor));
            //重量(KG)
            zeroInternalWeight = zeroInternalRadius.multiply(zeroInternalRadius)
                    .subtract(zeroRadius.multiply(zeroRadius))
                    .multiply(BigDecimal.valueOf(Math.PI))
                    .multiply(density)
                    .divide(BigDecimal.valueOf(1000D), 16, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal(zeroNumber)).multiply(length);
            zeroInternalMoney = zeroInternalWeight.multiply(unitPrice);
        }
        //材料重量
        BigDecimal internalWeight = fireInternalWeight.add(zeroInternalWeight);
        //材料金额
        BigDecimal internalMoney = fireInternalMoney.add(zeroInternalMoney);

        this.unitWeight = this.unitWeight.add(internalWeight);
        this.unitMoney = this.unitMoney.add(internalMoney);

        internalMaterial.setFireRadius(fireInternalRadius);
        internalMaterial.setFireWeight(fireInternalWeight);
        internalMaterial.setFireMoney(fireInternalMoney);
        internalMaterial.setZeroRadius(zeroInternalRadius);
        internalMaterial.setZeroWeight(zeroInternalWeight);
        internalMaterial.setZeroMoney(zeroInternalMoney);
        internalMaterial.setMaterialWeight(internalWeight);
        internalMaterial.setMaterialMoney(internalMoney);
        //火线零线直径增加
        this.fireInternalDiameter = fireInternalRadius.multiply(BigDecimal.valueOf(2));
        this.zeroInternalDiameter = zeroInternalRadius.multiply(BigDecimal.valueOf(2));
        //按顺序加入链表
        internalMaterials.add(internalMaterial);
    }


    //-------------填充物-----------------

    private InfillingMaterial infillingMaterial;

    public InfillingMaterial getInfillingMaterial() {
        return infillingMaterial;
    }

    public void setInfillingMaterial(BigDecimal density, BigDecimal unitPrice) {
        //导体->云母带->绝缘->填充物->包袋->屏蔽->钢带->外护套
        //重新计算后的总外径
        BigDecimal conductorDiameter = getExternalDiameter(fireInternalDiameter, zeroInternalDiameter);//导体外径
        //导体总的加权后的半径
        BigDecimal conductorRadius = conductorDiameter.divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP);
        //导体加权后总的面积
        BigDecimal totalInfillingVolume = conductorRadius.multiply(conductorRadius).multiply(BigDecimal.valueOf(Math.PI));
        //火线半径
        BigDecimal fireInfillingRadius = fireInternalDiameter.divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP);
        //火线对应面积
        BigDecimal fireInfillingVolume = fireInfillingRadius
                .multiply(fireInfillingRadius)
                .multiply(BigDecimal.valueOf(Math.PI))
                .multiply(BigDecimal.valueOf(fireNumber)); //包含多少根火线
        //零线对应的面积
        BigDecimal zeroInfillingVolume = BigDecimal.ZERO;
        if (zeroNumber > 0) {
            BigDecimal zeroInfillingRadius = zeroInternalDiameter.divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP);
            zeroInfillingVolume = zeroInfillingRadius
                    .multiply(zeroInfillingRadius)
                    .multiply(BigDecimal.valueOf(Math.PI))
                    .multiply(BigDecimal.valueOf(zeroNumber));//包含多少根零线
        }
        // 填充物面积 = 导体加权总面积 - 火线总面积- 零线总面totalInfillingVolume积
        BigDecimal remainInfillingVolume = totalInfillingVolume.subtract(fireInfillingVolume).subtract(zeroInfillingVolume);
        BigDecimal infillingWeight = remainInfillingVolume.multiply(density)
                .divide(BigDecimal.valueOf(1000D), 16, RoundingMode.HALF_UP).multiply(length); //填充物重量(kg)
        BigDecimal infillingMoney = infillingWeight.multiply(unitPrice); //填充物金额
        InfillingMaterial infillingMaterial = new InfillingMaterial();
        infillingMaterial.setExternalDiameter(conductorDiameter);
        infillingMaterial.setWideDiameter(fireInternalDiameter);
        infillingMaterial.setFineDiameter(zeroInternalDiameter);
        infillingMaterial.setInfillingWeight(infillingWeight);
        infillingMaterial.setInfillingMoney(infillingMoney);

        this.unitWeight = this.unitWeight.add(infillingWeight);
        this.unitMoney = this.unitMoney.add(infillingMoney);

        this.externalDiameter = conductorDiameter;
        this.infillingMaterial = infillingMaterial;
    }


    //----------------填充物之外材料----------------


    //外部直径
    private BigDecimal externalDiameter;

    /**
     * 外部材料
     */
    private final List<ExternalMaterial> externalMaterials = new ArrayList<>();

    public List<ExternalMaterial> getExternalMaterials() {
        return externalMaterials;
    }


    public void addExternalMaterials(BigDecimal density, BigDecimal unitPrice, BigDecimal factor, BigDecimal thickness) {
        BigDecimal radius = externalDiameter.divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP);
        BigDecimal externalRadius = radius.add(thickness.multiply(factor)); // 内部材料半径
        BigDecimal externalWeight = ((externalRadius.multiply(externalRadius))
                .subtract(radius.multiply(radius)))
                .multiply(BigDecimal.valueOf(Math.PI))
                .multiply(density)
                .divide(BigDecimal.valueOf(1000D), 16, RoundingMode.HALF_UP).multiply(length); //外部材料重量(kg);
        BigDecimal externalMoney = externalWeight.multiply(unitPrice);// 外部材料金额

        this.externalDiameter = externalRadius.multiply(BigDecimal.valueOf(2));
        ExternalMaterial externalMaterial = new ExternalMaterial();
        externalMaterial.setExternalRadius(externalRadius);
        externalMaterial.setMaterialWeight(externalWeight);
        externalMaterial.setMaterialMoney(externalMoney);

        this.unitWeight = this.unitWeight.add(externalWeight);
        this.unitMoney = this.unitMoney.add(externalMoney);

        externalMaterials.add(externalMaterial);
    }

}
