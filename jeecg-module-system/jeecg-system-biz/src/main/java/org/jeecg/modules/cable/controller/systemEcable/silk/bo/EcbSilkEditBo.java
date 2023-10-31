package org.jeecg.modules.cable.controller.systemEcable.silk.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.jeecg.common.validate.AddGroup;
import org.jeecg.common.validate.EditGroup;

@Data
public class EcbSilkEditBo {

    @Schema(description = "主键ID")
    @NotNull(message = "主键ID不得为空", groups = {EditGroup.class})
    private Integer ecsId;// 主键ID

    @Schema(description = "是否启用")
    private Boolean startType;// 是否启用

    @Schema(description = "简称")
    @NotBlank(message = "简称不得为空", groups = {EditGroup.class, AddGroup.class})
    private String abbreviation;// 简称

    @Schema(description = "全称")
    @NotBlank(message = "全称不得为空", groups = {EditGroup.class, AddGroup.class})
    private String fullName;// 全称

    /**
     * 导体ID
     */

    @Schema(description = "导体ID")
    @NotNull(message = "导体ID不得为空")
    private Integer conductorId;
    /**
     * 云母带ID
     */

    @Schema(description = "云母带ID")
    @NotNull(message = "云母带ID不得为空")
    private Integer micatapeId;
    /**
     * 绝缘ID
     */

    @Schema(description = "绝缘ID")
    @NotNull(message = "绝缘ID不得为空")
    private Integer insulationId;
    /**
     * 填充物ID
     */

    @Schema(description = "填充物ID")
    @NotNull(message = "填充物ID不得为空")
    private Integer infillingId;
    /**
     * 包带ID
     */

    @Schema(description = "包带ID")
    @NotNull(message = "包带ID不得为空")
    private Integer bagId;
    /**
     * 屏蔽ID
     */
    @Schema(description = "屏蔽ID")
    @NotNull(message = "屏蔽ID不得为空")
    private Integer shieldId;
    /**
     * 钢带ID
     */
    @Schema(description = "钢带ID")
    @NotNull(message = "钢带ID不得为空")
    private Integer steelBandId;
    /**
     * 护套ID
     */
    @Schema(description = "护套ID")
    @NotNull(message = "护套ID不得为空")
    private Integer sheathId;


    @Schema(description = "介绍")
    private String description;
}
