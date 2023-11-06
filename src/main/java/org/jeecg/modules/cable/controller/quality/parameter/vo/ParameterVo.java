package org.jeecg.modules.cable.controller.quality.parameter.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.quality.EcquLevel;
import org.jeecg.modules.cable.entity.quality.EcquParameter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "电缆质量")
@Data
public class ParameterVo {

    @Schema(description = "list")
    private List<EcquParameter> list;

    @Schema(description = "count")
    private  Long count;
}
