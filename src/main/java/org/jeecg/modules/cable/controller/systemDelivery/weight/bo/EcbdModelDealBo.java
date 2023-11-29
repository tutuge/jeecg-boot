package org.jeecg.modules.cable.controller.systemDelivery.weight.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbdModelDealBo {
    @Schema(description = "主键ID")
    private Integer ecbdmId;//主键ID

    @Schema(description = "快递ID")
    private Integer ecbdId;//快递ID

    @Schema(description = "起始重量")
    private Integer startWeight1;//起始重量

    @Schema(description = "结束重量")
    private Integer endWeight1;//结束重量

    @Schema(description = "起始重量")
    private Integer startWeight2;//起始重量

    @Schema(description = "结束重量")
    private Integer endWeight2;//结束重量

    @Schema(description = "起始重量")
    private Integer startWeight3;//起始重量

    @Schema(description = "结束重量")
    private Integer endWeight3;//结束重量

    @Schema(description = "起始重量")
    private Integer startWeight4;//起始重量


    @Schema(description = "结束重量")
    private Integer endWeight4;//结束重量

    @Schema(description = "起始重量")
    private Integer startWeight5;//起始重量
    @Schema(description = "结束重量")
    private Integer endWeight5;//结束重量
}
