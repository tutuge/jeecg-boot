package org.jeecg.modules.cable.controller.userOffer.programme.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "方案")
@Data
public class ProgrammeBaseBo {

    @Schema(description = "主键ID")
    @NotNull(message = "主键不得为空")
    private Integer ecuopId;// 主键ID

}
