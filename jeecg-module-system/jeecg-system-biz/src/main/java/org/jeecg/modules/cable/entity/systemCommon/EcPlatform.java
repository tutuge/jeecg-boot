package org.jeecg.modules.cable.entity.systemCommon;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "平台类型")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcPlatform {

    @Schema(description = "平台类型ID")
    private Integer platformId;// 主键ID

    @Schema(description = "是否启用")
    private Boolean startType;// 是否启用

    @Schema(description = "序号")
    private Integer sortId;// 序号

    @Schema(description = "平台类型名称")
    private String platformName;

    @Schema(description = "备注")
    private String description;// 备注
}
