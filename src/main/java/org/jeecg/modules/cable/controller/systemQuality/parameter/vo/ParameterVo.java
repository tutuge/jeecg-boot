package org.jeecg.modules.cable.controller.systemQuality.parameter.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.systemQuality.EcqParameter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "系统电缆质量参数")
@Data
public class ParameterVo {

    @Schema(description = "list")
    private List<EcqParameter> list;

    @Schema(description = "count")
    private Long count;
}
