package org.jeecg.modules.cable.entity.userQuality;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;
import org.jeecg.modules.cable.entity.userCommon.EcbuStore;

import java.math.BigDecimal;

@Schema(description = "参数信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcquParameter {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecqupId;

    @Schema(description = "公司ID")
    private Integer ecCompanyId;

    @Schema(description = "质量等级ID")
    private Integer ecqulId;// 质量等级ID

    @Schema(description = "仓库ID")
    private Integer ecbusId;

    @Schema(description = "每米长度")
    private BigDecimal length;// 每米长度

    @Schema(description = "成本加点")
    private BigDecimal cost;// 成本加点

    @Schema(description = "备注")
    private String description;

    @Schema(description = "型号类型")
    @TableField(exist = false)
    private EcSilk ecSilk;

    @Schema(description = "质量等级")
    @TableField(exist = false)
    private EcquLevel ecquLevel;

    @Schema(description = "仓库")
    @TableField(exist = false)
    private EcbuStore ecbuStore;
}
