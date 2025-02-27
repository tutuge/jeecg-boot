package org.jeecg.modules.cable.model.userQuality;

import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.userQuality.parameter.bo.ParameterBaseBo;
import org.jeecg.modules.cable.controller.userQuality.parameter.bo.ParameterBo;
import org.jeecg.modules.cable.controller.userQuality.parameter.bo.ParameterDealBo;
import org.jeecg.modules.cable.controller.userQuality.parameter.vo.ParameterVo;
import org.jeecg.modules.cable.entity.userQuality.EcquParameter;
import org.jeecg.modules.cable.service.userQuality.EcquParameterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class EcquParameterModel {
    @Resource
    EcquParameterService ecquParameterService;


    public ParameterVo getListAndCount(ParameterBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecqulId = bo.getEcqulId();

        EcquParameter record = new EcquParameter();
        record.setEcCompanyId(sysUser.getEcCompanyId());
        record.setEcqulId(ecqulId);
        List<EcquParameter> list = ecquParameterService.getList(record);
        long count = ecquParameterService.getCount(record);
        return new ParameterVo(list, count);
    }


    public EcquParameter getObject(ParameterBaseBo bo) {
        EcquParameter record = new EcquParameter();
        record.setEcqupId(bo.getEcqupId());
        return ecquParameterService.getObject(record);
    }


    @Transactional(rollbackFor = Exception.class)
    public String deal(ParameterDealBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecqupId = bo.getEcqupId();
        Integer ecqulId = bo.getEcqulId();
        Integer ecbusId = bo.getEcbusId();
        BigDecimal length = bo.getLength();
        BigDecimal cost = bo.getCost();
        String description = bo.getDescription();

        String msg;
        EcquParameter record = new EcquParameter();
        record.setEcqulId(ecqulId);
        record.setEcbusId(ecbusId);
        record.setEcCompanyId(sysUser.getEcCompanyId());
        if (ObjectUtil.isNull(ecqupId)) {// 插入
            record.setLength(length);
            record.setCost(cost);
            record.setDescription(description);
            ecquParameterService.insert(record);
            msg = "正常插入数据";
        } else {// 更新
            record.setEcqupId(ecqupId);
            record.setLength(length);
            record.setCost(cost);
            record.setDescription(description);
            ecquParameterService.updateByPrimaryKeySelective(record);
            msg = "正常更新数据";
        }
        return msg;
    }

    public void delete(ParameterBaseBo bo) {
        ecquParameterService.deleteByPrimaryKey(bo.getEcqupId());
    }
}
