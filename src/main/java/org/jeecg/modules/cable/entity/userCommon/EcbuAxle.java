package org.jeecg.modules.cable.entity.userCommon;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "用户木轴")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbuAxle {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecbuaId;

    @Schema(description = "公司ID")
    private Integer ecCompanyId;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "排序")
    private Integer sortId;

    @Schema(description = "木轴名称")
    private String axleName;

    @Schema(description = "木轴高度")
    private BigDecimal axleHeight;

    @Schema(description = "中心圆直径")
    private BigDecimal circleDiameter;

    @Schema(description = "轴宽")
    private BigDecimal axleWidth;

    @Schema(description = "轴深")
    private BigDecimal axleDeep;

    @Schema(description = "木轴重量")
    private BigDecimal axleWeight;

    @Schema(description = "价格")
    private BigDecimal axlePrice;

    @Schema(description = "备注")
    private String description;
}
