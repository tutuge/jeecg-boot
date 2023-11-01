package org.jeecg.modules.cable.model.userCommon;

import jakarta.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.userCommon.taxpoint.bo.TaxPointBaseBo;
import org.jeecg.modules.cable.controller.userCommon.taxpoint.bo.TaxPointBo;
import org.jeecg.modules.cable.controller.userCommon.taxpoint.bo.TaxPointDealBo;
import org.jeecg.modules.cable.controller.userCommon.taxpoint.bo.TaxPointSortBo;
import org.jeecg.modules.cable.controller.userCommon.taxpoint.vo.TaxPointVo;
import org.jeecg.modules.cable.entity.systemEcable.EcdTaxPoint;
import org.jeecg.modules.cable.entity.userCommon.EcduTaxpoint;
import org.jeecg.modules.cable.service.userCommon.EcdTaxpointService;
import org.jeecg.modules.cable.service.userCommon.EcduTaxpointService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
        EcdTaxPoint record = new EcdTaxPoint();
        record.setStartType(bo.getStartType());
        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcdTaxPoint> list = ecdTaxpointService.getList(record);
        Long count = ecdTaxpointService.getCount(record);
        return new TaxPointVo(list, count);
    }

    //getObject
    public EcdTaxPoint getObject(TaxPointBaseBo bo) {
        EcdTaxPoint record = new EcdTaxPoint();
        Integer ecdtId1 = bo.getEcdtId();
        record.setEcdtId(ecdtId1);
        EcdTaxPoint object = ecdTaxpointService.getObject(record);
        if (object != null) {
            Integer ecdtId = object.getEcdtId();
            EcduTaxpoint recordEcduTaxpoint = new EcduTaxpoint();
            recordEcduTaxpoint.setEcdtId(ecdtId);
            EcduTaxpoint ecduTaxpoint = ecduTaxpointService.getObject(recordEcduTaxpoint);
            object.setEcduTaxpoint(ecduTaxpoint);
        }
        return object;
    }

    public String deal(TaxPointDealBo bo) {
        // 获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        Integer ecdtId = bo.getEcdtId();
        String pointName = bo.getPointName();// 自定义名称
        BigDecimal percentCommon = bo.getPercentCommon();// 普票税点
        BigDecimal percentSpecial = bo.getPercentSpecial();// 专票税点
        String description = bo.getDescription();

        EcdTaxPoint record = new EcdTaxPoint();
        EcdTaxPoint taxPoint = ecdTaxpointService.getObject(record);
        String msg;
        if (ecdtId == null) {
            int sortId = 1;
            if (taxPoint != null) {
                sortId = taxPoint.getSortId() + 1;
            }
            record.setEcCompanyId(ecUser.getEcCompanyId());
            record.setStartType(false);
            record.setSortId(sortId);
            record.setPointName(pointName);
            record.setPercentCommon(percentCommon);
            record.setPercentSpecial(percentSpecial);
            record.setDescription(description);
            ecdTaxpointService.insert(record);
            msg = "新增数据成功";
        } else {
            record.setEcdtId(ecdtId);
            record.setPointName(pointName);
            record.setPercentCommon(percentCommon);
            record.setPercentSpecial(percentSpecial);
            record.setDescription(description);
            ecdTaxpointService.update(record);
            msg = "更新数据成功";
        }
        return msg;
    }

    public void delete(TaxPointBaseBo bo) {
        ecdTaxpointService.delete(bo.getEcdtId());
    }

    public String start(TaxPointBaseBo bo) {
        Integer ecdtId = bo.getEcdtId();

        EcdTaxPoint record = new EcdTaxPoint();
        record.setEcdtId(ecdtId);
        EcdTaxPoint taxPoint = ecdTaxpointService.getObject(record);
        Boolean startType = taxPoint.getStartType();
        String msg = "";
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        record = new EcdTaxPoint();
        record.setEcdtId(ecdtId);
        record.setStartType(startType);
        ecdTaxpointService.update(record);
        return msg;
    }

    @Transactional(rollbackFor = Exception.class)
    public void sort(List<TaxPointSortBo> bos) {
        for (TaxPointSortBo bo : bos) {
            Integer sortId = bo.getSortId();
            Integer ecbuaId = bo.getEcdtId();
            EcdTaxPoint record = new EcdTaxPoint();
            record.setEcdtId(ecbuaId);
            record.setSortId(sortId);
            ecdTaxpointService.update(record);
        }
    }
}
