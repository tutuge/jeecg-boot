package org.jeecg.modules.cable.controller.systemDelivery.weight.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbdModelDealBo {
    @Schema(description = "主键ID")
    @NotNull(message = "重量区间主键ID不得为空")
    private Integer ecbdmId;

    @Schema(description = "快递ID")
    private Integer ecbdId;

    @Schema(description = "起始重量")
    @NotNull(message = "区间一起始重量不得为空")
    private Integer startWeight1;

    @Schema(description = "结束重量")
    @NotNull(message = "区间一结束重量不得为空")
    private Integer endWeight1;

    @Schema(description = "起始重量")
    @NotNull(message = "区间二起始重量不得为空")
    private Integer startWeight2;

    @Schema(description = "结束重量")
    @NotNull(message = "区间二结束重量不得为空")
    private Integer endWeight2;

    @Schema(description = "起始重量")
    @NotNull(message = "区间三起始重量不得为空")
    private Integer startWeight3;

    @Schema(description = "结束重量")
    @NotNull(message = "区间三结束重量不得为空")
    private Integer endWeight3;

    @Schema(description = "起始重量")
    @NotNull(message = "区间四起始重量不得为空")
    private Integer startWeight4;

    @Schema(description = "结束重量")
    @NotNull(message = "区间四结束重量不得为空")
    private Integer endWeight4;

    @Schema(description = "起始重量")
    @NotNull(message = "区间五起始重量不得为空")
    private Integer startWeight5;

    @Schema(description = "结束重量")
    @NotNull(message = "区间五结束重量不得为空")
    private Integer endWeight5;
}
