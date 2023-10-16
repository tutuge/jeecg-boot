package org.jeecg.modules.cable.controller.efficiency.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "省份bo")
@Data
public class PccBo {

    @Schema(description = "类型 ID 1 快递使用")
    private Integer typeId;
}
