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

    ///**
    // * 导体ID
    // */
    //@Excel(name = "导体ID", width = 15)
    //@Schema(description = "导体ID")
    // private Integer conductorId;
    ///**
    // * 云母带ID
    // */
    //@Excel(name = "云母带ID", width = 15)
    //@Schema(description = "云母带ID")
    // private Integer micatapeId;
    ///**
    // * 绝缘ID
    // */
    //@Excel(name = "绝缘ID", width = 15)
    //@Schema(description = "绝缘ID")
    // private Integer insulationId;
    ///**
    // * 填充物ID
    // */
    //@Excel(name = "填充物ID", width = 15)
    //@Schema(description = "填充物ID")
    // private Integer infillingId;
    ///**
    // * 包带ID
    // */
    //@Excel(name = "包带ID", width = 15)
    //@Schema(description = "包带ID")
    // private Integer bagId;
    ///**
    // * 屏蔽ID
    // */
    //@Excel(name = "屏蔽ID", width = 15)
    //@Schema(description = "屏蔽ID")
    // private Integer shieldId;
    ///**
    // * 钢带ID
    // */
    //@Excel(name = "钢带ID", width = 15)
    //@Schema(description = "钢带ID")
    // private Integer steelBandId;
    ///**
    // * 护套ID
    // */
    //@Excel(name = "护套ID", width = 15)
    //@Schema(description = "护套ID")
    // private Integer sheathId;


    @Schema(description = "介绍")
    private String description;//

    @Schema(description = "添加时间")
    private Date addTime;// 添加时间

    @Schema(description = "更新时间")
    private Date updateTime;// 更新时间
}
