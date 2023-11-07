package org.jeecg.modules.cable.entity.userEcable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.common.validate.AddGroup;

import java.util.Date;

@Schema(description = "用户型号类型")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuSilk {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecusId;// 主键ID

    @Schema(description = "用户ID")
    private Integer ecuId;// 用户ID

    @Schema(description = "公司ID")
    private Integer companyId;

    @Schema(description = "是否启用")
    private Boolean startType;// 是否启用

    @Schema(description = "排序")
    private Integer sortId;// 序号

    @Schema(description = "简称")
    @NotBlank(message = "简称不得为空", groups = {AddGroup.class})
    private String abbreviation;// 简称

    @Schema(description = "全称")
    @NotBlank(message = "全称不得为空", groups = {AddGroup.class})
    private String fullName;// 全称

    @Schema(description = "介绍")
    private String description;//

    @Schema(description = "添加时间")
    private Date addTime;// 添加时间

    @Schema(description = "更新时间")
    private Date updateTime;// 更新时间
}
