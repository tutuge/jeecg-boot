package org.jeecg.modules.cable.entity.userCommon;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "用户公司")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcduCompany {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecducId;//主键ID

    @Schema(description = "公司ID")
    private Integer ecCompanyId;//公司ID

    @Schema(description = "是否启用")
    private Boolean startType;//是否启用

    @Schema(description = "序号")
    private Integer sortId;//序号

    @Schema(description = "是否默认")
    private Boolean defaultType;//是否默认

    @Schema(description = "公司简称")
    private String abbreviation;//公司简称

    @Schema(description = "公司全称")
    private String fullName;//公司全称

    @Schema(description = "logo图片")
    private String logoImg;//logo图片

    @Schema(description = "印章图片")
    private String sealImg;//印章图片

    @Schema(description = "发票税点类型")
    private Integer billPercentType;//发票税点类型

    @Schema(description = "备注")
    private String description;//备注

    @Schema(description = "图片")
    @TableField(exist = false)
    private EcducImages ecducImages;

    @Schema(description = "图片位置")
    @TableField(exist = false)
    private EcduciPosition ecduciPosition;
}
