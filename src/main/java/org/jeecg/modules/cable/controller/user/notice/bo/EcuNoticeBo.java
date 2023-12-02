package org.jeecg.modules.cable.controller.user.notice.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "用户备注")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuNoticeBo {


    @Schema(description = "主键ID")
    @NotNull(message = "主键ID不得为空")
    private Integer ecunId;


    @Schema(description = "是否默认")
    private Boolean defaultType;
}
