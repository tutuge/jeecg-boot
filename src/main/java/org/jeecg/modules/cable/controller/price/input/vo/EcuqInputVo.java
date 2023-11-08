package org.jeecg.modules.cable.controller.price.input.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.price.EcuqInput;

import java.math.BigDecimal;

@Schema(description = "报价单每列数据")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class EcuqInputVo extends EcuqInput {

    @Schema(description = "木轴ID")
    private Integer ecbuaId;

    @Schema(description = "木轴数量")
    private Integer axleNumber;

    @Schema(description = "税前单价")
    private BigDecimal unitPrice;// 税前单价

}
