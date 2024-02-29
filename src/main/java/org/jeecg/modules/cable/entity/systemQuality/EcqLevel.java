package org.jeecg.modules.cable.entity.systemQuality;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;
import org.jeecg.modules.cable.entity.systemEcable.EcbMaterials;

@Schema(description = "系统质量等级")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcqLevel {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecqlId;

    @Schema(description = "型号系列ID")
    private Integer ecsId;

    @Schema(description = "导体ID")
    private Integer ecbcId;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "序号")
    private Integer sortId;

    @Schema(description = "1是系统默认库 2是自有库")
    private Integer powerId;

    @Schema(description = "自定义名称")
    private String name;

    @Schema(description = "是否默认质量等级")
    private Boolean defaultType;

    @Schema(description = "备注")
    private String description;

    @Schema(description = "型号类型")
    @TableField(exist = false)
    private EcSilk ecSilk;

    @Schema(description = "导体")
    @TableField(exist = false)
    private EcbMaterials ecbMaterials;

    @Schema(description = "用户导体")
    @TableField(exist = false)
    private String conductorName;
}
