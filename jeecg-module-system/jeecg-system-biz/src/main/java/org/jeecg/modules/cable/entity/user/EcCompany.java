package org.jeecg.modules.cable.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcCompany {
    private Integer ecCompanyId;//主键ID

    private Integer cartId;//对应管理员ID

    private Boolean startType;//是否启用

    private Integer companyType;//公司类型

    private String companyName;//公司名称

    private String addressDesc;//公司地址

    private BigDecimal money;//账户金额

    private BigDecimal moneyFrozen;//冻结金额

    private BigDecimal moneyUse;//可用金额

    private BigDecimal moneyConsume;//消费金额

    private BigDecimal rechargeTotal;//充值总额

    private BigDecimal withdrawTotal;//提现总额、

    private String description;//备注

    private Long endTime;//截止日期

    private Long addTime;//添加时间

    private Long updateTime;//修改时间
}
