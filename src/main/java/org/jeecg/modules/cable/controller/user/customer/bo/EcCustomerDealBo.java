package org.jeecg.modules.cable.controller.user.customer.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcCustomerDealBo {

    @Schema(description = "主键ID")
    private Integer eccuId;

    @Schema(description = "客户名称")
    @NotBlank(message = "客户名称不得为空")
    private String customerName;

    @Schema(description = "客户手机号")
    @NotBlank(message = "客户手机号不得为空")
    private String customerPhone;

    @Schema(description = "客户账号")
    @NotBlank(message = "客户账号不得为空")
    private String accountNumber;

    @Schema(description = "收货人名称")
    @NotBlank(message = "收货人名称不得为空")
    private String receiveName;

    @Schema(description = "收货人手机号")
    @NotBlank(message = "收货人手机号不得为空")
    private String receivePhone;

    @Schema(description = "收货人地址")
    @NotBlank(message = "收货人地址不得为空")
    private String receiveAddress;

    @Schema(description = "发票收货人名称")
    @NotBlank(message = "发票收货人名称不得为空")
    private String billName;

    @Schema(description = "发票收货人手机号")
    @NotBlank(message = "发票收货人手机号不得为空")
    private String billPhone;

    @Schema(description = "发票收货人地址")
    @NotBlank(message = "发票收货人地址不得为空")
    private String billAddress;

    @Schema(description = "公司名称")
    @NotBlank(message = "公司名称不得为空")
    private String companyName;

    @Schema(description = "税号")
    @NotBlank(message = "税号不得为空")
    private String taxAccount;

    @Schema(description = "公司地址")
    @NotBlank(message = "公司地址不得为空")
    private String address;

    @Schema(description = "公司电话")
    @NotBlank(message = "公司电话不得为空")
    private String companyPhone;

    @Schema(description = "银行名称")
    @NotBlank(message = "银行名称不得为空")
    private String bankName;

    @Schema(description = "银行账号")
    @NotBlank(message = "银行账号不得为空")
    private String bankAccount;

    @Schema(description = "付款账号")
    private String payAccount;

    @Schema(description = "付款平台")
    private String payPlatform;

    @Schema(description = "其他信息")
    private String other;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "备注")
    private String description;
}
