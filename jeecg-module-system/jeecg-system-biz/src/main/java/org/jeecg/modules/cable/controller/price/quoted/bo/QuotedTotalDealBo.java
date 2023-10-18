package org.jeecg.modules.cable.controller.price.quoted.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
@Schema(description = "下方备注添加")
public class QuotedTotalDealBo {

    @Schema(description = "主键ID")
    private Integer ecuqId;

    @Schema(description = "备注标题")
    private String totalTitle;//备注标题

    @Schema(description = "总备注")
    private String totalDesc;//总备注
}
