package org.jeecg.modules.cable.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "填充物之外的材料")
@Data
public class ExternalMaterial {

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
     * 外部材料厚度
     */
    private BigDecimal thickness;



    //--------------计算后当前材料的数值--------------
    BigDecimal externalRadius = BigDecimal.ZERO;// 外部总半径

    BigDecimal materialWeight = BigDecimal.ZERO;// 当前材料重量
    BigDecimal materialMoney = BigDecimal.ZERO;// 当前材料金额
}
