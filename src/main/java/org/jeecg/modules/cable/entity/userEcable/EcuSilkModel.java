package org.jeecg.modules.cable.entity.userEcable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.common.validate.AddGroup;
import org.jeecg.poi.excel.annotation.Excel;

import java.util.Date;

@Schema(description = "用户型号")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuSilkModel {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecusmId;

    @Schema(description = "型号类型ID")
    private Integer ecuSilkId;

    @Schema(description = "用户ID")
    private Integer ecuId;

    @Schema(description = "公司ID")
    private Integer companyId;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "排序")
    private Integer sortId;

    @Schema(description = "简称")
    @NotBlank(message = "简称不得为空", groups = {AddGroup.class})
    private String abbreviation;

    @Schema(description = "全称")
    @NotBlank(message = "全称不得为空", groups = {AddGroup.class})
    private String fullName;

    @Excel(name = "导体", width = 15)
    @Schema(description = "导体")
    private Boolean conductor;

    @Excel(name = "云母带", width = 15)
    @Schema(description = "云母带")
    private Boolean micaTape;

    @Excel(name = "绝缘", width = 15)
    @Schema(description = "绝缘")
    private Boolean insulation;

    @Excel(name = "填充物", width = 15)
    @Schema(description = "填充物")
    private Boolean infilling;

    @Excel(name = "包带", width = 15)
    @Schema(description = "包带")
    private Boolean bag;

    @Excel(name = "屏蔽", width = 15)
    @Schema(description = "屏蔽")
    private Boolean shield;

    @Excel(name = "钢带", width = 15)
    @Schema(description = "钢带")
    private Boolean steelBand;

    @Excel(name = "护套", width = 15)
    @Schema(description = "护套")
    private Boolean sheath;

    @Schema(description = "介绍")
    private String description;//

    @Schema(description = "添加时间")
    private Date addTime;// 添加时间

    @Schema(description = "更新时间")
    private Date updateTime;// 更新时间
}
