package org.jeecg.modules.cable.entity.quality;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;
import org.jeecg.modules.cable.entity.userCommon.EcbuStore;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcquParameter {

    @Schema(description = "主键ID")
    private Integer ecqupId;// 主键ID

    @Schema(description = "公司ID")
    private Integer ecCompanyId;// 公司ID

    @Schema(description = "质量等级ID")
    private Integer ecqulId;// 质量等级ID

    @Schema(description = "仓库ID")
    private Integer ecbusId;// 仓库ID

    @Schema(description = "每米长度")
    private BigDecimal length;// 每米长度

    @Schema(description = "成本加点")
    private BigDecimal cost;// 成本加点

    @Schema(description = "备注")
    private String description;// 备注

    @Schema(description = "丝型号")
    @TableField(exist = false)
    private EcSilk ecSilk;// 丝型号

    @Schema(description = "质量等级")
    @TableField(exist = false)
    private EcquLevel ecquLevel;// 质量等级

    @Schema(description = "仓库")
    @TableField(exist = false)
    private EcbuStore ecbuStore;// 仓库
}
