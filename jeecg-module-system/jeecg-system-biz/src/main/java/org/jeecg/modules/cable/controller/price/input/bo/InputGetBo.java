package org.jeecg.modules.cable.controller.price.input.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "通过ID获取EcuqInput")
@Data
public class InputGetBo {

    @Schema(description = "主键ID")
    private Integer ecuqiId;
}
