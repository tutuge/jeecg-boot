package org.jeecg.modules.cable.entity.userCommon;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Schema(description = "用户设置的公司数据，用于报价单的抬头")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcduCompany {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecducId;

    @Schema(description = "公司ID")
    private Integer ecCompanyId;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "序号")
    private Integer sortId;

    @Schema(description = "是否默认")
    private Boolean defaultType;

    @Schema(description = "公司简称")
    private String abbreviation;

    @Schema(description = "公司全称")
    private String fullName;

    @Schema(description = "logo图片")
    private String logoImg;

    @Schema(description = "印章图片")
    private String sealImg;

    @Schema(description = "fa类型")
    private Integer billPercentType;

    @Schema(description = "备注")
    private String description;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "修改时间")
    private Date updateTime;

    @Schema(description = "图片")
    @TableField(exist = false)
    private EcducImages ecducImages;

    @Schema(description = "图片位置")
    @TableField(exist = false)
    private EcduciPosition ecduciPosition;
}
