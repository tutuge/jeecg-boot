package org.jeecg.modules.cable.controller.userCommon.utaxpoint.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "获取税点")
public class UTaxPointBaseBo {

    @Schema(description = "系统发票税点id")
    private Integer ecdtId;//系统发票税点id
}
