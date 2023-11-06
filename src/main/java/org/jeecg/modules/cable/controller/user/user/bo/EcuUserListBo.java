package org.jeecg.modules.cable.controller.user.user.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "用户")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuUserListBo {


    @Schema(description = "是否启用")
    private Boolean startType;//是否启用
}
