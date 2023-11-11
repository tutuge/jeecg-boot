package org.jeecg.modules.cable.controller.userCommon.store.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "获取仓库列表")
public class StoreBo {
    
    @Schema(description = "是否启用")
    private Boolean startType;
}
