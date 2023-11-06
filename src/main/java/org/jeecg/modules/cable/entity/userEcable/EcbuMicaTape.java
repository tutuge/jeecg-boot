package org.jeecg.modules.cable.entity.userEcable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.systemEcable.EcbMicaTape;

import java.math.BigDecimal;

@Schema(description = "用户云母带")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbuMicaTape {
    private Integer ecbumId;//主键ID

    @Schema(description = "系统云母带ID")
    private Integer ecbmId;//系统云母带ID

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

    @Schema(description = "系统云母带")
    private EcbMicaTape ecbMicatape;//系统云母带
}
