package org.jeecg.modules.cable.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "填充物")
@Data
public class InfillingMaterial {

    /**
     * 密度
     */
    private BigDecimal density;
    /**
     * 单位价格
     */
    private BigDecimal unitPrice;

    private BigDecimal externalDiameter = BigDecimal.ZERO;//填充物之后的外径
    private BigDecimal wideDiameter = BigDecimal.ZERO;// 粗芯直径
    private BigDecimal fineDiameter = BigDecimal.ZERO;// 细芯直径


    private BigDecimal infillingWeight = BigDecimal.ZERO;// 填充物重量
    private BigDecimal infillingMoney = BigDecimal.ZERO;// 填充物金额
}
