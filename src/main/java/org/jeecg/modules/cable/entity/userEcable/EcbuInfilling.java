package org.jeecg.modules.cable.entity.userEcable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.systemEcable.EcbInfilling;

import java.math.BigDecimal;

@Schema(description = "用户填充物")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbuInfilling {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecbuiId;//主键ID

    @Schema(description = "系统填充物ID")
    private Integer ecbinId;//系统填充物ID

    @Schema(description = "公司ID")
    private Integer ecCompanyId;//公司ID

    @Schema(description = "是否启用")
    private Boolean startType;//是否启用

    @Schema(description = "自定义名称")
    private String name;//自定义名称

    @Schema(description = "单价")
    private BigDecimal unitPrice;//单价

    @Schema(description = "密度")
    private BigDecimal density;//密度

    @Schema(description = "备注")
    private String description;//备注

    @Schema(description = "系统填充物")
    @TableField(exist = false)
    private EcbInfilling ecbInfilling;//系统填充物
}
