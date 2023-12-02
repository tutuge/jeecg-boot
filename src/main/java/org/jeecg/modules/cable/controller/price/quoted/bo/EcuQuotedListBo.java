package org.jeecg.modules.cable.controller.price.quoted.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "报价单")
public class EcuQuotedListBo extends EcuQuotedBo {
    @Schema(description = "每页数量")
    private Integer pageSize = 10;

    @Schema(description = "页码")
    private Integer pageNo = 1;
}
