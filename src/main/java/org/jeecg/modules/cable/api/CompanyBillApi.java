package org.jeecg.modules.cable.api;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.modules.cable.entity.hand.CompanyBill;
import org.jeecg.modules.cable.tools.PhoneUtils;
import org.jeecg.modules.cable.tools.StringTools;

@Slf4j
public class CompanyBillApi {

    // getCompanyBill
    public static CompanyBill getCompanyBill(String text) {
        text = StringUtils.deleteWhitespace(text);
        text = text.replaceAll(":", "");
        text = text.replaceAll("：", "");
        text = text.replaceAll(" ", "");
        text = text.replaceAll("－", "-");
        log.info("text + " + text);
        CompanyBill companyBill = new CompanyBill();
        String companyName = getCompanyName(text);// 公司名称
        companyBill.setCompanyName(companyName);
        String taxAccount = getTaxAccount(text);// 税号
        companyBill.setTaxAccount(taxAccount);
        String address = getAddress(text);// 地址
        companyBill.setAddress(address);
        String companyPhone = getCompanyPhone(text);// 公司电话
        companyBill.setCompanyPhone(companyPhone);
        String bankName = getBankName(text);// 银行名称
        companyBill.setBankName(bankName);
        String bankAccount = getBankAccount(text);// 银行账号
        companyBill.setBankAccount(bankAccount);
        String email = getEmail(text);
        companyBill.setEmail(email);
        return companyBill;
    }

    // getCompanyName
    public static String getCompanyName(String text) {
        String companyName = "";
        if (text.contains("名称")) {
            companyName = StringUtils.substringAfter(text, "名称");
            companyName = getCompanyNameStr(companyName);
        }
        if (!text.contains("名称") && text.contains("单位")) {
            companyName = StringUtils.substringAfter(text, "单位");
            companyName = getCompanyNameStr(companyName);

        }
        if (!text.contains("名称") && !text.contains("单位") && text.contains("公司全称")) {
            companyName = StringUtils.substringAfter(text, "公司全称");
            companyName = getCompanyNameStr(companyName);
        }
        if (!text.contains("名称")
                && !text.contains("单位")
                && !text.contains("公司全称")
                && text.contains("纳税人识别号")) {
            companyName = StringUtils.substringBefore(text, "纳税人识别号");
        }
        // log.info("companyName + " + companyName);
        return companyName;
    }

    // getTaxAccount
    public static String getTaxAccount(String text) {
        String taxAccount = "";
        if (text.contains("税号")) {
            taxAccount = StringUtils.substringAfter(text, "税号");
            taxAccount = getTaxAccountStr(taxAccount);
        }
        if (!text.contains("税号") && text.contains("统一社会信用代码")) {
            taxAccount = StringUtils.substringAfter(text, "统一社会信用代码");
            taxAccount = getTaxAccountStr(taxAccount);
        }
        if (!text.contains("税号")
                && !text.contains("统一社会信用代码")
                && text.contains("统一社会代码")) {
            taxAccount = StringUtils.substringAfter(text, "统一社会代码");
            taxAccount = getTaxAccountStr(taxAccount);
        }
        if (!text.contains("税号")
                && !text.contains("统一社会信用代码")
                && !text.contains("统一社会代码")
                && text.contains("纳税人识别号")) {
            taxAccount = StringUtils.substringAfter(text, "纳税人识别号");
            taxAccount = getTaxAccountStr(taxAccount);
        }
        return taxAccount;
    }

