package org.jeecg.modules.cable.controller.userCommon.position.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "利润")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcProfitEditBo {

    @Schema(description = "主键ID")
    private Integer ecpId;// 主键ID

    @Schema(description = "公司ID")
    private Integer ecCompanyId;// 公司ID

    @Schema(description = "利润名称")
    private String profitName;// 利润名称

    @Schema(description = "是否启用")
    private Boolean startType;// 是否启用

    @Schema(description = "质量等级ID")
    private Integer ecqulId;// 质量等级ID

    @Schema(description = "型号")
    @NotNull(message = "型号不得为空")
    private String silkName;// 丝型号

    @Schema(description = "平方数")
    @NotNull(message = "平方数不得为空")
    private String area;// 平方数

    @Schema(description = "芯数字符串")
    @NotNull(message = "芯数不得为空")
    private String coreStr;// 芯数字符串

    @Schema(description = "销售数量 起")
    @NotNull(message = "销售数量不得为空")
    private Integer startNumber;// 销售数量 起

    @Schema(description = "销售数量 止")
    @NotNull(message = "销售数量不得为空")
    private Integer endNumber;// 销售数量 止

    @Schema(description = "单位")
    @NotNull(message = "单位不得为空")
    private Integer ecbuluId;// 单位

    @Schema(description = "单价 开始")
    @NotNull(message = "单价不得为空")
    private BigDecimal startUnitPrice;// 单价 开始

    @Schema(description = "单价 结束")
    @NotNull(message = "单价不得为空")
    private BigDecimal endUnitPrice;// 单价 结束

    @Schema(description = "利润")
    @NotNull(message = "利润不得为空")
    private BigDecimal profit;// 利润


    @Schema(description = "备注")
    private String description;// 备注

    private Integer pageNum;

    private Integer pageSize;
}
