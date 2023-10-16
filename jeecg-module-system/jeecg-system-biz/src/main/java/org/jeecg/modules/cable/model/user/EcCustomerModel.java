package org.jeecg.modules.cable.model.user;

import org.jeecg.modules.cable.entity.user.EcCustomer;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.modules.cable.model.price.EcuQuotedModel;
import org.jeecg.modules.cable.service.user.EcCustomerService;
import org.jeecg.modules.cable.tools.CommonFunction;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EcCustomerModel {
    @Resource
    EcuLoginModel ecuLoginModel;
    @Resource
    EcUserModel ecUserModel;
    @Resource
    EcCustomerService ecCustomerService;
    @Resource
    EcuQuotedModel ecuQuotedModel;

    //deal
    public Map<String, Object> deal(HttpServletRequest request) {
        Map<String, Object> map;
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            EcUser ecUser = ecUserModel.getObjectPassEcuId(ecuId);
            int eccuId = Integer.parseInt(request.getParameter("eccuId"));
            String customerName = request.getParameter("customerName");
            String customerPhone = request.getParameter("customerPhone");
            String accountNumber = request.getParameter("accountNumber");
            String receiveName = request.getParameter("receiveName");
            String receivePhone = request.getParameter("receivePhone");
            String receiveAddress = request.getParameter("receiveAddress");
            String billName = request.getParameter("billName");
            String billPhone = request.getParameter("billPhone");
            String billAddress = request.getParameter("billAddress");
            String companyName = request.getParameter("companyName");
            String taxAccount = request.getParameter("taxAccount");
            String address = request.getParameter("address");
            String companyPhone = request.getParameter("companyPhone");
            String bankName = request.getParameter("bankName");
            String bankAccount = request.getParameter("bankAccount");
            String email = request.getParameter("email");
            String description = request.getParameter("description");
            EcCustomer record = new EcCustomer();
            EcCustomer ecCustomer = getObjectPassEcCompanyIdAndCustomerName(
                    eccuId,
                    ecUser.getEcCompanyId(),
                    customerName);
            if (ecCustomer != null) {
                status = 3;//客户名称已占用
                code = "103";
                msg = "客户名称已占用";
            } else {
                if (eccuId == 0) {//新增数据
                    record.setEcCompanyId(ecUser.getEcCompanyId());
                    record.setCustomerName(customerName);
                    record.setEcuId(ecuId);
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
                    status = 4;//正常新增数据
                    code = "200";
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
                    status = 4;//正常更新数据
                    code = "201";
                    msg = "正常更新数据";
                }
                if (request.getParameter("ecuqId") != null) {
                    int ecuqId = Integer.parseInt(request.getParameter("ecuqId"));
                    ecCustomer = getObjectPassEcCompanyIdAndCustomerName(
                            eccuId,
                            ecUser.getEcCompanyId(),
                            customerName);
                    eccuId = ecCustomer.getEccuId();
                    ecuQuotedModel.dealEccuId(ecuqId, eccuId);
                }
            }
            CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    //getList
    public Map<String, Object> getList(HttpServletRequest request) {
        Map<String, Object> map;
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            EcCustomer record = new EcCustomer();
            record.setEcuId(ecuId);
            if (request.getParameter("pageNumber") != null) {
                int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
                int startNumber = (Integer.parseInt(request.getParameter("page")) - 1) * pageNumber;
                record.setStartNumber(startNumber);
                record.setPageNumber(pageNumber);
            }
            if (request.getParameter("customerName") != null) {//客户名称
                String customerName = request.getParameter("customerName");
                record.setCustomerName("%" + customerName + "%");
            }
            if (request.getParameter("customerPhone") != null) {//客户手机号
                String customerPhone = request.getParameter("customerPhone");
                record.setCustomerPhone("%" + customerPhone + "%");
            }
            if (request.getParameter("accountNumber") != null) {//账号名称
                String accountNumber = request.getParameter("accountNumber");
                record.setAccountNumber("%" + accountNumber + "%");
            }
            if (request.getParameter("receiveName") != null) {//收货人名称
                String receiveName = request.getParameter("receiveName");
                record.setReceiveName("%" + receiveName + "%");
            }
            if (request.getParameter("receivePhone") != null) {//收货人手机号
                String receivePhone = request.getParameter("receivePhone");
                record.setReceivePhone("%" + receivePhone + "%");
            }
            if (request.getParameter("receiveAddress") != null) {//收货人地址
                String receiveAddress = request.getParameter("receiveAddress");
                record.setReceiveAddress("%" + receiveAddress + "%");
            }
            if (request.getParameter("billName") != null) {//发票收货人名称
                String billName = request.getParameter("billName");
                record.setBillName("%" + billName + "%");
            }
            if (request.getParameter("billPhone") != null) {//发票收货人手机号
                String billPhone = request.getParameter("billPhone");
                record.setBillPhone("%" + billPhone + "%");
            }
            if (request.getParameter("billAddress") != null) {//发票收货人地址
                String billAddress = request.getParameter("billAddress");
                record.setBillAddress("%" + billAddress + "%");
            }
            if (request.getParameter("companyName") != null) {//公司名称
                String companyName = request.getParameter("companyName");
                record.setCompanyName("%" + companyName + "%");
            }
            if (request.getParameter("taxAccount") != null) {//税号
                String taxAccount = request.getParameter("taxAccount");
                record.setTaxAccount("%" + taxAccount + "%");
            }
            if (request.getParameter("address") != null) {//公司地址
                String address = request.getParameter("address");
                record.setAddress("%" + address + "%");
            }
            if (request.getParameter("companyPhone") != null) {//公司电话
                String companyPhone = request.getParameter("companyPhone");
                record.setCompanyPhone("%" + companyPhone + "%");
            }
            if (request.getParameter("bankName") != null) {//银行名称
                String bankName = request.getParameter("bankName");
                record.setBankName("%" + bankName + "%");
            }
            if (request.getParameter("bankAccount") != null) {//银行账号
                String bankAccount = request.getParameter("bankAccount");
                record.setBankAccount("%" + bankAccount + "%");
            }
            if (request.getParameter("email") != null) {//邮箱
                String email = request.getParameter("email");
                record.setEmail("%" + email + "%");
            }
            List<EcCustomer> list = ecCustomerService.getList(record);
            long count = ecCustomerService.getCount(record);
            map.put("list", list);
            map.put("count", count);
            status = 3;//正常获取数据
            code = "200";
            msg = "正常获取数据";
            CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    //getObject
    public Map<String, Object> getObject(HttpServletRequest request) {
        Map<String, Object> map;
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            int eccuId = Integer.parseInt(request.getParameter("eccuId"));
            EcCustomer ecCustomer = getObjectPassEccuId(eccuId);
            map.put("object", ecCustomer);
            status = 3;//正常获取数据
            code = "200";
            msg = "正常获取数据";
            CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    /***===数据模型===***/
    //getObjectPassEccuId
    public EcCustomer getObjectPassEccuId(int eccuId) {
        EcCustomer record = new EcCustomer();
        record.setEccuId(eccuId);
        return ecCustomerService.getObject(record);
    }

    //getObjectPassEcuIdAndCustomerName
    public EcCustomer getObjectPassEcCompanyIdAndCustomerName(int eccuId, int ecCompanyId, String customerName) {
        EcCustomer record = new EcCustomer();
        record.setEccuId(eccuId);
        record.setEcCompanyId(ecCompanyId);
        record.setCustomerName(customerName);
        return ecCustomerService.getObject(record);
    }
}
