package org.jeecg.modules.cable.controller.systemCommon.taxpoint.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "获取系统税点列表")
public class TaxPointBo {
    
    @Schema(description = "是否启用")
    private Boolean startType;
}
