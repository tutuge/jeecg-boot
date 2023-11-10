package org.jeecg.modules.cable.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "公司金额信息")
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
    private String companyName;//公司名称

    @Schema(description = "公司地址")
    private String addressDesc;//公司地址

    @Schema(description = "账户金额")
    private BigDecimal money;//账户金额

    @Schema(description = "冻结金额")
    private BigDecimal moneyFrozen;//冻结金额

    @Schema(description = "可用金额")
    private BigDecimal moneyUse;//可用金额

    @Schema(description = "消费金额")
    private BigDecimal moneyConsume;//消费金额

    @Schema(description = "充值总额")
    private BigDecimal rechargeTotal;//充值总额

    @Schema(description = "提现总额")
    private BigDecimal withdrawTotal;//提现总额

    @Schema(description = "备注")
    private String description;//备注

    @Schema(description = "截止日期")
    private Long endTime;//截止日期

    @Schema(description = "添加时间")
    private Long addTime;//添加时间

    @Schema(description = "修改时间")
    private Long updateTime;//修改时间
}
