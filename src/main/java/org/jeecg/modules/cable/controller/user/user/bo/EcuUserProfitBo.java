package org.jeecg.modules.cable.controller.user.user.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "用户修改利润点")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuUserProfitBo {

    @Schema(description = "利润")
    @NotNull(message = "利润点不得为空")
    private BigDecimal profit;//利润
}
