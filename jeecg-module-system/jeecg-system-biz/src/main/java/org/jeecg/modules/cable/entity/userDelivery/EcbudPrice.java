package org.jeecg.modules.cable.entity.userDelivery;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.pcc.EcProvince;

import java.math.BigDecimal;

@Schema(description = "物流信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbudPrice {

    @Schema(description = "物流Id")
    private Integer ecbudpId;

    @Schema(description = "快递Id")
    private Integer ecbudId;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "排序")
    private Integer sortId;

    private Integer ecpId;

    private String provinceName;

    private BigDecimal firstPrice;

    private BigDecimal price1;

    private BigDecimal price2;

    private BigDecimal price3;

    private BigDecimal price4;

    private BigDecimal price5;

    //Ec_province
    private EcProvince ecProvince;
}
