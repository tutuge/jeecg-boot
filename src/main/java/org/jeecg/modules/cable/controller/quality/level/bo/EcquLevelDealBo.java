package org.jeecg.modules.cable.controller.quality.level.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EcquLevelDealBo {

    @Schema(description = "主键ID")
    private Integer ecqulId;// 主键ID

    @Schema(description = "型号ID")
    @NotNull(message = "型号ID不得为空")
    private Integer ecsId;// 丝型号ID

    @Schema(description = "导体ID")
    @NotNull(message = "导体ID不得为空")
    private Integer ecbucId;// 用户导体ID

    @Schema(description = "自定义名称")
    @NotNull(message = "自定义名称不得为空")
    private String name;// 自定义名称

    @Schema(description = "备注")
    private String description;// 备注
}
