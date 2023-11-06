package org.jeecg.modules.cable.entity.hand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyBill {
    private String companyName;//公司名称

    private String taxAccount;//税号

    private String address;//公司地址

    private String companyPhone;//公司电话

    private String bankName;//银行名称

    private String bankAccount;//银行账号

    private String email;//邮箱
}
