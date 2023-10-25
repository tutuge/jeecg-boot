package org.jeecg.modules.cable.entity.systemEcable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.userEcable.EcbuShield;

import java.math.BigDecimal;


@Schema(description = "系统屏蔽")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbShield {

    @Schema(description = "主键ID")
    private Integer ecbsId;//主键ID


    @Schema(description = "管理员ID")
    private Integer ecaId;//管理员ID

    @Schema(description = "管理员名称")
    private String ecaName;//管理员名称

    @Schema(description = "是否启用")
    private Boolean startType;//是否启用

    @Schema(description = "序号")
    private Integer sortId;//序号

    @Schema(description = "简称")
    private String abbreviation;//简称

    @Schema(description = "全称")
    private String fullName;//全称

    @Schema(description = "单价")
    private BigDecimal unitPrice;//单价

    @Schema(description = "密度")
    private BigDecimal density;//密度

    @Schema(description = "备注")
    private String description;//备注

    @Schema(description = "添加时间")
    private Long addTime;//添加时间

    @Schema(description = "更新时间")
    private Long updateTime;//更新时间

    @Schema(description = "用户屏蔽")
    private EcbuShield ecbuShield;//用户屏蔽

    @Schema(description = "公司ID")
    private Integer ecCompanyId;//公司ID
}
