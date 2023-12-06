package org.jeecg.modules.cable.model.userCommon;

import jakarta.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.userCommon.taxpoint.bo.EcduDealPercentBo;
import org.jeecg.modules.cable.controller.userCommon.taxpoint.bo.EcduTaxPointBaseBo;
import org.jeecg.modules.cable.controller.userCommon.taxpoint.bo.EcduTaxPointBo;
import org.jeecg.modules.cable.controller.userCommon.taxpoint.bo.EcduTaxPointDealBo;
import org.jeecg.modules.cable.controller.userCommon.taxpoint.vo.EcduTaxPointVo;
import org.jeecg.modules.cable.entity.systemEcable.EcdTaxPoint;
import org.jeecg.modules.cable.entity.userCommon.EcduTaxPoint;
import org.jeecg.modules.cable.service.userCommon.EcdTaxPointService;
import org.jeecg.modules.cable.service.userCommon.EcduTaxPointService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class EcduTaxPointModel {
    @Resource
    EcduTaxPointService ecduTaxpointService;
    @Resource
    EcdTaxPointService ecdTaxpointService;// 系统税点

    public EcduTaxPointVo getListAndCount(EcduTaxPointBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcduTaxPoint record = new EcduTaxPoint();
        record.setStartType(bo.getStartType());
        record.setEcCompanyId(sysUser.getEcCompanyId());
        List<EcduTaxPoint> list = ecduTaxpointService.getList(record);
        for (EcduTaxPoint ecduTaxpoint : list) {
            Integer ecdtId = ecduTaxpoint.getEcdtId();
            EcdTaxPoint recordEcdTaxPoint = new EcdTaxPoint();
            recordEcdTaxPoint.setEcdtId(ecdtId);
            EcdTaxPoint ecdTaxpoint = ecdTaxpointService.getObject(recordEcdTaxPoint);
            ecduTaxpoint.setEcdTaxpoint(ecdTaxpoint);
        }
        long count = ecduTaxpointService.getCount(record);
        return new EcduTaxPointVo(list, count);
    }

    @Transactional(rollbackFor = Exception.class)
    public String deal(EcduTaxPointDealBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecdtId = bo.getEcdtId();
        String name = bo.getName();// 自定义名称
        BigDecimal percentCommon = bo.getPercentCommon();// 普票税点
        BigDecimal percentSpecial = bo.getPercentSpecial();// 专票税点
        String description = bo.getDescription();
        EcduTaxPoint record = new EcduTaxPoint();
        record.setEcCompanyId(sysUser.getEcCompanyId());
        record.setEcdtId(ecdtId);
        EcduTaxPoint ecduTaxpoint = ecduTaxpointService.getObject(record);
        String msg;
        if (ecduTaxpoint == null) {
            record.setEcdtId(ecdtId);
            record.setEcCompanyId(sysUser.getEcCompanyId());
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

    public String start(EcduTaxPointBaseBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecdtId = bo.getEcdtId();
        EcduTaxPoint record = new EcduTaxPoint();
        record.setEcdtId(ecdtId);
        record.setEcCompanyId(sysUser.getEcCompanyId());
        EcduTaxPoint ecduTaxpoint = ecduTaxpointService.getObject(record);
        String msg;
        if (ecduTaxpoint == null) {
            EcdTaxPoint recordEcdTaxPoint = new EcdTaxPoint();
            recordEcdTaxPoint.setEcdtId(ecdtId);
            EcdTaxPoint ecdTaxpoint = ecdTaxpointService.getObject(recordEcdTaxPoint);
            record.setEcdtId(ecdtId);
            record.setEcCompanyId(sysUser.getEcCompanyId());
            record.setStartType(true);
            record.setName("");
            record.setPercentCommon(ecdTaxpoint.getPercentCommon());
            record.setPercentSpecial(ecdTaxpoint.getPercentSpecial());
            record.setDescription("");
            ecduTaxpointService.insert(record);
            msg = "数据启用成功";
        } else {
            Boolean startType = ecduTaxpoint.getStartType();
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

    @Transactional(rollbackFor = Exception.class)
    public void delete(EcduTaxPointBaseBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecdtId = bo.getEcdtId();
        EcduTaxPoint record = new EcduTaxPoint();
        record.setEcdtId(ecdtId);
        record.setEcCompanyId(sysUser.getEcCompanyId());
        ecduTaxpointService.deletePassEcCompanyIdAndEcdtId(record);
    }


    public EcduTaxPoint getObject(EcduTaxPointBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcduTaxPoint record = new EcduTaxPoint();
        if (bo.getEcdtId() != null) {
            record.setEcdtId(bo.getEcdtId());
        }
        record.setStartType(bo.getStartType());
        record.setEcCompanyId(sysUser.getEcCompanyId());
        return ecduTaxpointService.getObject(record);
    }

    // dealPercent
    public void dealPercent(EcduDealPercentBo bo) {
        Integer ecdutId = bo.getEcdutId();
        EcduTaxPoint record = new EcduTaxPoint();
        record.setEcdutId(ecdutId);
        record.setPercentCommon(bo.getPercentCommon());
        record.setPercentSpecial(bo.getPercentSpecial());
        ecduTaxpointService.updateByPrimaryKeySelective(record);

    }
}
