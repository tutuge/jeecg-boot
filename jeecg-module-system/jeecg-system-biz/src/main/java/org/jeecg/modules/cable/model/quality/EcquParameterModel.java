package org.jeecg.modules.cable.model.quality;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.quality.parameter.vo.ParameterVo;
import org.jeecg.modules.cable.entity.quality.EcquParameter;
import org.jeecg.modules.cable.service.quality.EcquParameterService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EcquParameterModel {
    @Resource
    EcquParameterService ecquParameterService;

    //getListAndCount
    public ParameterVo getListAndCount(HttpServletRequest request) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        int ecqulId = Integer.parseInt(request.getParameter("ecqulId"));
        EcquParameter record = new EcquParameter();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setEcqulId(ecqulId);
        List<EcquParameter> list = ecquParameterService.getList(record);
        long count = ecquParameterService.getCount(record);

        return new ParameterVo(list, count);
    }

    //getObject
    public EcquParameter getObject(HttpServletRequest request) {

        EcquParameter record = new EcquParameter();
        if (request.getParameter("ecqupId") != null) {
            int ecqupId = Integer.parseInt(request.getParameter("ecqupId"));
            record.setEcqupId(ecqupId);
        }
        return ecquParameterService.getObject(record);
    }

    //deal
    public String deal(HttpServletRequest request) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        int ecqupId = Integer.parseInt(request.getParameter("ecqupId"));
        int ecqulId = Integer.parseInt(request.getParameter("ecqulId"));
        int ecbusId = Integer.parseInt(request.getParameter("ecbusId"));
        BigDecimal length = new BigDecimal(request.getParameter("length"));
        BigDecimal cost = new BigDecimal(request.getParameter("cost"));
        String description = request.getParameter("description");
        EcquParameter record = new EcquParameter();
        record.setEcqulId(ecqulId);
        record.setEcbusId(ecbusId);
        EcquParameter ecquParameter = ecquParameterService.getObjectPassEcqulIdAndEcbusId(record);
        String msg;
        if (ecquParameter != null) {
            throw new RuntimeException("名称已占用");
        } else {
            if (ecqupId == 0) {//插入
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
            } else {//更新
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
