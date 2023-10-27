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
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EcCustomerModel {
    @Resource
    EcCustomerService ecCustomerService;
    @Resource
    EcuQuotedModel ecuQuotedModel;

    // deal
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
            if (ObjectUtil.isNull(eccuId)) {// 新增数据
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

    // getList
    public CustomerVo getList(EcCustomerDealBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        EcCustomer record = new EcCustomer();
        record.setEcuId(ecUser.getEcuId());
        BeanUtils.copyProperties(bo, record);

        Integer pageNumber = bo.getPageSize();
        Integer startNumber = (bo.getPageNum() - 1) * pageNumber;
        record.setStartNumber(startNumber);
        record.setPageNumber(pageNumber);
        
        List<EcCustomer> list = ecCustomerService.getList(record);
        long count = ecCustomerService.getCount(record);
        return new CustomerVo(list, count);
    }

    // getObject
    public EcCustomer getObject(HttpServletRequest request) {
        Integer eccuId = Integer.parseInt(request.getParameter("eccuId"));
        return getObjectPassEccuId(eccuId);
    }

    /***===数据模型===***/
// getObjectPassEccuId
    public EcCustomer getObjectPassEccuId(Integer eccuId) {
        EcCustomer record = new EcCustomer();
        record.setEccuId(eccuId);
        return ecCustomerService.getObject(record);
    }

    // getObjectPassEcuIdAndCustomerName
    public EcCustomer getObjectPassEcCompanyIdAndCustomerName(Integer eccuId, Integer ecCompanyId, String customerName) {
        EcCustomer record = new EcCustomer();
        record.setEccuId(eccuId);
        record.setEcCompanyId(ecCompanyId);
        record.setCustomerName(customerName);
        return ecCustomerService.getObject(record);
    }
}
