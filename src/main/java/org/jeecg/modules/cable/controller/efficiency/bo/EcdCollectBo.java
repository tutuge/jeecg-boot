package org.jeecg.modules.cable.controller.efficiency.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EcdCollectBo {

    @Schema(description = "类型 1用户仓库 2质量等级 3用户导体 4用户云母带 5用户绝缘数据 6用户填充物数据 7用户包带数据 8用户屏蔽数据 9用户钢带数据 10 用户单位长度数据 11用户平台公司数据")
    @NotNull(message = "类型不得为空")
    private Integer typeId;
}
