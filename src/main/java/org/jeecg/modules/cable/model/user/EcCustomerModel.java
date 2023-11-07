package org.jeecg.modules.cable.model.user;

import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.user.customer.bo.EcCustomerDealBo;
import org.jeecg.modules.cable.controller.user.customer.bo.EcuCustomerBaseBo;
import org.jeecg.modules.cable.entity.price.EcuQuoted;
import org.jeecg.modules.cable.entity.user.EcCustomer;
import org.jeecg.modules.cable.model.price.EcuQuotedModel;
import org.jeecg.modules.cable.service.user.EcCustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class EcCustomerModel {
    @Resource
    EcCustomerService ecCustomerService;
    @Resource
    EcuQuotedModel ecuQuotedModel;


    @Transactional(rollbackFor = Exception.class)
    public String deal(EcCustomerDealBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        Integer eccuId = bo.getEccuId();

        EcCustomer record = new EcCustomer();
        BeanUtils.copyProperties(bo, record);
        String customerName = bo.getCustomerName();
        String msg = "";
        EcCustomer ecCustomer = getObjectPassEcCompanyIdAndCustomerName(eccuId, sysUser.getEcCompanyId(), customerName);
        if (ecCustomer != null) {
            throw new RuntimeException("客户名称已占用");
        }
        if (ObjectUtil.isNull(eccuId)) {// 新增数据
            record.setEcCompanyId(sysUser.getEcCompanyId());
            ecCustomerService.insert(record);
            msg = "正常新增数据";
        } else {
            record.setEccuId(eccuId);
            ecCustomerService.update(record);
            msg = "正常更新数据";
        }
        Integer ecuqId = bo.getEcuqId();
        if (ecuqId != null) {
            ecCustomer = getObjectPassEcCompanyIdAndCustomerName(eccuId, sysUser.getEcCompanyId(), customerName);
            eccuId = ecCustomer.getEccuId();
            ecuQuotedModel.dealEccuId(ecuqId, eccuId);
        }
        return msg;
    }

    public void delete(EcuCustomerBaseBo bo) {
        Integer eccuId = bo.getEccuId();
        List<EcuQuoted> ecuQuoteds = ecuQuotedModel.queryByCustomerId(eccuId);
        if (!ecuQuoteds.isEmpty()) {
            throw new RuntimeException("当前用户在被使用，无法删除");
        }
        ecCustomerService.deleteById(eccuId);
    }


    public EcCustomer getObject(EcuCustomerBaseBo bo) {
        return getObjectPassEccuId(bo.getEccuId());
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
