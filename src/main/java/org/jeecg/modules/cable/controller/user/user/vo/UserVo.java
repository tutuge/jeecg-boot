package org.jeecg.modules.cable.controller.user.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.common.system.vo.EcUser;

import java.util.List;

@Schema(description = "用户")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {
    @Schema(description = "列表")
    private List<EcUser> list;

    @Schema(description = "数量")
    private Long count;
}
