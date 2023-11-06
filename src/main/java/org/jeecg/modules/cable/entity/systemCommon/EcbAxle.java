package org.jeecg.modules.cable.entity.systemCommon;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "系统木轴")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbAxle {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecbaId;//主键ID

    @Schema(description = "是否启用")
    private Boolean startType;//是否启用

    @Schema(description = "排序")
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
