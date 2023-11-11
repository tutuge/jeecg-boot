package org.jeecg.modules.cable.entity.userDelivery;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "用户默认物流或快递类型")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbudDelivery {
    /**
     * 用户默认物流或快递类型
     */
    @Schema(description = "主键ID")
    private Integer ecbuddId;

    @Schema(description = "报价单ID")
    private Integer ecuqId;

    @Schema(description = "公司ID")
    private Integer ecCompanyId;//公司ID

    @Schema(description = "用户ID")
    private Integer ecuId;//用户ID

    @Schema(description = "序号")
    private Integer sortId;//序号
}
