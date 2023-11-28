package org.jeecg.modules.cable.model.userDelivery;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.userDelivery.delivery.bo.EcbudDeliveryBo;
import org.jeecg.modules.cable.entity.userDelivery.EcbudDelivery;
import org.jeecg.modules.cable.service.userDelivery.EcbudDeliveryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户默认仓库
 */
@Service
@Slf4j
public class EcbudDeliveryModel {
    @Resource
    EcbudDeliveryService ecbudDeliveryService;

    public EcbudDelivery getObject() {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecuId = sysUser.getUserId();
        EcbudDelivery record = new EcbudDelivery();
        record.setEcCompanyId(sysUser.getEcCompanyId());
        record.setEcuId(ecuId);// 暂不开启
        // log.info(CommonFunction.getGson().toJson(record));
        EcbudDelivery ecbudDelivery = ecbudDeliveryService.getObject(record);
        if (ecbudDelivery == null) {
            record.setSortId(1);
            ecbudDeliveryService.insert(record);
            ecbudDelivery = ecbudDeliveryService.getObject(record);
        }
        if (ecbudDelivery == null) {
            throw new RuntimeException("未获得数据");
        } else {
            return ecbudDelivery;
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public String deal(EcbudDeliveryBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer sortId = bo.getSortId();
        Integer ecCompanyId = sysUser.getEcCompanyId();
        EcbudDelivery record = new EcbudDelivery();
        record.setEcCompanyId(ecCompanyId);
        Integer ecuId = sysUser.getUserId();
        record.setEcuId(ecuId);
        record.setEcuqId(bo.getEcuqId());
        EcbudDelivery ecbudDelivery = ecbudDeliveryService.getObject(record);
        String msg = "";
        if (ecbudDelivery == null) {// 插入
            record.setEcCompanyId(ecCompanyId);
            record.setSortId(sortId);
            ecbudDeliveryService.insert(record);
            msg = "正常插入数据";
        } else {
            record.setEcbuddId(ecbudDelivery.getEcbuddId());
            record.setSortId(sortId);
            ecbudDeliveryService.updateByPrimaryKeySelective(record);
            msg = "正常更新数据";
        }
        return msg;
    }

}
