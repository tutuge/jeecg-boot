package org.jeecg.modules.cable.controller.userCommon.taxpoint.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "获取系统税点列表")
public class UTaxPointBo {


    @Schema(description = "系统发票税点ID")
    private Integer ecdtId;//系统发票税点id

    @Schema(description = "是否启用")
    private Boolean startType;
}
