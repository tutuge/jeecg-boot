package org.jeecg.modules.cable.entity.userCommon;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "平台类型")
public class EcbuPcompany {

    @Schema(description = "主键ID")
    private Integer ecbupId;//主键ID

    @Schema(description = "公司ID")
    private Integer ecCompanyId;//公司ID

    @Schema(description = "是否启用")
    private Boolean startType;//是否启用

    @Schema(description = "序号")
    private Integer sortId;//序号

    @Schema(description = "平台类型ID")
    private Integer platformId;//平台类型ID

    @Schema(description = "平台名称")
    private String pcName;//平台名称

    @Schema(description = "平台税点")
    private BigDecimal pcPercent;//平台税点

    @Schema(description = "备注")
    private String description;//备注
}
