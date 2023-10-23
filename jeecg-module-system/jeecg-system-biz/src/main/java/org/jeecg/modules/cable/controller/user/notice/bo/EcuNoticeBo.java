package org.jeecg.modules.cable.controller.user.notice.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "用户备注")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuNoticeBo {


    @Schema(description = "主键ID")
    private Integer ecunId;//主键ID


    @Schema(description = "是否默认")
    private Boolean defaultType;//是否默认
}
