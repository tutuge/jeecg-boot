package org.jeecg.modules.cable.controller.user.notice.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "用户备注")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuNoticeDealBo {

    @Schema(description = "主键ID")
    private Integer ecunId;//主键ID

    @Schema(description = "注意事项名称")
    @NotBlank(message = "注意事项名称不得为空")
    private String noticeName;//注意事项名称

    @NotBlank(message = "标题不得为空")
    @Schema(description = "标题")
    private String title;//标题

    @NotBlank(message = "内容不得为空")
    @Schema(description = "内容")
    private String content;//内容
}
