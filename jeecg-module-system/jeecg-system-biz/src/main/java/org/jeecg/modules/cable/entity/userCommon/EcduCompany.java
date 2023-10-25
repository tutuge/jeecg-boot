package org.jeecg.modules.cable.entity.userCommon;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcduCompany {

    @Schema(description = "主键ID")
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
    private EcducImages ecducImages;

    @Schema(description = "图片位置")
    private EcduciPosition ecduciPosition;
}
