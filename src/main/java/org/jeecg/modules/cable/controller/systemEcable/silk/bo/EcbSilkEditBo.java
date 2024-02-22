package org.jeecg.modules.cable.controller.systemEcable.silk.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.jeecg.common.validate.AddGroup;
import org.jeecg.common.validate.EditGroup;
import org.jeecg.modules.cable.entity.systemEcable.EcbMaterialType;
import org.jeecg.modules.cable.entity.userEcable.EcbuMaterialType;

import java.util.List;

@Data
public class EcbSilkEditBo {

    @Schema(description = "主键ID")
    @NotNull(message = "主键ID不得为空", groups = {EditGroup.class})
    private Integer ecsId;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "简称")
    @NotBlank(message = "简称不得为空", groups = {EditGroup.class, AddGroup.class})
    private String abbreviation;

    @Schema(description = "全称")
    @NotBlank(message = "全称不得为空", groups = {EditGroup.class, AddGroup.class})
    private String fullName;

    @Schema(description = "材料类型")
    private List<EcbMaterialType> materialTypesList;


    @Schema(description = "介绍")
    private String description;
}
