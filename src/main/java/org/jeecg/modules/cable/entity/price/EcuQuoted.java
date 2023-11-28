package org.jeecg.modules.cable.entity.price;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.modules.cable.entity.user.EcCustomer;

import java.math.BigDecimal;
import java.util.Date;

@Schema(description = "报价单")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuQuoted {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecuqId;

    @Schema(description = "公司ID")
    private Integer ecCompanyId;

    @Schema(description = "快递ID")
    private Integer ecbudId;

    @Schema(description = "用户ID")
    private Integer ecuId;

    @Schema(description = "客户ID")
    private Integer eccuId;

    @Schema(description = "发货地仓库")
    private Integer deliveryStoreId;

    @Schema(description = "运费除以")
    private BigDecimal deliveryDivide;

    @Schema(description = "运费加减")
    private BigDecimal deliveryAdd;


    @Schema(description = "流水号")
    private String serialNumber;

    @Schema(description = "交易类型 1 生成中 2 已成交 3 保存备用")
    private Integer tradeType;

    @Schema(description = "报价单名称")
    private String name;

    @Schema(description = "省ID")
    private Integer ecpId;

    @Schema(description = "省名称")
    private String provinceName;

    @Schema(description = "总重量")
    private BigDecimal totalWeight;

    @Schema(description = "总金额")
    private BigDecimal totalMoney;

    @Schema(description = "快递金额")
    private BigDecimal deliveryMoney;

    @Schema(description = " 发票类型 1 不开发票 2 普通发票 3 专用发票")
    private Integer billPercentType;

    @Schema(description = " 平台公司ID")
    private Integer ecbupId;

    @Schema(description = " 开票公司")
    private String billName;

    @Schema(description = " 添加时间")
    private Date addTime;

    @Schema(description = " 完成时间")
    private Date completeTime;

    @Schema(description = " 不开发票总计")
    private BigDecimal nbuptMoney;

    @Schema(description = " 开发票总计")
    private BigDecimal buptMoney;

    @Schema(description = " 单位加价金额")
    private BigDecimal unitPriceAdd;

    @Schema(description = " 加价百分比")
    private BigDecimal addPricePercent;

    /**
     * 卖价就是我们之前计算的税前单价，出厂价是去掉仓库的利润点，按照仓库原价
     */
    @Schema(description = " 价格类型 1卖价 2 出厂价")
    private Integer priceType;


    @Schema(description = "导体折扣")
    private BigDecimal reduction;

    @Schema(description = " 备注标题")
    private String totalTitle;

    @Schema(description = " 总备注")
    private String totalDesc;

    @Schema(description = " 用户")
    @TableField(exist = false)
    private EcUser ecUser;

    @Schema(description = " 客户")
    @TableField(exist = false)
    private EcCustomer ecCustomer;

    @TableField(exist = false)
    private Integer startNumber;

    @TableField(exist = false)
    private Integer pageNumber;

    @Schema(description = " 客户")
    @TableField(exist = false)
    private String ecUsername;

    @Schema(description = " 客户名称")
    @TableField(exist = false)
    private String customerName;

    @Schema(description = " 客户手机")
    @TableField(exist = false)
    private String customerPhone;

    @Schema(description = " 客户账号")
    @TableField(exist = false)
    private String accountNumber;

    @Schema(description = " 关联公司")
    @TableField(exist = false)
    private String companyName;

    @Schema(description = " 添加时间开始时间")
    @TableField(exist = false)
    private Date addStartTime;

    @Schema(description = " 添加时间结束时间")
    @TableField(exist = false)
    private Date addEndTime;

    @Schema(description = " 完成时间开始时间")
    @TableField(exist = false)
    private Date completeStartTime;

    @Schema(description = " 完成时间结束时间")
    @TableField(exist = false)
    private Date completeEndTime;
}
