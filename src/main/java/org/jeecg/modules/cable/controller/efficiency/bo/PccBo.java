package org.jeecg.modules.cable.controller.efficiency.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "省份bo")
@Data
public class PccBo {

    @Schema(description = "类型 ID 1 省份信息 2 省市县信息")
    @NotNull(message = "类型不得为空")
    private Integer typeId;
}
