package org.jeecg.modules.cable.controller.userCommon.utaxpoint.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "获取税点")
public class UTaxPointDealBo {

    @Schema(description = "系统发票税点id")
    private Integer ecdtId;//系统发票税点id

    @Schema(description = "自定义名称")
    private String name;//自定义名称

    @Schema(description = "普票税点")
    private BigDecimal percentCommon;//普票税点

    @Schema(description = "专票税点")
    private BigDecimal percentSpecial;//专票税点

    @Schema(description = "备注")
    private String description;//备注
}
