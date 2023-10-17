package org.jeecg.modules.cable.model.quality;

import org.jeecg.modules.cable.entity.quality.EcquParameter;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.modules.cable.service.quality.EcquParameterService;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.tools.CommonFunction;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EcquParameterModel {
    @Resource
    EcquParameterService ecquParameterService;
    @Resource
    EcUserService ecUserService;

    //getListAndCount
    public Map<String, Object> getListAndCount(HttpServletRequest request) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        int ecqulId = Integer.parseInt(request.getParameter("ecqulId"));
        EcquParameter record = new EcquParameter();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setEcqulId(ecqulId);
        List<EcquParameter> list = ecquParameterService.getList(record);
        long count = ecquParameterService.getCount(record);
        map.put("list", list);
        map.put("count", count);
        status = 3;//正常获取数据
        code = "200";
        msg = "正常获取数据";
        CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }

    //getObject
    public Map<String, Object> getObject(HttpServletRequest request) {

        EcquParameter record = new EcquParameter();
        if (request.getParameter("ecqupId") != null) {
            int ecqupId = Integer.parseInt(request.getParameter("ecqupId"));
            record.setEcqupId(ecqupId);
        }
        map.put("object", ecquParameterService.getObject(record));
        status = 3;//正常获取数据
        code = "200";
        msg = "正常获取数据";
        CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }

    //deal
    public Map<String, Object> deal(HttpServletRequest request) {
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
                status = 4;//正常插入数据
                code = "200";
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
        CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }
}