    // getAddress
    public static String getAddress(String text) {
        String address = "";
        if (text.contains("单位地址")) {
            address = StringUtils.substringAfter(text, "单位地址");
            // log.info("address + " + address);
            address = getAddressStr(address);
        }
        if (!text.contains("单位地址") && text.contains("地址、电话")) {
            address = StringUtils.substringAfter(text, "地址、电话");
            // log.info("address + " + address);
            address = getAddressStr(address);
        }
        if (!text.contains("单位地址") && !text.contains("地址、电话") && text.contains("地址电话")) {
            address = StringUtils.substringAfter(text, "地址电话");
            // log.info("address 地址电话 + " + address);
            address = getAddressStr(address);
        }
        if (!text.contains("单位地址")
                && !text.contains("地址、电话")
                && !text.contains("地址电话")
                && text.contains("公司注册地址")) {
            address = StringUtils.substringAfter(text, "公司注册地址");
            // log.info("address + " + address);
            address = getAddressStr(address);
        }
        if (!text.contains("单位地址")
                && !text.contains("地址、电话")
                && !text.contains("地址电话")
                && !text.contains("公司注册地址")
                && text.contains("地址，电话")) {
            address = StringUtils.substringAfter(text, "地址，电话");
            // log.info("address + " + address);
            address = getAddressStr(address);
        }
        if (!text.contains("单位地址")
                && !text.contains("地址、电话")
                && !text.contains("地址电话")
                && !text.contains("公司注册地址")
                && !text.contains("地址，电话")
                && text.contains("地址")) {
            address = StringUtils.substringAfter(text, "地址");
            // log.info("address + " + address);
            address = getAddressStr(address);
        }
        // log.info("address + " + address);
        return address;
    }

    // getCompanyPhone
    public static String getCompanyPhone(String text) {
        String companyPhone = "";
        if (text.contains("电话号码")) {
            companyPhone = StringUtils.substringAfter(text, "电话号码");
            companyPhone = getCompanyPhoneStr(companyPhone);
        }
        if (!text.contains("电话号码") && text.contains("电话")) {
            companyPhone = StringUtils.substringAfter(text, "电话");
            companyPhone = getCompanyPhoneStr(companyPhone);
        }
        if (!text.contains("电话号码")
                && !text.contains("电话")
                && text.contains("公司注册地址")) {
            companyPhone = StringUtils.substringAfter(text, "公司注册地址");
            companyPhone = getCompanyPhoneStr(companyPhone);
        }
        // log.info("companyPhone + " + companyPhone);
        return companyPhone;
    }

    // getBankName
    public static String getBankName(String text) {
        String bankName = "";
        if (text.contains("开户银行")) {
            bankName = StringUtils.substringAfter(text, "开户银行");
            // log.info("bankName + " + bankName);
            bankName = getBankNameStr(bankName);
        }
        if (text.contains("开户行及账号")) {
            bankName = StringUtils.substringAfter(text, "开户行及账号");
            // log.info("bankName + " + bankName);
            bankName = getBankNameStr(bankName);
        }
        if (!text.contains("开户银行") && !text.contains("开户行及账号") && text.contains("开户行")) {
            bankName = StringUtils.substringAfter(text, "开户行");
            // log.info("bankName + " + bankName);
            bankName = getBankNameStr(bankName);
        }
        // log.info("bankName + " + bankName);
        return bankName;
    }

    // getBankAccount
    public static String getBankAccount(String text) {
        String bankAccount = "";
        if (text.contains("账户号码")) {
            bankAccount = StringUtils.substringAfter(text, "账户号码");
            bankAccount = getBankAccountStr(bankAccount);
        }
        if (!text.contains("账户号码") && text.contains("银行账户")) {
            bankAccount = StringUtils.substringAfter(text, "银行账户");
            bankAccount = getBankAccountStr(bankAccount);
        }
        if (!text.contains("账户号码") && !text.contains("银行账户") && text.contains("账号")) {
            bankAccount = StringUtils.substringAfter(text, "账号");
            bankAccount = getBankAccountStr(bankAccount);
        }
        if (!text.contains("账户号码") && !text.contains("银行账户") && !text.contains("账号") && text.contains("开户银行")) {
            bankAccount = StringUtils.substringAfter(text, "开户银行");
            bankAccount = getBankAccountStr(bankAccount);
        }
        if (!text.contains("账户号码")
                && !text.contains("银行账户")
                && !text.contains("账号")
                && !text.contains("开户银行")
                && text.contains("帐号")) {
            bankAccount = StringUtils.substringAfter(text, "帐号");
            bankAccount = getBankAccountStr(bankAccount);
        }
        // log.info("bankAccount + " + bankAccount);
        return bankAccount;
    }

