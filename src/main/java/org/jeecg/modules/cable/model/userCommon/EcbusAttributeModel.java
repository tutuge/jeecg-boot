package org.jeecg.modules.cable.model.userCommon;

import jakarta.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.userCommon.attribute.bo.AttributeBo;
import org.jeecg.modules.cable.entity.userCommon.EcbusAttribute;
import org.jeecg.modules.cable.service.userCommon.EcbusAttributeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EcbusAttributeModel {

    @Resource
    EcbusAttributeService ecbusAttributeService;


    @Transactional(rollbackFor = Exception.class)
    public void deal(AttributeBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();


        EcbusAttribute record = new EcbusAttribute();
        record.setEcCompanyId(sysUser.getEcCompanyId());
        // 铜利润
        record.setPcShowOrHide(bo.getPcShowOrHide());
        // 铝利润
        record.setPaShowOrHide(bo.getPaShowOrHide());
        // 运费加点
        record.setDmShowOrHide(bo.getDmShowOrHide());
        deal(record);
    }


    public EcbusAttribute getObject() {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        EcbusAttribute record = new EcbusAttribute();
        record.setEcCompanyId(sysUser.getEcCompanyId());
        return ecbusAttributeService.getObject(record);
    }

    /***===数据模型===***/

    @Transactional(rollbackFor = Exception.class)
    public void deal(EcbusAttribute record) {
        EcbusAttribute recordEcbusAttribute = new EcbusAttribute();
        recordEcbusAttribute.setEcCompanyId(record.getEcCompanyId());
        EcbusAttribute ecbusAttribute = ecbusAttributeService.getObject(record);
        if (ecbusAttribute == null) {
            ecbusAttributeService.insert(record);
        } else {
            ecbusAttributeService.update(record);
        }
    }

}
