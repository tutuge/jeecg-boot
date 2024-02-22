package org.jeecg.modules.cable.domain.computeBo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BaseBo {
    @Schema(description = "材料ID")
    private Integer id;

    @Schema(description = "材料名称")
    private String fullName;

    @Schema(description = "材料类型ID")
    private Integer materialTypeId;

    @Schema(description = "材料类型名称")
    private String materialTypeName;
}
