package org.jeecg.modules.cable.model.quality;

import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.quality.parameter.bo.ParameterBaseBo;
import org.jeecg.modules.cable.controller.quality.parameter.bo.ParameterBo;
import org.jeecg.modules.cable.controller.quality.parameter.bo.ParameterDealBo;
import org.jeecg.modules.cable.controller.quality.parameter.vo.ParameterVo;
import org.jeecg.modules.cable.entity.quality.EcquParameter;
import org.jeecg.modules.cable.service.quality.EcquParameterService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class EcquParameterModel {
    @Resource
    EcquParameterService ecquParameterService;

    // getListAndCount
    public ParameterVo getListAndCount(ParameterBo bo) {
        // 获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer ecqulId = bo.getEcqulId();

        EcquParameter record = new EcquParameter();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setEcqulId(ecqulId);
        List<EcquParameter> list = ecquParameterService.getList(record);
        long count = ecquParameterService.getCount(record);
        return new ParameterVo(list, count);
    }

    // getObject
    public EcquParameter getObject(ParameterBaseBo bo) {
        EcquParameter record = new EcquParameter();
        record.setEcqupId(bo.getEcqupId());
        return ecquParameterService.getObject(record);
    }

    // deal
    public String deal(ParameterDealBo bo) {
        // 获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        Integer ecqupId = bo.getEcqupId();
        Integer ecqulId = bo.getEcqulId();
        Integer ecbusId = bo.getEcbusId();
        BigDecimal length = bo.getLength();
        BigDecimal cost = bo.getCost();
        String description = bo.getDescription();


        EcquParameter record = new EcquParameter();
        record.setEcqulId(ecqulId);
        record.setEcbusId(ecbusId);
        EcquParameter ecquParameter = ecquParameterService.getObjectPassEcqulIdAndEcbusId(record);
        String msg;
        if (ecquParameter != null) {
            throw new RuntimeException("名称已占用");
        } else {
            if (ObjectUtil.isNull(ecqupId)) {// 插入
                record = new EcquParameter();
                record.setEcCompanyId(ecUser.getEcCompanyId());
                record.setEcqulId(ecqulId);
                record.setLength(length);
                record.setCost(cost);
                record.setDescription(description);
                record.setEcbusId(ecbusId);
                log.info(CommonFunction.getGson().toJson(record));
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
        }

        return msg;
    }
}
