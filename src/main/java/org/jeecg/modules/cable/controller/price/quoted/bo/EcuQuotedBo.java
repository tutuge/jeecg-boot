package org.jeecg.modules.cable.controller.price.quoted.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "报价单")
public class EcuQuotedBo {

    @Schema(description = "主键ID")
    @NotNull(message = "报价单ID不得为空")
    private Integer ecuqId;//主键ID
//
//    private Integer ecCompanyId;//公司ID

    @Schema(description = "快递ID")
    private Integer ecbudId;//快递ID

    @Schema(description = "发货地仓库")
    private Integer deliveryStoreId;//发货地仓库

    @Schema(description = "运费除以")
    private BigDecimal deliveryDivide;//运费除以

    @Schema(description = "运费加减")
    private BigDecimal deliveryAdd;//运费加减
//
//    private String serialNumber;//流水号

    @Schema(description = "交易类型 1 生成中 2 已成交 3 保存备用")
    private Integer tradeType;//交易类型 1 生成中 2 已成交 3 保存备用

    @Schema(description = "报价单名称")
    private String name;//报价单名称

    @Schema(description = "省名称")
    private String provinceName;//省名称

    @Schema(description = "省ID")
    private Integer ecpId;//省ID

    @Schema(description = "发票类型 1 不开发票 2 普通发票 3 专用发票")
    private Integer billPercentType;//发票类型 1 不开发票 2 普通发票 3 专用发票

    @Schema(description = "平台公司ID")
    private Integer ecbupId;//平台公司ID

    @Schema(description = "单位加价金额")
    private BigDecimal unitPriceAdd;//单位加价金额

    @Schema(description = "加价百分比")
    private BigDecimal addPricePercent;//加价百分比

    @Schema(description = "导体折扣（百分制）")
    private BigDecimal reduction = BigDecimal.ZERO;

    @Schema(description = "用户导体ID")
    private Integer ecbucId;

    @Schema(description = "导体单价")
    private BigDecimal cunitPrice = BigDecimal.ZERO;// 导体单价

    /**
     * 卖价就是我们之前计算的税前单价，出厂价是去掉仓库的利润点，按照仓库原价
     */
    @Schema(description = " 价格类型 1卖价 2 出厂价")
    private Integer priceType;


    @Schema(description = "关联公司")
    private String companyName;//关联公司
}
