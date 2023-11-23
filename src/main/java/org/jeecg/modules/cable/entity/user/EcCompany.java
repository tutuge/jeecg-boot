package org.jeecg.modules.cable.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "客户公司信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcCompany {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecCompanyId;//主键ID

    @Schema(description = "对应管理员ID")
    private Integer cartId;//对应管理员ID

    @Schema(description = "是否启用")
    private Boolean startType;//是否启用

    @Schema(description = "公司类型")
    private Integer companyType;//公司类型

    @Schema(description = "公司名称")
    private String companyName;

    @Schema(description = "公司地址")
    private String addressDesc;

    @Schema(description = "账户金额")
    private BigDecimal money;

    @Schema(description = "冻结金额")
    private BigDecimal moneyFrozen;

    @Schema(description = "可用金额")
    private BigDecimal moneyUse;

    @Schema(description = "消费金额")
    private BigDecimal moneyConsume;

    @Schema(description = "充值总额")
    private BigDecimal rechargeTotal;

    @Schema(description = "提现总额")
    private BigDecimal withdrawTotal;

    @Schema(description = "备注")
    private String description;

    @Schema(description = "截止日期")
    private Long endTime;

    @Schema(description = "添加时间")
    private Long addTime;

    @Schema(description = "修改时间")
    private Long updateTime;
}
