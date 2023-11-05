package org.jeecg.modules.cable.entity.price;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.modules.cable.entity.user.EcCustomer;

import java.math.BigDecimal;

@Schema(description = "报价单")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuQuoted {

    @Schema(description = "主键ID")
    private Integer ecuqId;// 主键ID

    @Schema(description = "公司ID")
    private Integer ecCompanyId;// 公司ID

    @Schema(description = "快递ID")
    private Integer ecbudId;// 快递ID

    @Schema(description = "用户ID")
    private Integer ecuId;// 用户ID

    @Schema(description = "客户ID")
    private Integer eccuId;// 客户ID

    @Schema(description = "发货地仓库")
    private Integer deliveryStoreId;// 发货地仓库

    @Schema(description = "运费除以")
    private BigDecimal deliveryDivide;// 运费除以

    @Schema(description = "运费加减")
    private BigDecimal deliveryAdd;// 运费加减


    @Schema(description = "流水号")
    private String serialNumber;// 流水号

    @Schema(description = "交易类型 1 生成中 2 已成交 3 保存备用")
    private Integer tradeType;// 交易类型 1 生成中 2 已成交 3 保存备用

    @Schema(description = "报价单名称")
    private String name;// 报价单名称

    @Schema(description = "省ID")
    private Integer ecpId;// 省ID

    @Schema(description = "省名称")
    private String provinceName;// 省名称

    @Schema(description = "总重量")
    private BigDecimal totalWeight;// 总重量

    @Schema(description = "总金额")
    private BigDecimal totalMoney;// 总金额

    @Schema(description = "快递金额")
    private BigDecimal deliveryMoney;// 快递金额

    @Schema(description = " 发票类型 1 不开发票 2 普通发票 3 专用发票")
    private Integer billPercentType;// 发票类型 1 不开发票 2 普通发票 3 专用发票

    @Schema(description = " 平台公司ID")
    private Integer ecbupId;// 平台公司ID

    @Schema(description = " 开票公司")
    private String billName;// 开票公司

    @Schema(description = " 添加时间")
    private Long addTime;// 添加时间

    @Schema(description = " 完成时间")
    private Long completeTime;// 完成时间

    @Schema(description = " 不开发票总计")
    private BigDecimal nbuptMoney;// 不开发票总计

    @Schema(description = " 开发票总计")
    private BigDecimal buptMoney;// 开发票总计

    @Schema(description = " 单位加价金额")
    private BigDecimal unitPriceAdd;// 单位加价金额

    @Schema(description = " 加价百分比")
    private BigDecimal addPricePercent;// 加价百分比

    @Schema(description = " 备注标题")
    private String totalTitle;// 备注标题

    @Schema(description = " 总备注")
    private String totalDesc;// 总备注

    @Schema(description = " 用户")
    private EcUser ecUser;// 用户

    @Schema(description = " 客户")
    private EcCustomer ecCustomer;// 客户

    private Integer startNumber;

    private Integer pageNumber;

    @Schema(description = " 客户")
    private String ecUsername;// 用户名称用于模糊搜索

    @Schema(description = " 客户名称")
    private String customerName;// 客户名称

    @Schema(description = " 客户手机")
    private String customerPhone;// 客户手机

    @Schema(description = " 客户账号")
    private String accountNumber;// 客户账号

    @Schema(description = " 关联公司")
    private String companyName;// 关联公司

    @Schema(description = " 添加时间开始时间")
    private Long addStartTime;// 添加时间开始时间

    @Schema(description = " 添加时间结束时间")
    private Long addEndTime;// 添加时间结束时间

    @Schema(description = " 完成时间开始时间")
    private Long completeStartTime;// 完成时间开始时间

    @Schema(description = " 完成时间结束时间")
    private Long completeEndTime;// 完成时间结束时间
}
