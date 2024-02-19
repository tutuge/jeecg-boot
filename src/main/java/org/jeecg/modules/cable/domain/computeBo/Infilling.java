package org.jeecg.modules.cable.domain.computeBo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "填充物")
@Data
public class Infilling {

    @Schema(description = "填充物ID")
    private Integer id;

    @Schema(description = "材料名称")
    private String fullName;
}
