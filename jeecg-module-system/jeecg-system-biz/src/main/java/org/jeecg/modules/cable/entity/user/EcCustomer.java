package org.jeecg.modules.cable.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcCustomer {
    private Integer eccuId;//主键ID

    private Integer ecCompanyId;//公司ID

    private Integer ecuId;//用户ID

    private String customerName;//客户名称

    private String customerPhone;//客户手机号

    private String accountNumber;//客户账号

    private String receiveName;//收货人名称

    private String receivePhone;//收货人手机号

    private String receiveAddress;//收货人地址

    private String billName;//发票收货人名称

    private String billPhone;//发票收货人手机号

    private String billAddress;//发票收货人地址

    private String companyName;//公司名称

    private String taxAccount;//税号

    private String address;//公司地址

    private String companyPhone;//公司电话

    private String bankName;//银行名称

    private String bankAccount;//银行账号

    private String email;//邮箱

    private String description;//备注

    private Integer startNumber;

    private Integer pageNumber;
}
