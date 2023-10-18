package org.jeecg.modules.cable.model.userCommon;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.userCommon.utaxpoint.bo.UTaxPointBo;
import org.jeecg.modules.cable.controller.userCommon.utaxpoint.vo.UTaxPointVo;
import org.jeecg.modules.cable.entity.systemEcable.EcdTaxpoint;
import org.jeecg.modules.cable.entity.userCommon.EcduTaxpoint;
import org.jeecg.modules.cable.service.userCommon.EcdTaxpointService;
import org.jeecg.modules.cable.service.userCommon.EcduTaxpointService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class EcduTaxpointModel {
    @Resource
    EcduTaxpointService ecduTaxpointService;
    @Resource
    EcdTaxpointService ecdTaxpointService;//系统税点

    //getListAndCount
    public UTaxPointVo getListAndCount(UTaxPointBo bo) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        EcduTaxpoint record = new EcduTaxpoint();
        record.setStartType(bo.getStartType());
        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcduTaxpoint> list = ecduTaxpointService.getList(record);
        for (EcduTaxpoint ecduTaxpoint : list) {
            int ecdtId = ecduTaxpoint.getEcdtId();
            EcdTaxpoint recordEcdTaxpoint = new EcdTaxpoint();
            recordEcdTaxpoint.setEcdtId(ecdtId);
            EcdTaxpoint ecdTaxpoint = ecdTaxpointService.getObject(recordEcdTaxpoint);
            ecduTaxpoint.setEcdTaxpoint(ecdTaxpoint);
        }
        long count = ecduTaxpointService.getCount(record);
        return new UTaxPointVo(list, count);
    }

    //deal
    public String deal(HttpServletRequest request) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        int ecdtId = Integer.parseInt(request.getParameter("ecdtId"));
        String name = request.getParameter("name");//自定义名称
        BigDecimal percentCommon = new BigDecimal(request.getParameter("percentCommon"));//普票税点
        BigDecimal percentSpecial = new BigDecimal(request.getParameter("percentSpecial"));//专票税点
        String description = request.getParameter("description");
        EcduTaxpoint record = new EcduTaxpoint();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setEcdtId(ecdtId);
        EcduTaxpoint ecduTaxpoint = ecduTaxpointService.getObject(record);
        String msg;
        if (ecduTaxpoint == null) {
            record.setEcdtId(ecdtId);
            record.setEcCompanyId(ecUser.getEcCompanyId());
            record.setStartType(true);
            record.setName(name);
            record.setPercentCommon(percentCommon);
            record.setPercentSpecial(percentSpecial);
            record.setDescription(description);
            ecduTaxpointService.insert(record);

            msg = "新增数据成功";
        } else {
            record.setEcdutId(ecduTaxpoint.getEcdutId());
            record.setName(name);
            record.setPercentCommon(percentCommon);
            record.setPercentSpecial(percentSpecial);
            record.setDescription(description);
            ecduTaxpointService.updateByPrimaryKeySelective(record);

            msg = "更新数据成功";
        }
        return msg;
    }

    //start
    public String start(HttpServletRequest request) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        int ecdtId = Integer.parseInt(request.getParameter("ecdtId"));
        EcduTaxpoint record = new EcduTaxpoint();
        record.setEcdtId(ecdtId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcduTaxpoint ecduTaxpoint = ecduTaxpointService.getObject(record);
        String msg;
        if (ecduTaxpoint == null) {
            EcdTaxpoint recordEcdTaxpoint = new EcdTaxpoint();
            recordEcdTaxpoint.setEcdtId(ecdtId);
            EcdTaxpoint ecdTaxpoint = ecdTaxpointService.getObject(recordEcdTaxpoint);
            record.setEcdtId(ecdtId);
            record.setEcCompanyId(ecUser.getEcCompanyId());
            record.setStartType(true);
            record.setName("");
            record.setPercentCommon(ecdTaxpoint.getPercentCommon());
            record.setPercentSpecial(ecdTaxpoint.getPercentSpecial());
            record.setDescription("");
            ecduTaxpointService.insert(record);
            msg = "数据启用成功";
        } else {
            boolean startType = ecduTaxpoint.getStartType();
            if (!startType) {
                startType = true;
                msg = "数据启用成功";
            } else {
                startType = false;
                msg = "数据禁用成功";
            }
            record.setEcdutId(ecduTaxpoint.getEcdutId());
            record.setStartType(startType);
            ecduTaxpointService.updateByPrimaryKeySelective(record);
        }

        return msg;
    }

    //delete
    public void delete(HttpServletRequest request) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        int ecdtId = Integer.parseInt(request.getParameter("ecdtId"));
        EcduTaxpoint record = new EcduTaxpoint();
        record.setEcdtId(ecdtId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        ecduTaxpointService.deletePassEcCompanyIdAndEcdtId(record);
    }

    //getObject
    public EcduTaxpoint getObject(UTaxPointBo bo) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        EcduTaxpoint record = new EcduTaxpoint();
        if (bo.getEcdtId() != null) {
            record.setEcdtId(bo.getEcdtId());
        }
        record.setStartType(bo.getStartType());
        record.setEcCompanyId(ecUser.getEcCompanyId());
        return ecduTaxpointService.getObject(record);
    }

    //dealPercent
    public void dealPercent(HttpServletRequest request) {

        int ecdutId = Integer.parseInt(request.getParameter("ecdutId"));
        EcduTaxpoint record = new EcduTaxpoint();
        record.setEcdutId(ecdutId);
        if (request.getParameter("percentCommon") != null) {
            BigDecimal percentCommon = new BigDecimal(request.getParameter("percentCommon"));
            record.setPercentCommon(percentCommon);
        }
        if (request.getParameter("percentSpecial") != null) {
            BigDecimal percentSpecial = new BigDecimal(request.getParameter("percentSpecial"));
            record.setPercentSpecial(percentSpecial);
        }
        ecduTaxpointService.updateByPrimaryKeySelective(record);

    }
}
