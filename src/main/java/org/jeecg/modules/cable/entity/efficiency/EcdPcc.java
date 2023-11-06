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
    private Integer ecdpId;//主键ID

    @Schema(description = "类型 1 快递使用")
    private Integer typeId;//类型 1 快递使用
    
    @Schema(description = "txt文件路径")
    private String txtUrl;//txt文件路径

    @Schema(description = "生效时间")
    private Long effectTime;//影响时间
}
