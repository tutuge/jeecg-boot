package org.jeecg.modules.cable.domain.materialType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 成本库表内的批量处理
 */
@Data
public class MaterialTypeBatch {

    @Schema(description = "材料类型ID")
    private Integer materialTypeId;

    @Schema(description = "材料类型 0 普通材料 1 导体 2 填充物")
    private Integer materialType;

    @Schema(description = "名称")
    private String fullName;
}
