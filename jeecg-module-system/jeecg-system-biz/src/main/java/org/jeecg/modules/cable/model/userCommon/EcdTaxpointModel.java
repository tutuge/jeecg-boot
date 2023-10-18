package org.jeecg.modules.cable.model.userCommon;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.userCommon.taxpoint.bo.TaxPointBo;
import org.jeecg.modules.cable.controller.userCommon.taxpoint.vo.TaxPointVo;
import org.jeecg.modules.cable.entity.systemEcable.EcdTaxpoint;
import org.jeecg.modules.cable.entity.userCommon.EcduTaxpoint;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userCommon.EcdTaxpointService;
import org.jeecg.modules.cable.service.userCommon.EcduTaxpointService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcdTaxpointModel {
    @Resource
    EcUserService ecUserService;
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
        long count = ecdTaxpointService.getCount(record);
        return new TaxPointVo(list, count);
    }

    //getObject
    public EcdTaxpoint getObject(HttpServletRequest request) {

        EcdTaxpoint record = new EcdTaxpoint();
        if (request.getParameter("ecdtId") != null) {
            int ecdtId = Integer.parseInt(request.getParameter("ecdtId"));
            record.setEcdtId(ecdtId);
        }
        EcdTaxpoint object = ecdTaxpointService.getObject(record);
        if (object != null) {
            int ecdtId = object.getEcdtId();
            EcduTaxpoint recordEcduTaxpoint = new EcduTaxpoint();
            recordEcduTaxpoint.setEcdtId(ecdtId);
            EcduTaxpoint ecduTaxpoint = ecduTaxpointService.getObject(recordEcduTaxpoint);
            object.setEcduTaxpoint(ecduTaxpoint);
        }
        return object;
    }
}