    // getEmail
    public static String getEmail(String text) {
        String email;
        email = StringUtils.substringBetween(text, "邮箱", "com");
        if (email != null) {
            email += "com";
        }
        if (email == null) {
            email = StringUtils.substringBetween(text, "邮箱", "cn");
            if (email != null) {
                email += "cn";
            }
        }
        if (email == null) {
            email = StringUtils.substringBetween(text, "邮箱", "net");
            if (email != null) {
                email += ".net";
            }
        }
        email = StringUtils.deleteWhitespace(email);
        // log.info("email + " + email);
        return email;
    }

    // getCompanyNameStr
    public static String getCompanyNameStr(String companyName) {
        if (companyName.contains("企业税号")) {
            companyName = StringUtils.substringBefore(companyName, "企业税号");
        }
        if (companyName.contains("税号")) {
            companyName = StringUtils.substringBefore(companyName, "税号");
        }
        if (companyName.contains("地址")) {
            companyName = StringUtils.substringBefore(companyName, "地址");
        }
        if (companyName.contains("开户银行")) {
            companyName = StringUtils.substringBefore(companyName, "开户银行");
        }
        if (companyName.contains("统一社会信用代码")) {
            companyName = StringUtils.substringBefore(companyName, "统一社会信用代码");
        }
        if (companyName.contains("统一社会代码")) {
            companyName = StringUtils.substringBefore(companyName, "统一社会代码");
        }
        if (companyName.contains("纳税人识别号")) {
            companyName = StringUtils.substringBefore(companyName, "纳税人识别号");
        }
        return companyName;
    }

    // getTaxAccountStr
    public static String getTaxAccountStr(String taxAccount) {
        if (taxAccount.contains("企业地址")) {
            taxAccount = StringUtils.substringBefore(taxAccount, "企业地址");
        }
        if (taxAccount.contains("地址、电话")) {
            taxAccount = StringUtils.substringBefore(taxAccount, "地址、电话");
        }
        if (taxAccount.contains("单位地址")) {
            taxAccount = StringUtils.substringBefore(taxAccount, "单位地址");
        }
        if (taxAccount.contains("电话")) {
            taxAccount = StringUtils.substringBefore(taxAccount, "电话");
        }
        if (taxAccount.contains("地址")) {
            taxAccount = StringUtils.substringBefore(taxAccount, "地址");
        }
        if (taxAccount.contains("公司注册")) {
            taxAccount = StringUtils.substringBefore(taxAccount, "公司注册");
        }
        if (taxAccount.contains("账户号码")) {
            taxAccount = StringUtils.substringBefore(taxAccount, "账户号码");
        }
        return taxAccount;
    }

    // getAddressStr
    public static String getAddressStr(String address) {
        if (address.contains("企业电话")) {
            address = StringUtils.substringBefore(address, "企业电话");
        }
        if (address.contains("开户行")) {
            address = StringUtils.substringBefore(address, "开户行");
        }
        if (address.contains("电话")) {
            address = StringUtils.substringBefore(address, "电话");
        }
        if (address.contains("开户银行")) {
            address = StringUtils.substringBefore(address, "开户银行");
        }
        if (address.contains("账号")) {
            address = StringUtils.substringBefore(address, "账号");
        }
        // log.info("address + " + address);
        if (address.length() >= 11) {
            String targetPhone = "";
            String phone = address.substring(address.length() - 11);
            if (PhoneUtils.isChinaPhoneLegal(phone)) {
                targetPhone = phone;
            } else {
                if (PhoneUtils.isTelephone(phone)) {
                    targetPhone = phone;
                }
                if (address.length() >= 12) {
                    String phoneStr = address.substring(address.length() - 12);
                    if (PhoneUtils.isTelephone(phoneStr)) {
                        targetPhone = phoneStr;
                    }
                }
                if (address.length() >= 13) {
                    String phoneStr = address.substring(address.length() - 13);
                    // log.info("phoneStr + " + phoneStr);
                    if (PhoneUtils.isTelephone(phoneStr)) {
                        targetPhone = phoneStr;
                    }
                }
            }
            address = address.replace(targetPhone, "");
        }
        return address;
    }

