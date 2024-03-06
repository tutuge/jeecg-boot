package org.jeecg.modules.cable.domain;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "电缆对象，数据与计算")
public class Cable {
    /**
     * 电缆规格
     */
    private String areaStr;
    /**
     * 是否是特殊电缆
     */
    private Boolean special = false;
    //----------普通材料------------
    /**
     * 粗芯导体根数
     */
    private Integer fireNumber = 0;

    /**
     * 细芯导体数量
     */
    private Integer zeroNumber = 0;
    //----------特殊材料------------

    /**
     * 大段数
     */
    private Integer cableNumber;
    /**
     * 分屏段数
     */
    private Integer segmentsNumber;

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
    // 判断是普通电缆还是特殊电缆
    //普通电缆粗芯细芯只有一个*号
    //特殊的是两个星号，更多星号的不予考虑
    private void setAreaStr(String areaStr) {
        this.areaStr = areaStr;
        if (StrUtil.isNotBlank(areaStr)) {
            //判断是否有粗芯细芯
            String[] areaArr = areaStr.split("\\+");
            //判断是否是特殊电缆，特殊电缆的话，有多个星号
            String[] fireArr = areaArr[0].split("\\*");
            if (fireArr.length == 2) {
                special = false;
                //粗芯根数
                this.fireNumber = Integer.valueOf(fireArr[0]);
            } else if (fireArr.length == 3) {
                special = true;
                this.cableNumber = Integer.valueOf(fireArr[0]);
                this.segmentsNumber = Integer.valueOf(fireArr[1]);
            } else {
                throw new RuntimeException("当前电缆规格无法解析");
            }

            //有一个加号，会切成粗芯细芯两个字符串
            if (areaArr.length == 2) {
                String[] split = areaArr[1].split("\\*");
                if (split.length == 2) {
                    //细芯根数
                    this.zeroNumber = Integer.valueOf(split[0]);
                }
            }
        } else {
            throw new RuntimeException("规格不得为空");
        }
    }

