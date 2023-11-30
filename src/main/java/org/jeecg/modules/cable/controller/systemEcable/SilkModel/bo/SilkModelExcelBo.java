package org.jeecg.modules.cable.controller.systemEcable.SilkModel.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.jeecg.poi.excel.annotation.Excel;

@Data
@Schema(description = "型号导入类")
public class SilkModelExcelBo {

    @Schema(description = "型号简称")
    @Excel(name = "型号简称")
    @NotBlank(message = "型号简称不得为空")
    private String abbreviation;

    @Schema(description = "全称")
    @Excel(name = "型号全称")
    @NotBlank(message = "型号全称不得为空")
    private String fullName;

    @Excel(name = "型号系列")
    private String silkName;

    @Excel(name = "是否有导体", replace = {"是_true", "否_false"})
    @Schema(description = "导体")
    @NotNull(message = "是否有导体不得为空")
    private Boolean conductor;

    @Excel(name = "是否有云母带", replace = {"是_true", "否_false"})
    @Schema(description = "云母带")
    @NotNull(message = "是否有云母带不得为空")
    private Boolean micaTape;

    @Excel(name = "是否绝缘", replace = {"是_true", "否_false"})
    @Schema(description = "绝缘")
    @NotNull(message = "是否绝缘不得为空")
    private Boolean insulation;

    @Excel(name = "是否有填充物", replace = {"是_true", "否_false"})
    @Schema(description = "填充物")
    @NotNull(message = "是否有填充物不得为空")
    private Boolean infilling;

    @Excel(name = "是否有包带", replace = {"是_true", "否_false"})
    @Schema(description = "包带")
    @NotNull(message = "是否有包带不得为空")
    private Boolean bag;

    @Excel(name = "是否有屏蔽层", replace = {"是_true", "否_false"})
    @Schema(description = "屏蔽")
    @NotNull(message = "是否有屏蔽层不得为空")
    private Boolean shield;

    @Excel(name = "是否有钢带", replace = {"是_true", "否_false"})
    @Schema(description = "钢带")
    @NotNull(message = "是否有钢带不得为空")
    private Boolean steelBand;

    @Excel(name = "是否有护套", replace = {"是_true", "否_false"})
    @Schema(description = "护套")
    @NotNull(message = "是否有护套不得为空")
    private Boolean sheath;
}
