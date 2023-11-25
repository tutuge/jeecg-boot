package org.jeecg.modules.cable.controller.userQuality.parameter.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.userQuality.EcquParameter;

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
