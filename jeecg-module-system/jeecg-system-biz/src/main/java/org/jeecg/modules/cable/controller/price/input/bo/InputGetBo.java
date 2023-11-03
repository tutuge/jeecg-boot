package org.jeecg.modules.cable.controller.price.input.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "通过ID获取EcuqInput")
@Data
public class InputGetBo {

    @Schema(description = "主键ID")
    @NotNull(message = "主键不得为空")
    private Integer ecuqiId;
}
