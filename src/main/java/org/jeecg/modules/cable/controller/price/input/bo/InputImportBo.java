package org.jeecg.modules.cable.controller.price.input.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class InputImportBo {

    @Schema(description = "报价单ID")
    private Integer ecuqId;//报价单ID

    @Schema(description = "利润")
    private BigDecimal profit;//利润

    @Schema(description = "实际税点 此税点即为开发票的税点")
    private BigDecimal billPercent;//实际税点 此税点即为开发票的税点
}
