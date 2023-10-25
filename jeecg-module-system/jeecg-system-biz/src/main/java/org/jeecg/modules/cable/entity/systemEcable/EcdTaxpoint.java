package org.jeecg.modules.cable.entity.systemEcable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.userCommon.EcduTaxpoint;

import java.math.BigDecimal;

@Schema(description = "系统税点")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcdTaxpoint {

    @Schema(description = "主键ID")
    private Integer ecdtId;//主键ID

    @Schema(description = "管理员ID")
    private Integer ecaId;//管理员ID

    @Schema(description = "管理员名称")
    private String ecaName;//管理员名称

    @Schema(description = "是否启用")
    private Boolean startType;//是否启用

    @Schema(description = "序号")
    private Integer sortId;//序号

    @Schema(description = "税点名称")
    private String pointName;//税点名称

    @Schema(description = "普票税点")
    private BigDecimal percentCommon;//普票税点

    @Schema(description = "专票税点")
    private BigDecimal percentSpecial;//专票税点

    @Schema(description = "备注")
    private String description;//备注

    @Schema(description = "添加时间")
    private Long addTime;//添加时间

    @Schema(description = "更新时间")
    private Long updateTime;//更新时间

    @Schema(description = "用户发票税点")
    private EcduTaxpoint ecduTaxpoint;//用户发票税点

    @Schema(description = "公司ID")
    private Integer ecCompanyId;//公司ID
}
