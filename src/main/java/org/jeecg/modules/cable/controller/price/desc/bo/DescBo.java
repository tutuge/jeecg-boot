package org.jeecg.modules.cable.controller.price.desc.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "结构信息")
@Data
public class DescBo {
    @Schema(description = "主键ID")
    private Integer ecuqiId;
}
