package org.jeecg.modules.cable.controller.price.desc.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "结构信息")
@Data
public class DescStartBo {
    @Schema(description = "主键ID")
    private Integer ecuqdId;// 主键ID

    @Schema(description = "是否启动手输")
    private Boolean inputStart;// 是否启动手输
}
