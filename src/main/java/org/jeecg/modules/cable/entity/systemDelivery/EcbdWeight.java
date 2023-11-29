package org.jeecg.modules.cable.entity.systemDelivery;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbdWeight {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecbdmId;

    @Schema(description = "快递ID")
    private Integer ecbdId;

    @Schema(description = "起始重量")
    private Integer startWeight1;

    @Schema(description = "结束重量")
    private Integer endWeight1;

    @Schema(description = "起始重量")
    private Integer startWeight2;

    @Schema(description = "结束重量")
    private Integer endWeight2;

    @Schema(description = "起始重量")
    private Integer startWeight3;

    @Schema(description = "结束重量")
    private Integer endWeight3;

    @Schema(description = "起始重量")
    private Integer startWeight4;

    @Schema(description = "结束重量")
    private Integer endWeight4;

    @Schema(description = "起始重量")
    private Integer startWeight5;

    @Schema(description = "结束重量")
    private Integer endWeight5;
}
