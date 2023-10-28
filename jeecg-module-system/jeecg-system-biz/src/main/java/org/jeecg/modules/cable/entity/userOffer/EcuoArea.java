package org.jeecg.modules.cable.entity.userOffer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "截面")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuoArea {

    @Schema(description = "主键ID")
    private Integer ecuoaId;// 主键ID

    @Schema(description = "质量等级ID")
    private Integer ecqulId;// 质量等级ID

    @Schema(description = "序号")
    private Integer sortId;// 序号

    @Schema(description = "截面")
    private String areaStr;// 截面
}
