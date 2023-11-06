package org.jeecg.modules.cable.entity.userDelivery;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Schema(description = "表头")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbudModel {
    @Schema(description = "主键ID")
    private Integer ecbudmId;// 主键ID

    @Schema(description = "快递ID")
    private Integer ecbudId;// 快递ID

    @Schema(description = "开始重量1")
    private Integer startWeight1;// 开始重量1

    @Schema(description = "结束重量1")
    private Integer endWeight1;// 结束重量1

    @Schema(description = "开始重量2")
    private Integer startWeight2;// 开始重量2


    @Schema(description = "结束重量2")
    private Integer endWeight2;// 结束重量2

    @Schema(description = "开始重量3")
    private Integer startWeight3;// 开始重量3

    @Schema(description = "结束重量3")
    private Integer endWeight3;// 结束重量3

    @Schema(description = "开始重量4")
    private Integer startWeight4;// 开始重量4

    @Schema(description = "结束重量4")
    private Integer endWeight4;// 结束重量4

    @Schema(description = "开始重量5")
    private Integer startWeight5;// 开始重量5

    @Schema(description = "结束重量5")
    private Integer endWeight5;// 结束重量5
}
