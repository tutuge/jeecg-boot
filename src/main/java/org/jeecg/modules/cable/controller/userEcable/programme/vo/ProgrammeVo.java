package org.jeecg.modules.cable.controller.userEcable.programme.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "获取编辑结构中的重量和金额")
@Data
public class ProgrammeVo {

    @Schema(description = "成本库表结构信息")
    private List<MaterialVo> materialVos;
}
