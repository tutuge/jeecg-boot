package org.jeecg.modules.cable.entity.efficiency;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcdPcc {
    @Schema(description = "主键ID")
    private Integer ecdpId;

    @Schema(description = "类型 1 快递使用")
    private Integer typeId;

    @Schema(description = "txt文件路径")
    private String txtUrl;

    @Schema(description = "生效时间")
    private Long effectTime;
}
