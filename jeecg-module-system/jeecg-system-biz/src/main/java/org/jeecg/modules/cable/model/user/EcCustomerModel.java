package org.jeecg.modules.cable.model.user;

import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.user.customer.bo.EcCustomerDealBo;
import org.jeecg.modules.cable.controller.user.customer.vo.CustomerVo;
import org.jeecg.modules.cable.entity.user.EcCustomer;
import org.jeecg.modules.cable.model.price.EcuQuotedModel;
import org.jeecg.modules.cable.service.user.EcCustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EcCustomerModel {
    @Resource
    EcCustomerService ecCustomerService;
    @Resource
    EcuQuotedModel ecuQuotedModel;

    //deal
    public String deal(EcCustomerDealBo bo) {

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer eccuId = bo.getEccuId();
        String customerName = bo.getCustomerName();
        String customerPhone = bo.getCustomerPhone();
        String accountNumber = bo.getAccountNumber();
        String receiveName = bo.getReceiveName();
        String receivePhone = bo.getReceivePhone();
        String receiveAddress = bo.getReceiveAddress();
        String billName = bo.getBillName();
        String billPhone = bo.getBillPhone();
        String billAddress = bo.getBillAddress();
        String companyName = bo.getCompanyName();
        String taxAccount = bo.getTaxAccount();
        String address = bo.getAddress();
        String companyPhone = bo.getCompanyPhone();
        String bankName = bo.getBankName();
        String bankAccount = bo.getBankAccount();
        String email = bo.getEmail();
        String description = bo.getDescription();

        EcCustomer record = new EcCustomer();
        String msg = "";
        EcCustomer ecCustomer = getObjectPassEcCompanyIdAndCustomerName(
                eccuId,
                ecUser.getEcCompanyId(),
                customerName);
        if (ecCustomer != null) {

            throw new RuntimeException("客户名称已占用");
        } else {
            if (ObjectUtil.isNull(eccuId)) {//新增数据
                record.setEcCompanyId(ecUser.getEcCompanyId());
                record.setCustomerName(customerName);
                record.setEcuId(ecUser.getEcuId());
                record.setCustomerPhone(customerPhone);
                record.setAccountNumber(accountNumber);
                record.setReceiveName(receiveName);
                record.setReceivePhone(receivePhone);
                record.setReceiveAddress(receiveAddress);
                record.setBillName(billName);
                record.setBillPhone(billPhone);
                record.setBillAddress(billAddress);
                record.setCompanyName(companyName);
                record.setTaxAccount(taxAccount);
                record.setAddress(address);
                record.setCompanyPhone(companyPhone);
                record.setBankName(bankName);
                record.setBankAccount(bankAccount);
                record.setEmail(email);
                record.setDescription(description);
                ecCustomerService.insert(record);

                msg = "正常新增数据";
            } else {
                record.setEccuId(eccuId);
                record.setCustomerName(customerName);
                record.setCustomerPhone(customerPhone);
                record.setAccountNumber(accountNumber);
                record.setReceiveName(receiveName);
                record.setReceivePhone(receivePhone);
                record.setReceiveAddress(receiveAddress);
                record.setBillName(billName);
                record.setBillPhone(billPhone);
                record.setBillAddress(billAddress);
                record.setCompanyName(companyName);
                record.setTaxAccount(taxAccount);
                record.setAddress(address);
                record.setCompanyPhone(companyPhone);
                record.setBankName(bankName);
                record.setBankAccount(bankAccount);
                record.setEmail(email);
                record.setDescription(description);
                ecCustomerService.update(record);
                msg = "正常更新数据";
            }
            Integer ecuqId = bo.getEcuqId();
            if (ecuqId != null) {
                ecCustomer = getObjectPassEcCompanyIdAndCustomerName(
                        eccuId,
                        ecUser.getEcCompanyId(),
                        customerName);
                eccuId = ecCustomer.getEccuId();
                ecuQuotedModel.dealEccuId(ecuqId, eccuId);
            }
        }
        return msg;
    }

    //getList
    public CustomerVo getList(EcCustomerDealBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        EcCustomer record = new EcCustomer();
        record.setEcuId(ecUser.getEcuId());

        Integer pageNumber = bo.getPageNumber();
        Integer startNumber = (bo.getStartNumber() - 1) * pageNumber;
        record.setStartNumber(startNumber);
        record.setPageNumber(pageNumber);

//        if (request.getParameter("customerName") != null) {//客户名称
//            String customerName = request.getParameter("customerName");
//            record.setCustomerName("%" + customerName + "%");
//        }
//        if (request.getParameter("customerPhone") != null) {//客户手机号
//            String customerPhone = request.getParameter("customerPhone");
//            record.setCustomerPhone("%" + customerPhone + "%");
//        }
//        if (request.getParameter("accountNumber") != null) {//账号名称
//            String accountNumber = request.getParameter("accountNumber");
//            record.setAccountNumber("%" + accountNumber + "%");
//        }
//        if (request.getParameter("receiveName") != null) {//收货人名称
//            String receiveName = request.getParameter("receiveName");
//            record.setReceiveName("%" + receiveName + "%");
//        }
//        if (request.getParameter("receivePhone") != null) {//收货人手机号
//            String receivePhone = request.getParameter("receivePhone");
//            record.setReceivePhone("%" + receivePhone + "%");
//        }
//        if (request.getParameter("receiveAddress") != null) {//收货人地址
//            String receiveAddress = request.getParameter("receiveAddress");
//            record.setReceiveAddress("%" + receiveAddress + "%");
//        }
//        if (request.getParameter("billName") != null) {//发票收货人名称
//            String billName = request.getParameter("billName");
//            record.setBillName("%" + billName + "%");
//        }
//        if (request.getParameter("billPhone") != null) {//发票收货人手机号
//            String billPhone = request.getParameter("billPhone");
//            record.setBillPhone("%" + billPhone + "%");
//        }
//        if (request.getParameter("billAddress") != null) {//发票收货人地址
//            String billAddress = request.getParameter("billAddress");
//            record.setBillAddress("%" + billAddress + "%");
//        }
//        if (request.getParameter("companyName") != null) {//公司名称
//            String companyName = request.getParameter("companyName");
//            record.setCompanyName("%" + companyName + "%");
//        }
//        if (request.getParameter("taxAccount") != null) {//税号
//            String taxAccount = request.getParameter("taxAccount");
//            record.setTaxAccount("%" + taxAccount + "%");
//        }
//        if (request.getParameter("address") != null) {//公司地址
//            String address = request.getParameter("address");
//            record.setAddress("%" + address + "%");
//        }
//        if (request.getParameter("companyPhone") != null) {//公司电话
//            String companyPhone = request.getParameter("companyPhone");
//            record.setCompanyPhone("%" + companyPhone + "%");
//        }
//        if (request.getParameter("bankName") != null) {//银行名称
//            String bankName = request.getParameter("bankName");
//            record.setBankName("%" + bankName + "%");
//        }
//        if (request.getParameter("bankAccount") != null) {//银行账号
//            String bankAccount = request.getParameter("bankAccount");
//            record.setBankAccount("%" + bankAccount + "%");
//        }
//        if (request.getParameter("email") != null) {//邮箱
//            String email = request.getParameter("email");
//            record.setEmail("%" + email + "%");
//        }
        List<EcCustomer> list = ecCustomerService.getList(record);
        long count = ecCustomerService.getCount(record);
        return new CustomerVo(list, count);
    }

    //getObject
    public EcCustomer getObject(HttpServletRequest request) {
        Integer eccuId = Integer.parseInt(request.getParameter("eccuId"));
        return getObjectPassEccuId(eccuId);
    }

    /***===数据模型===***/
//getObjectPassEccuId
    public EcCustomer getObjectPassEccuId(Integer eccuId) {
        EcCustomer record = new EcCustomer();
        record.setEccuId(eccuId);
        return ecCustomerService.getObject(record);
    }

    //getObjectPassEcuIdAndCustomerName
    public EcCustomer getObjectPassEcCompanyIdAndCustomerName(Integer eccuId, Integer ecCompanyId, String customerName) {
        EcCustomer record = new EcCustomer();
        record.setEccuId(eccuId);
        record.setEcCompanyId(ecCompanyId);
        record.setCustomerName(customerName);
        return ecCustomerService.getObject(record);
    }
}
