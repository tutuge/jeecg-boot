package org.jeecg.modules.cable.entity.userCommon;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbuAxle {

    @Schema(description = "主键ID")
    private Integer ecbuaId;//主键ID


    @Schema(description = "公司ID")
    private Integer ecCompanyId;//公司ID

    @Schema(description = "是否启用")
    private Boolean startType;//是否启用

    @Schema(description = "序号")
    private Integer sortId;//序号

    @Schema(description = "木轴名称")
    private String axleName;//木轴名称

    @Schema(description = "木轴高度")
    private BigDecimal axleHeight;//木轴高度

    @Schema(description = "中心圆直径")
    private BigDecimal circleDiameter;//中心圆直径

    @Schema(description = "轴宽")
    private BigDecimal axleWidth;//轴宽

    @Schema(description = "轴深")
    private BigDecimal axleDeep;//轴深

    @Schema(description = "轴重")
    private BigDecimal axleWeight;//轴重

    @Schema(description = "价格")
    private BigDecimal axlePrice;//价格

    @Schema(description = "备注")
    private String description;//备注
}
