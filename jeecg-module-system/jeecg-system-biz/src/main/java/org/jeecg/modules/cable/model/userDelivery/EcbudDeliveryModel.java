package org.jeecg.modules.cable.model.userDelivery;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.userDelivery.delivery.bo.EcbudDeliveryBo;
import org.jeecg.modules.cable.entity.userDelivery.EcbudDelivery;
import org.jeecg.modules.cable.service.userDelivery.EcbudDeliveryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class EcbudDeliveryModel {// 用户默认仓库
    @Resource
    EcbudDeliveryService ecbudDeliveryService;


    public EcbudDelivery getObject() {
        // 获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer ecuId = ecUser.getEcuId();
        EcbudDelivery record = new EcbudDelivery();
        record.setEcCompanyId(ecUser.getEcCompanyId());
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
        // 获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer sortId = bo.getSortId();

        EcbudDelivery record = new EcbudDelivery();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        Integer ecuId = ecUser.getEcuId();
        record.setEcuId(ecuId);
        EcbudDelivery ecbudDelivery = ecbudDeliveryService.getObject(record);
        String msg = "";
        if (ecbudDelivery == null) {// 插入
            record.setEcCompanyId(ecUser.getEcCompanyId());
            record.setEcuId(ecuId);
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
