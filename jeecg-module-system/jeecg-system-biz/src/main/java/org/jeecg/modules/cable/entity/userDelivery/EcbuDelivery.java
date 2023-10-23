package org.jeecg.modules.cable.entity.userDelivery;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.userCommon.EcbuStore;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbuDelivery {

    private Integer ecbudId;

    @Schema(description = "公司ID")
    private Integer ecCompanyId;

    @Schema(description = "仓库ID")
    private Integer ecbusId;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "排序")
    private Integer sortId;

    @Schema(description = "快递类型")
    private Integer deliveryType;

    @Schema(description = "快递名称")
    private String deliveryName;

    private String description;

    private EcbuStore ecbuStore;
}
