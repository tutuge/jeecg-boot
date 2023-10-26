package org.jeecg.modules.cable.controller.userCommon.taxpoint.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "获取系统税点")
public class TaxPointBaseBo {


    @Schema(description = "主键ID")
    @NotNull(message = "主键ID不得为空")
    private Integer ecdtId;//主键ID
}
