package org.jeecg.modules.cable.controller.quality.level.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EcquLevelDealBo {

    @Schema(description = "主键ID")
    private Integer ecqulId;// 主键ID

    @Schema(description = "丝型号ID")
    private Integer ecsId;// 丝型号ID

    @Schema(description = "用户导体ID")
    private Integer ecbucId;// 用户导体ID

    @Schema(description = "自定义名称")
    private String name;// 自定义名称

    @Schema(description = "备注")
    private String description;// 备注
}
