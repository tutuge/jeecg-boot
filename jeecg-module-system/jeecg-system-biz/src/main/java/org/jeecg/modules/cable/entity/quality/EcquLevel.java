package org.jeecg.modules.cable.entity.quality;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;
import org.jeecg.modules.cable.entity.systemEcable.EcbConductor;
import org.jeecg.modules.cable.entity.userEcable.EcbuConductor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcquLevel {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecqulId;// 主键ID

    @Schema(description = "型号ID")
    private Integer ecsId;// 丝型号ID

    @Schema(description = "用户导体ID")
    private Integer ecbucId;// 用户导体ID

    @Schema(description = "公司ID")
    private Integer ecCompanyId;// 公司ID

    @Schema(description = "是否启用")
    private Boolean startType;// 是否启用

    @Schema(description = "序号")
    private Integer sortId;// 序号

    @Schema(description = "序号")
    private Integer powerId;//

    @Schema(description = "自定义名称")
    private String name;// 自定义名称

    @Schema(description = "是否默认质量等级")
    private Boolean defaultType;// 是否默认质量等级

    @Schema(description = "备注")
    private String description;// 备注

    @Schema(description = "丝型号")
    @TableField(exist = false)
    private EcSilk ecSilk;// 丝型号

    @Schema(description = "用户导体")
    @TableField(exist = false)
    private EcbuConductor ecbuConductor;// 用户导体

    @Schema(description = "系统导体")
    @TableField(exist = false)
    private EcbConductor ecbConductor;// 系统导体
}
