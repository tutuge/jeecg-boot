package org.jeecg.modules.cable.controller.userQuality.level.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "电缆质量等级编辑")
@Data
public class EcquLevelDealBo {

    @Schema(description = "主键ID")
    private Integer ecqulId;// 主键ID

    @Schema(description = "型号ID")
    @NotNull(message = "型号ID不得为空")
    private Integer ecsId;

    @Schema(description = "导体ID")
    @NotNull(message = "导体ID不得为空")
    private Integer ecbucId;

    @Schema(description = "自定义名称")
    @NotNull(message = "自定义名称不得为空")
    private String name;

    @Schema(description = "备注")
    private String description;
}
