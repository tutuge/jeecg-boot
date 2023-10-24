package org.jeecg.modules.cable.entity.systemDelivery;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbDelivery {
    @Schema(description = "主键ID")
    private Integer ecbdId;//主键ID

    @Schema(description = "是否启用")
    private Boolean startType;//是否启用

    @Schema(description = "序号")
    private Integer sortId;//序号

    @Schema(description = "快递类型")
    private Integer deliveryType;//快递类型

    @Schema(description = "快递名称")
    private String deliveryName;//快递名称

    @Schema(description = "备注")
    private String description;//备注
}