    // getCompanyPhoneStr
    public static String getCompanyPhoneStr(String companyPhone) {
        // log.info("companyPhone + " + companyPhone);
        String phone = "";
        if (companyPhone.contains("开户银行")) {
            companyPhone = StringUtils.substringBefore(companyPhone, "开户银行");
            phone = getPhone(companyPhone);
        }
        if (!companyPhone.contains("开户银行")
                && companyPhone.contains("开户行")) {
            companyPhone = StringUtils.substringBefore(companyPhone, "开户行");
            phone = getPhone(companyPhone);
        }
        if (!companyPhone.contains("开户银行")
                && !companyPhone.contains("开户行")
                && companyPhone.contains("账号")) {
            companyPhone = StringUtils.substringBefore(companyPhone, "账号");
            phone = getPhone(companyPhone);
        }
        if (!companyPhone.contains("开户银行")
                && !companyPhone.contains("开户行")
                && !companyPhone.contains("账号")
                && companyPhone.contains("地址")) {
            companyPhone = StringUtils.substringBefore(companyPhone, "地址");
            phone = getPhone(companyPhone);
        }
        // log.info("companyPhoneLast + " + companyPhone);
        if (PhoneUtils.isChinaPhoneLegal(companyPhone) || PhoneUtils.isTelephone(companyPhone)) {
            phone = companyPhone;
        }
        return phone;
    }

    // getPhone
    public static String getPhone(String companyPhone) {
        String targetPhone = "";
        if (companyPhone.length() >= 11) {
            log.info("companyPhone + " + companyPhone);
            String phone = companyPhone.substring(companyPhone.length() - 11);
            log.info("phone + " + phone);
            if (PhoneUtils.isChinaPhoneLegal(phone)) {
                targetPhone = phone;
            } else {
                if (PhoneUtils.isTelephone(phone)) {
                    targetPhone = phone;
                }
                if (companyPhone.length() >= 12) {
                    String phoneStr = companyPhone.substring(companyPhone.length() - 12);
                    if (PhoneUtils.isTelephone(phoneStr)) {
                        targetPhone = phoneStr;
                    }
                }
                log.info("companyPhone13 + " + companyPhone);
                if (companyPhone.length() >= 13) {
                    String phoneStr = companyPhone.substring(companyPhone.length() - 13);
                    // log.info("phoneStr + " + phoneStr);
                    if (PhoneUtils.isTelephone(phoneStr)) {
                        targetPhone = phoneStr;
                    }
                }
            }
        }
        return targetPhone;
    }

    // getBankNameStr
    public static String getBankNameStr(String bankName) {
        if (bankName.contains("账号")) {
            bankName = StringUtils.substringBefore(bankName, "账号");
        }
        if (bankName.contains("开户行地址")) {
            bankName = StringUtils.substringBefore(bankName, "开户行地址");
        }
        if (bankName.contains("统一社会信用代码")) {
            bankName = StringUtils.substringBefore(bankName, "统一社会信用代码");
        }
        if (bankName.contains("法定代表人")) {
            bankName = StringUtils.substringBefore(bankName, "法定代表人");
        }
        if (bankName.contains("帐号")) {
            bankName = StringUtils.substringBefore(bankName, "帐号");
        }
        if (bankName.contains("收票邮箱")) {
            bankName = StringUtils.substringBefore(bankName, "收票邮箱");
        }
        if (bankName.contains("银行账户")) {
            bankName = StringUtils.substringBefore(bankName, "银行账户");
        }
        if (bankName.contains("邮箱")) {
            bankName = StringUtils.substringBefore(bankName, "邮箱");
        }
        String number = StringTools.getNumber(bankName);
        bankName = bankName.replace(number, "");
        return bankName;
    }

    // getBankAccountStr
    public static String getBankAccountStr(String bankAccount) {
        if (bankAccount.contains("收票邮箱")) {
            bankAccount = StringUtils.substringBefore(bankAccount, "收票邮箱");
        }
        if (bankAccount.contains("开户行")) {
            bankAccount = StringUtils.substringBefore(bankAccount, "开户行");
        }
        if (bankAccount.contains("行号")) {
            bankAccount = StringUtils.substringBefore(bankAccount, "行号");
        }
        if (bankAccount.contains("统一社会信用代码")) {
            bankAccount = StringUtils.substringBefore(bankAccount, "统一社会信用代码");
        }
        bankAccount = StringTools.getNumber(bankAccount);
        return bankAccount;
    }
}
