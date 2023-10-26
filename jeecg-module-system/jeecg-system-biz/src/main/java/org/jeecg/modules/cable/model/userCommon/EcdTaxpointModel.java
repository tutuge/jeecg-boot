package org.jeecg.modules.cable.model.userCommon;

import jakarta.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.userCommon.taxpoint.bo.TaxPointBaseBo;
import org.jeecg.modules.cable.controller.userCommon.taxpoint.bo.TaxPointBo;
import org.jeecg.modules.cable.controller.userCommon.taxpoint.vo.TaxPointVo;
import org.jeecg.modules.cable.entity.systemEcable.EcdTaxpoint;
import org.jeecg.modules.cable.entity.userCommon.EcduTaxpoint;
import org.jeecg.modules.cable.service.userCommon.EcdTaxpointService;
import org.jeecg.modules.cable.service.userCommon.EcduTaxpointService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcdTaxpointModel {
    @Resource
    EcdTaxpointService ecdTaxpointService;//系统税点
    @Resource
    EcduTaxpointService ecduTaxpointService;//用户税点

    //getListAndCount
    public TaxPointVo getListAndCount(TaxPointBo bo) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        EcdTaxpoint record = new EcdTaxpoint();
        record.setStartType(bo.getStartType());
        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcdTaxpoint> list = ecdTaxpointService.getList(record);
        Long count = ecdTaxpointService.getCount(record);
        return new TaxPointVo(list, count);
    }

    //getObject
    public EcdTaxpoint getObject(TaxPointBaseBo bo) {
        EcdTaxpoint record = new EcdTaxpoint();
        Integer ecdtId1 = bo.getEcdtId();
        record.setEcdtId(ecdtId1);
        EcdTaxpoint object = ecdTaxpointService.getObject(record);
        if (object != null) {
            Integer ecdtId = object.getEcdtId();
            EcduTaxpoint recordEcduTaxpoint = new EcduTaxpoint();
            recordEcduTaxpoint.setEcdtId(ecdtId);
            EcduTaxpoint ecduTaxpoint = ecduTaxpointService.getObject(recordEcduTaxpoint);
            object.setEcduTaxpoint(ecduTaxpoint);
        }
        return object;
    }
}
