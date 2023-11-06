package org.jeecg.modules.cable.controller.systemEcable.bag.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "系统包带列表请求参数")
@Data
public class EcbBagListBo {

    @Schema(description = "是否启用")
    private Boolean startType;
}
