package org.jeecg.modules.cable.entity.userEcable;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jeecg.modules.cable.entity.systemEcable.EcbSteelband;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "用户钢带")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbuSteelband {

    @Schema(description = "主键ID")
    private Integer ecbusId;//主键ID

    @Schema(description = "系统钢带ID")
    private Integer ecbsbId;//系统钢带ID


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

    @Schema(description = "系统钢带")
    private EcbSteelband ecbSteelband;//系统钢带
}
