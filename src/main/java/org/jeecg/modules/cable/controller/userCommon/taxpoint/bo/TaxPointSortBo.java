package org.jeecg.modules.cable.controller.userCommon.taxpoint.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "系统税点排序")
public class TaxPointSortBo {


    @Schema(description = "主键ID")
    @NotNull(message = "主键ID不得为空")
    private Integer ecdtId;//主键ID

    @NotNull(message = "排序不得为空")
    @Schema(description = "排序")
    private Integer sortId;// 序号
}