    public static void main(String[] args) {
        //Double d = 3.1415926D / 4D * (110.31595161029D * 110.31595161029D) * 5 * 2 * 1 / 10000000D * 1.03D * 0.995D * 8.89D;
        //System.out.println(d);
        //110.31595161029D  导体丝号
        // 云母带 重量  0.00272979
        double y = 3.1415926D / 4D * ((1.54315952 * 1.54315952) - (1.1031595161029 * 1.1031595161029))
                * 2 / 1000 * 1.5 * 0.995;
        System.out.println("云母带重量：" + y);
        // 绝缘 0.07215202
        //注意绝缘乘以了5
        double x = 3.1415926D / 4D * ((2.94315952 * 2.94315952) - (1.54315952 * 1.54315952))
                * 2 * 5 / 1000 * 1.47 * 0.995;
        System.out.println("绝缘：" + x);

        //分支铝箔 0.009545039
        double z = 3.1415926D / 4D * ((5.69313989 * 5.69313989) - (5.53313989 * 5.53313989))
                * 5 / 1000 * 1.36 * 0.995;
        System.out.println("分支铝箔：" + z);
        //分支屏蔽 0.090339032
        double a = 3.1415926D / 4D * ((5.91713989 * 5.91713989) - (5.69313989 * 5.69313989))
                * 5 / 1000 * 8.89 * 0.995;
        System.out.println("分支屏蔽：" + a);

        // 纤维绳 0.103935025
        double b = 3.1415926D / 4D *
                (((13.1005477 * 13.1005477) - (5 * 2*2.94315952*2.94315952))-
                        (5.69313989*5.69313989-5.53313989*5.53313989)
                -(5.91713989*5.91713989-5.69313989*5.69313989))
                 / 1000 * 1.65 * 0.995;
        System.out.println("纤维绳：" + b);
        //无纺布 0.004598772
        double c = 3.1415926D / 4D * ((14.0413477 * 14.0413477) - (13.3245477 * 13.3245477)) / 1000 * 0.3 * 0.995;
        System.out.println("无纺布：" + c);
        //內护套 0.05455739
        double d = 3.1415926D / 4D * ((15.6413477 * 15.6413477) - (14.0413477 * 14.0413477) ) / 1000 * 1.47 * 0.995;
        System.out.println("內护套：" + d);
        //外铝箔 0.005346771
        double e= 3.1415926D / 4D * (  (15.8013477 * 15.8013477) - (15.6413477 * 15.6413477) ) / 1000 * 1.36 * 0.995;
        System.out.println("外铝箔：" + e);
        //钢带 0.081430266
        double f= 3.1415926D / 4D * (  (16.2175077 * 16.2175077) - (15.8013477 * 15.8013477)   ) / 1000 * 7.82 * 0.995;
        System.out.println("钢带：" + f);
        //护套 0.113334673
        double j= 3.1415926D / 4D * (  (19.0175077 *19.0175077 ) - (16.2175077 * 16.2175077)   ) / 1000 * 1.47 * 0.995;
        System.out.println("护套：" + j);

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
        BigDecimal fireRadius = BigDecimal.ZERO;  //火线半径
        BigDecimal fireWeight = BigDecimal.ZERO;      //粗芯重量
        BigDecimal fireMoney = BigDecimal.ZERO;   //粗芯金额
        BigDecimal fireDiameter = BigDecimal.ZERO;   //单段火线外径 = 半径*2
        if (special) {
            fireRadius = fireSilkNumber.divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP);
            //火线面积
            BigDecimal fireArea = fireRadius.multiply(fireRadius).multiply(BigDecimal.valueOf(Math.PI));
            //折扣之后再次计算出的火线导体半径
            fireRadius = reduce(fireArea, conductorReduction);
            //粗芯重量
            fireWeight = fireRadius.multiply(fireRadius)
                    .multiply(BigDecimal.valueOf(Math.PI))
                    .multiply(new BigDecimal(fireRootNumber))
                    .multiply(fireStrand)//绞合系数
                    .multiply(new BigDecimal(cableNumber))//特殊电缆有几根大段数
                    .multiply(new BigDecimal(segmentsNumber))//特殊电缆有几根分屏段数
                    .divide(BigDecimal.valueOf(1000D), 16, RoundingMode.HALF_UP) //转换为平方厘米
                    .multiply(conductorDensity).multiply(length);
            //金额
            fireMoney = fireWeight.multiply(conductorUnitPrice);
            //单段火线外径 = 半径*2
            fireDiameter = fireRadius.multiply(BigDecimal.valueOf(2D)).multiply(getSilkPercent(fireRootNumber));
        } else {
            fireRadius = fireSilkNumber.divide(new BigDecimal("2"), 16, RoundingMode.HALF_UP);
            //火线面积
            BigDecimal fireArea = fireRadius.multiply(fireRadius).multiply(BigDecimal.valueOf(Math.PI));
            //折扣之后再次计算出的火线导体半径
            fireRadius = reduce(fireArea, conductorReduction);
            //粗芯重量
            fireWeight = fireRadius.multiply(fireRadius)
                    .multiply(BigDecimal.valueOf(Math.PI))
                    .multiply(new BigDecimal(fireRootNumber))
                    .multiply(fireStrand)//绞合系数
                    .multiply(new BigDecimal(fireNumber))//有几根火线
                    .divide(BigDecimal.valueOf(1000D), 16, RoundingMode.HALF_UP) //转换为平方厘米
                    .multiply(conductorDensity).multiply(length);
            //粗芯金额
            fireMoney = fireWeight.multiply(conductorUnitPrice);
            //单段火线外径 = 半径*2
            fireDiameter = fireRadius.multiply(BigDecimal.valueOf(2D)).multiply(getSilkPercent(fireRootNumber));
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
        BigDecimal num2 = BigDecimal.valueOf(2);
        int precision = 100;
        MathContext mc = new MathContext(precision, RoundingMode.HALF_UP);
        BigDecimal deviation = divide;
        int cnt = 0;
        while (cnt < precision) {
            deviation = (deviation.add(divide.divide(deviation, mc))).divide(num2, mc);
            cnt++;
        }
        deviation = deviation.setScale(16, RoundingMode.HALF_UP);
        return deviation;
    }

    //  获取多导体电缆的导体的计算外径
    public BigDecimal getExternalDiameter(BigDecimal fireDiameter, BigDecimal zeroDiameter) {
        BigDecimal externalDiameter = BigDecimal.ZERO;// 外径
        BigDecimal averageDiameter;
        if (special) {

        } else {
            if (zeroNumber == 0) {// 零线数为0时视为等圆
                externalDiameter = getSilkPercent(fireNumber).multiply(fireDiameter);
            } else {// 既有火线又有零线
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
}
