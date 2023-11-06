package org.jeecg.modules.cable.controller.userEcable.bag.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "包带")
@Data
public class EcbuBagStartBo {

    @NotNull(message = "包带ID不得为空")
    @Schema(description = "包带ID")
    private Integer ecbbId;
}
