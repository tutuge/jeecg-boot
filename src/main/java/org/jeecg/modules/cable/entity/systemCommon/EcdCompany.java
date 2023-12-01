package org.jeecg.modules.cable.entity.systemCommon;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "系统公司")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcdCompany {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecdcId;

    //@Schema(description = "公司ID")
    //private Integer ecCompanyId;

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

    @Schema(description = "发票税点类型")
    private Integer billPercentType;

    @Schema(description = "备注")
    private String description;
}
