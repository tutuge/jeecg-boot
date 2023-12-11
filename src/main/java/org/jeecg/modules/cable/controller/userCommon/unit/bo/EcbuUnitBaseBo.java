package org.jeecg.modules.cable.controller.userCommon.unit.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "单位长度")
@Data
public class EcbuUnitBaseBo {

    @NotNull(message = "单位长度ID不得为空")
    @Schema(description = "单位长度ID")
    private Integer ecbuluId;
}
