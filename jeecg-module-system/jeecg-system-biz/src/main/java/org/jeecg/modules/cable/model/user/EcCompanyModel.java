package org.jeecg.modules.cable.model.user;

import org.jeecg.modules.cable.entity.user.EcCompany;
import org.jeecg.modules.cable.service.user.EcCompanyService;
import org.jeecg.modules.cable.tools.CommonFunction;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class EcCompanyModel {
    @Resource
    EcCompanyService ecCompanyService;

    //deal
    public Map<String, Object> deal(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status = 0;
        String code = "";
        String msg = "";
        String ecPhone = request.getParameter("ecPhone");
        String companyName = request.getParameter("companyName");
        String addressDesc = request.getParameter("addressDesc");
        BigDecimal zeroMoney = new BigDecimal("0");
        long endTime = System.currentTimeMillis() + 2592000L * 1000 * 12;
        EcCompany record = new EcCompany();
        record.setCartId(0);//没有管理员ID
        record.setStartType(true);//默认开启
        record.setCompanyType(1);//个人账户
        record.setAddressDesc(addressDesc);
        record.setMoney(zeroMoney);
        record.setMoneyFrozen(zeroMoney);
        record.setMoneyUse(zeroMoney);
        record.setMoneyConsume(zeroMoney);
        record.setWithdrawTotal(zeroMoney);
        record.setRechargeTotal(zeroMoney);
        record.setEndTime(endTime);
        record.setDescription("");
        record.setAddTime(System.currentTimeMillis());
        record.setUpdateTime(System.currentTimeMillis());
        if ("".equals(companyName)) {//如果公司名称为空时将公司名称视为手机号
            companyName = ecPhone;
        } else {
            EcCompany recordEcCompany = new EcCompany();
            recordEcCompany.setCompanyName(companyName);
            EcCompany ecCompany = ecCompanyService.getObject(recordEcCompany);
            if (ecCompany != null) {
                status = 5;//公司名称已占用
                code = "105";
                msg = "公司名称已占用";
            }
        }
        if (status != 5) {
            record.setCompanyName(companyName);
            ecCompanyService.insert(record);
            status = 6;//正常操作数据
            code = "200";
            msg = "正常操作数据";
        }
        CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }

    //getObjectPassCompanyName
    public EcCompany getObjectPassCompanyName(String ecPhone, String companyName) {
        if ("".equals(companyName)) {
            companyName = ecPhone;
        }
        EcCompany record = new EcCompany();
        record.setCompanyName(companyName);
        log.info(CommonFunction.getGson().toJson(record));
        return ecCompanyService.getObject(record);
    }
}
