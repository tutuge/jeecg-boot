package org.jeecg.modules.cable.entity.userCommon;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbusAttribute {

    @Schema(description = "主键ID")
    private Integer ecbusaId;//主键ID

    @Schema(description = "公司ID")
    private Integer ecCompanyId;//公司ID

    @Schema(description = "铜利润显示或隐藏")
    private Boolean pcShowOrHide;//铜利润显示或隐藏

    @Schema(description = "铝利润显示或隐藏")
    private Boolean paShowOrHide;//铝利润显示或隐藏

    @Schema(description = "运费加点显示或隐藏")
    private Boolean dmShowOrHide;//运费加点显示或隐藏
}
