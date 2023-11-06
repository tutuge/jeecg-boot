package org.jeecg.modules.cable.entity.quality;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "平方数")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuArea {

    @Schema(description = "主键ID")
    private Integer ecuaId;// 主键ID

    @Schema(description = "公司ID")
    private Integer ecCompanyId;// 公司ID

    @Schema(description = "质量等级ID")
    private Integer ecqulId;// 质量等级ID

    @Schema(description = "是否启用")
    private Boolean startType;// 是否启用

    @Schema(description = "排序")
    private Integer sortId;// 序号

    @Schema(description = "截面")
    private String areaStr;// 截面

    @Schema(description = "生效时间")
    private Long effectTime;
}
