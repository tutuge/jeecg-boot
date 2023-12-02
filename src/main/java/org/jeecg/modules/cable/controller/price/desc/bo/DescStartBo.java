package org.jeecg.modules.cable.controller.price.desc.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "报价单明细金额是否手输")
@Data
public class DescStartBo {
    @Schema(description = "主键ID")
    @NotNull(message = "主键ID不得为空")
    private Integer ecuqiId;

    @Schema(description = "是否启动手输")
    @NotNull(message = "是否启动手输不得为空")
    private Boolean inputStart;
}
