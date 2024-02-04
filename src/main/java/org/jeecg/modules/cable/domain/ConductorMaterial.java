package org.jeecg.modules.cable.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "导体对象")
@Data
public class ConductorMaterial {
    //todo 缺少导体的id和名称
    /**
     * 导体密度
     */
    private BigDecimal conductorDensity;
    /**
     * 导体单价
     */
    private BigDecimal conductorUnitPrice;
    /**
     * 粗芯根数
     */
    private Integer fireRootNumber;
    /**
     * 细芯根数
     */
    private Integer zeroRootNumber;
    /**
     * 粗芯丝号 也就是火线直径
     */
    private BigDecimal fireSilkNumber;
    /**
     * 细芯丝号 也就是零线直径
     */
    private BigDecimal zeroSilkNumber;
    ///**
    // * 粗芯过膜
    // */
    //private Integer fireMembrance;
    /**
     * 粗芯绞合系数
     */
    private BigDecimal fireStrand;
    ///**
    // * 细芯过膜
    // */
    //private Integer zeroMembrance;
    /**
     * 细芯绞合系数
     */
    private BigDecimal zeroStrand;


    //----------------报价单上的导体截面折扣---------------

    private  BigDecimal conductorReduction;



    //--------------计算后---------------
    /**
     * 粗芯半径
     */
    private BigDecimal fireRadius = BigDecimal.ZERO;
    /**
     * 细芯半径
     */
    private BigDecimal zeroRadius = BigDecimal.ZERO;
    /**
     * 粗芯直径
     */
    private BigDecimal fireDiameter = BigDecimal.ZERO;
    /**
     * 细芯直径
     */
    private BigDecimal zeroDiameter = BigDecimal.ZERO;
    /**
     * 导体直径
     */
    private BigDecimal externalDiameter = BigDecimal.ZERO;



    /**
     * 粗芯重量
     */
    private BigDecimal fireWeight = BigDecimal.ZERO;
    /**
     * 细芯重量
     */
    private BigDecimal zeroWeight = BigDecimal.ZERO;
    /**
     * 粗芯金额
     */
    private BigDecimal fireMoney = BigDecimal.ZERO;
    /**
     * 细芯金额
     */
    private BigDecimal zeroMoney = BigDecimal.ZERO;
    /**
     * 导体总重量
     */
    private BigDecimal conductorWeight = BigDecimal.ZERO;

    /**
     * 导体总金额
     */
    private BigDecimal conductorMoney = BigDecimal.ZERO;
}
