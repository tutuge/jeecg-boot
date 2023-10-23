package org.jeecg.modules.cable.entity.userEcable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.systemEcable.EcbBag;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbuBag {

    @Schema(description = "主键ID")
    private Integer ecbubId;//主键ID

    @Schema(description = "系统包带ID")
    private Integer ecbbId;//系统包带ID

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
    private EcbBag ecbBag;//系统填充物
}
