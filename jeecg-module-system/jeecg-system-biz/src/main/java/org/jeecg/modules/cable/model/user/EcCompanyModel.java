package org.jeecg.modules.cable.model.user;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.controller.user.user.bo.EcuUserRegisterBo;
import org.jeecg.modules.cable.entity.user.EcCompany;
import org.jeecg.modules.cable.service.user.EcCompanyService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Slf4j
public class EcCompanyModel {
    @Resource
    EcCompanyService ecCompanyService;

    //deal
    @Transactional(rollbackFor = Exception.class)
    public void deal(EcuUserRegisterBo bo) {
        String ecPhone = bo.getEcPhone();
        String companyName = bo.getCompanyName();
        String addressDesc = bo.getAddressDesc();

        BigDecimal zeroMoney = BigDecimal.ZERO;
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
                throw new RuntimeException("公司名称已占用");
            }
        }
        record.setCompanyName(companyName);
        ecCompanyService.insert(record);
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
