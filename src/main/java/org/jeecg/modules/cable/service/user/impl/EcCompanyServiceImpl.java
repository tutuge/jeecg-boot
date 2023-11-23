package org.jeecg.modules.cable.service.user.impl;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.user.EcCompany;
import org.jeecg.modules.cable.mapper.dao.user.EcCompanyMapper;
import org.jeecg.modules.cable.service.user.EcCompanyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * 客户的公司信息
 */
@Service
public class EcCompanyServiceImpl extends ServiceImpl<EcCompanyMapper, EcCompany> implements EcCompanyService {
    @Resource
    EcCompanyMapper ecCompanyMapper;

    @Override
    public EcCompany getObject(EcCompany record) {//根据EcUser获取EcCompany
        return ecCompanyMapper.getObject(record);
    }

    @Override
    public Integer insert(EcCompany record) {
        return ecCompanyMapper.insert(record);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public EcCompany detailCompany(String companyName) {
        EcCompany company = new EcCompany();
        company.setCompanyName(companyName);
        EcCompany object = getObject(company);
        if (ObjUtil.isNull(object)) {
            BigDecimal zeroMoney = BigDecimal.ZERO;
            long endTime = System.currentTimeMillis() + 2592000L * 1000 * 12;
            EcCompany record = new EcCompany();
            record.setCartId(0);//没有管理员ID
            record.setStartType(true);//默认开启
            record.setCompanyType(1);//个人账户
            record.setAddressDesc("");
            record.setMoney(zeroMoney);
            record.setMoneyFrozen(zeroMoney);
            record.setMoneyUse(zeroMoney);
            record.setMoneyConsume(zeroMoney);
            record.setWithdrawTotal(zeroMoney);
            record.setRechargeTotal(zeroMoney);
            record.setEndTime(endTime);
            record.setDescription("");
            record.setCompanyName(companyName);
            record.setAddTime(System.currentTimeMillis());
            record.setUpdateTime(System.currentTimeMillis());
            insert(record);
            return record;
        }
        return object;
    }

}
