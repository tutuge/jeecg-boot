package org.jeecg.modules.cable.entity.systemEcable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcSilk {

    @Schema(description = "主键ID")
    private Integer ecsId;// 主键ID

    @Schema(description = "管理员ID")
    private Integer ecaId;// 管理员ID

    @Schema(description = "管理员名称")
    private String ecaName;// 管理员名称

    @Schema(description = "是否启用")
    private Boolean startType;// 是否启用

    @Schema(description = "排序")
    private Integer sortId;// 序号

    @Schema(description = "简称")
    private String abbreviation;// 简称

    @Schema(description = "全称")
    private String fullName;// 全称

    @Schema(description = "介绍")
    private String description;//

    @Schema(description = "添加时间")
    private Long addTime;// 添加时间

    @Schema(description = "更新时间")
    private Long updateTime;// 更新时间
}
