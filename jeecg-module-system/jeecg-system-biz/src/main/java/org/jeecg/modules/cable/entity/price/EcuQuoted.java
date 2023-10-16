package org.jeecg.modules.cable.entity.price;

import org.jeecg.modules.cable.entity.user.EcCustomer;
import org.jeecg.modules.cable.entity.user.EcUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuQuoted {
    private Integer ecuqId;//主键ID

    private Integer ecCompanyId;//公司ID

    private Integer ecbudId;//快递ID

    private Integer ecuId;//用户ID

    private Integer eccuId;//客户ID

    private Integer deliveryStoreId;//发货地仓库

    private BigDecimal deliveryDivide;//运费除以

    private BigDecimal deliveryAdd;//运费加减

    private String serialNumber;//流水号

    private Integer tradeType;//交易类型 1 生成中 2 已成交 3 保存备用

    private String name;//报价单名称

    private Integer ecpId;//省ID

    private String provinceName;//省名称

    private BigDecimal totalWeight;//总重量

    private BigDecimal totalMoney;//总金额

    private BigDecimal deliveryMoney;//快递金额

    private Integer billPercentType;//发票类型 1 不开发票 2 普通发票 3 专用发票

    private Integer ecbupId;//平台公司ID

    private String billName;//开票公司

    private Long addTime;//添加时间

    private Long completeTime;//完成时间

    private BigDecimal nbuptMoney;//不开发票总计

    private BigDecimal buptMoney;//开发票总计

    private BigDecimal unitPriceAdd;//单位加价金额

    private BigDecimal addPricePercent;//加价百分比

    private String totalTitle;//备注标题

    private String totalDesc;//总备注

    private EcUser ecUser;//用户

    private EcCustomer ecCustomer;//客户

    private Integer startNumber;

    private Integer pageNumber;

    private String ecUsername;//用户名称用于模糊搜索

    private String customerName;//客户名称

    private String customerPhone;//客户手机

    private String accountNumber;//客户账号

    private String companyName;//关联公司

    private Long addStartTime;//添加时间开始时间

    private Long addEndTime;//添加时间结束时间

    private Long completeStartTime;//完成时间开始时间

    private Long completeEndTime;//完成时间结束时间
}
