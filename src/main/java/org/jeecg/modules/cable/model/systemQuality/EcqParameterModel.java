package org.jeecg.modules.cable.model.systemQuality;

import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.controller.systemQuality.parameter.bo.ParameterBaseBo;
import org.jeecg.modules.cable.controller.systemQuality.parameter.bo.ParameterBo;
import org.jeecg.modules.cable.controller.systemQuality.parameter.bo.ParameterDealBo;
import org.jeecg.modules.cable.controller.systemQuality.parameter.vo.ParameterVo;
import org.jeecg.modules.cable.entity.systemQuality.EcqParameter;
import org.jeecg.modules.cable.service.systemQuality.EcqParameterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class EcqParameterModel {
    @Resource
    private EcqParameterService ecqParameterService;


    public ParameterVo getListAndCount(ParameterBo bo) {
        Integer ecqulId = bo.getEcqlId();
        EcqParameter record = new EcqParameter();
        record.setEcqlId(ecqulId);
        List<EcqParameter> list = ecqParameterService.getList(record);
        long count = ecqParameterService.getCount(record);
        return new ParameterVo(list, count);
    }


    public EcqParameter getObject(ParameterBaseBo bo) {
        EcqParameter record = new EcqParameter();
        record.setEcqpId(bo.getEcqpId());
        return ecqParameterService.getObject(record);
    }


    @Transactional(rollbackFor = Exception.class)
    public String deal(ParameterDealBo bo) {
        Integer ecqupId = bo.getEcqpId();
        Integer ecqulId = bo.getEcqlId();
        Integer ecbusId = bo.getEcbsId();
        BigDecimal length = bo.getLength();
        BigDecimal cost = bo.getCost();
        String description = bo.getDescription();

        String msg;
        EcqParameter record = new EcqParameter();
        record.setEcqlId(ecqulId);
        record.setEcbsId(ecbusId);

        if (ObjectUtil.isNull(ecqupId)) {// 插入
            record.setLength(length);
            record.setCost(cost);
            record.setDescription(description);
            ecqParameterService.insert(record);
            msg = "正常插入数据";
        } else {// 更新
            record.setEcqpId(ecqupId);
            record.setLength(length);
            record.setCost(cost);
            record.setDescription(description);
            ecqParameterService.updateByPrimaryKeySelective(record);
            msg = "正常更新数据";
        }
        return msg;
    }

    public void delete(ParameterBaseBo bo) {
        ecqParameterService.delete(bo.getEcqpId());
    }
}
