package org.jeecg.modules.cable.entity.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcCustomer {

    @Schema(description = "主键ID")
    private Integer eccuId;//主键ID

    @Schema(description = "公司ID")
    private Integer ecCompanyId;//公司ID

    @Schema(description = "用户ID")
    private Integer ecuId;//用户ID

    @Schema(description = "客户名称")
    private String customerName;//客户名称

    @Schema(description = "客户手机号")
    private String customerPhone;//客户手机号

    @Schema(description = "客户账号")
    private String accountNumber;//客户账号

    @Schema(description = "收货人名称")
    private String receiveName;//收货人名称

    @Schema(description = "收货人手机号")
    private String receivePhone;//收货人手机号

    @Schema(description = "收货人地址")
    private String receiveAddress;//收货人地址

    @Schema(description = "发票收货人名称")
    private String billName;//发票收货人名称

    @Schema(description = "发票收货人手机号")
    private String billPhone;//发票收货人手机号

    @Schema(description = "发票收货人地址")
    private String billAddress;//发票收货人地址

    @Schema(description = "公司名称")
    private String companyName;//公司名称

    @Schema(description = "税号")
    private String taxAccount;//税号

    @Schema(description = "公司地址")
    private String address;//公司地址

    @Schema(description = "公司电话")
    private String companyPhone;//公司电话

    @Schema(description = "银行名称")
    private String bankName;//银行名称

    @Schema(description = "银行账号")
    private String bankAccount;//银行账号

    @Schema(description = "邮箱")
    private String email;//邮箱

    @Schema(description = "备注")
    private String description;//备注

    private Integer startNumber;

    private Integer pageNumber;
}
