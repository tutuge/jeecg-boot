package org.jeecg.modules.cable.entity.userQuality;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.userEcable.EcuSilk;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "质量等级信息")
public class EcquLevel {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecqulId;

    @Schema(description = "用户的型号系列ID")
    private Integer ecusId;

    @Schema(description = "用户导体ID")
    private Integer ecbucId;

    @Schema(description = "公司ID")
    private Integer ecCompanyId;

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

    @Schema(description = "添加时间")
    private Date createTime;

    @Schema(description = "修改时间")
    private Date updateTime;


    @Schema(description = "型号类型")
    @TableField(exist = false)
    private EcuSilk ecuSilk;


    @Schema(description = "用户导体")
    @TableField(exist = false)
    private String conductorName;
    //
    //@Schema(description = "系统导体")
    //@TableField(exist = false)
    //private EcbMaterials ecbMaterials;
}
