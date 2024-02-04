package org.jeecg.modules.cable.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "导体到填充物之间的内部材料")
@Data
public class InternalMaterial {
    //todo 缺少id和名称
    /**
     * 密度
     */
    private BigDecimal density;
    /**
     * 单位价格
     */
    private BigDecimal unitPrice;
    /**
     * 系数
     */
    private BigDecimal factor;
    /**
     * 粗芯材料厚度
     */
    private BigDecimal fireThickness;
    /**
     * 细芯材料厚度
     */
    private BigDecimal zeroThickness;


    //计算后当前材料的数值
    private BigDecimal fireRadius = BigDecimal.ZERO;// 粗芯材料总半径
    private BigDecimal fireWeight = BigDecimal.ZERO;// 粗芯材料重量
    private BigDecimal fireMoney = BigDecimal.ZERO;// 粗芯材料金额

    private BigDecimal zeroRadius = BigDecimal.ZERO;// 细芯材料总半径
    private BigDecimal zeroWeight = BigDecimal.ZERO;// 细芯材料重量
    private BigDecimal zeroMoney = BigDecimal.ZERO;// 细芯材料金额

    private BigDecimal materialWeight = BigDecimal.ZERO;// 当前材料总重量
    private BigDecimal materialMoney = BigDecimal.ZERO;// 当前材料总金额
}
