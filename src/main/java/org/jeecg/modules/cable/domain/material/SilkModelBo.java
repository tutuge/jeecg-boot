package org.jeecg.modules.cable.domain.material;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "材料是否使用")
@Data
public class SilkModelBo {

    @Schema(description = "材料类型名称")
    private String fullName;

    @Schema(description = "材料类型ID")
    private Integer id;

    @Schema(description = "是否启用")
    private Boolean use;
}
