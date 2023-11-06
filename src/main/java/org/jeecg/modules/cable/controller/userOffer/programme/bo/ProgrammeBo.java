package org.jeecg.modules.cable.controller.userOffer.programme.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "方案")
@Data
public class ProgrammeBo {

    /**
     * 质量等级ID
     */
    @Schema(description = "质量等级ID")
    @NotNull(message = "质量等级ID不可为空")
    private Integer ecqulId;

    @Schema(description = "主键ID")
    @NotNull(message = "主键ID不可为空")
    private Integer ecuopId;//主键ID
}
