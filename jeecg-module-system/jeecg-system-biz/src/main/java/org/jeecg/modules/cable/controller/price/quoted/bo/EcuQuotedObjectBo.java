package org.jeecg.modules.cable.controller.price.quoted.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "报价单")
public class EcuQuotedObjectBo {

    @Schema(description = "主键ID")
    private Integer ecuqId;//主键ID
}
