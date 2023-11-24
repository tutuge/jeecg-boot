package org.jeecg.modules.cable.entity.userDelivery;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Schema(description = "快递重量区间设置设置")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbudModel {

    @Schema(description = "主键ID")
    private Integer ecbudmId;

    /**
     * 关联 ecbuDelivery （各个仓库对应的快运/快递）表
     */
    @Schema(description = "快递ID")
    private Integer ecbudId;

    @Schema(description = "开始重量1")
    private Integer startWeight1;

    @Schema(description = "结束重量1")
    private Integer endWeight1;

    @Schema(description = "开始重量2")
    private Integer startWeight2;

    @Schema(description = "结束重量2")
    private Integer endWeight2;

    @Schema(description = "开始重量3")
    private Integer startWeight3;

    @Schema(description = "结束重量3")
    private Integer endWeight3;

    @Schema(description = "开始重量4")
    private Integer startWeight4;

    @Schema(description = "结束重量4")
    private Integer endWeight4;

    @Schema(description = "开始重量5")
    private Integer startWeight5;

    @Schema(description = "结束重量5")
    private Integer endWeight5;
}
