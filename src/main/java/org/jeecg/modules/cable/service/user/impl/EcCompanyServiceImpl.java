package org.jeecg.modules.cable.service.user.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjUtil;
import jakarta.annotation.Resource;
import org.jeecg.modules.cable.constants.CustomerCacheConstant;
import org.jeecg.modules.cable.controller.user.user.bo.EcuUserRegisterBo;
import org.jeecg.modules.cable.entity.user.EcCompany;
import org.jeecg.modules.cable.mapper.dao.user.EcCompanyMapper;
import org.jeecg.modules.cable.service.user.EcCompanyService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 客户的公司信息
 */
@Service
public class EcCompanyServiceImpl implements EcCompanyService {
    @Resource
    EcCompanyMapper ecCompanyMapper;

    @Override
    public EcCompany getObject(EcCompany record) {//根据EcUser获取EcCompany
        return ecCompanyMapper.getObject(record);
    }

    @Cacheable(value = {CustomerCacheConstant.CUSTOMER_COMPANY_CACHE}, key = "#ecCompanyId", unless = "#result == null ")
    @Override
    public EcCompany getObjectById(Integer ecCompanyId) {
        return ecCompanyMapper.selectById(ecCompanyId);
    }


    @CacheEvict(value = {CustomerCacheConstant.CUSTOMER_COMPANY_CACHE}, key = "#company.ecCompanyId")
    @Override
    public int updateById(EcCompany company) {
        return ecCompanyMapper.updateById(company);
    }

    @Override
    public void hasExpire(Integer ecCompanyId) {
        EcCompany company = ecCompanyMapper.selectById(ecCompanyId);
        Date endTime = company.getEndTime();
        int compare = DateUtil.compare(endTime, new Date());
        if (compare <= 0) {
            //公司过期的话，抛出本公司已经过期
            throw new RuntimeException("本公司已经过期");
        }
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
            //七天之后
            DateTime dateTime = DateUtil.offsetDay(new Date(), 7);
            record.setEndTime(dateTime);
            record.setDescription("");
            record.setCompanyName(companyName);
            record.setAddTime(new Date());
            record.setUpdateTime(new Date());
            insert(record);
            return record;
        }
        return object;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public EcCompany deal(EcuUserRegisterBo bo) {
        String ecPhone = bo.getEcPhone();
        String companyName = bo.getCompanyName();
        String addressDesc = bo.getAddressDesc();
        BigDecimal zeroMoney = BigDecimal.ZERO;
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
        //七天之后
        DateTime dateTime = DateUtil.offsetDay(new Date(), 7);
        record.setEndTime(dateTime);
        record.setDescription("");
        record.setAddTime(new Date());
        record.setUpdateTime(new Date());
        if ("".equals(companyName)) {
            //如果公司名称为空时将公司名称视为手机号
            companyName = ecPhone;
        } else {
            EcCompany recordEcCompany = new EcCompany();
            recordEcCompany.setCompanyName(companyName);
            EcCompany ecCompany = getObject(recordEcCompany);
            if (ecCompany != null) {
                throw new RuntimeException("公司名称已占用");
            }
        }
        record.setCompanyName(companyName);
        insert(record);
        return record;
    }

    //根据公司名称查询公司
    @Override
    public EcCompany getObjectPassCompanyName(String ecPhone, String companyName) {
        if ("".equals(companyName)) {
            companyName = ecPhone;
        }
        EcCompany record = new EcCompany();
        record.setCompanyName(companyName);
        return getObject(record);
    }


}
