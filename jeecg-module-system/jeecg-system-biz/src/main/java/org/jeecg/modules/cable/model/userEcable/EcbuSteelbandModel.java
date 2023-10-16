package org.jeecg.modules.cable.model.userEcable;

import org.jeecg.modules.cable.entity.systemEcable.EcbSteelband;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.modules.cable.entity.userEcable.EcbuSteelband;
import org.jeecg.modules.cable.model.systemEcable.EcbSteelbandModel;
import org.jeecg.modules.cable.service.systemEcable.EcbSteelbandService;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userEcable.EcbuSteelbandService;
import org.jeecg.modules.cable.tools.CommonFunction;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EcbuSteelbandModel {
    @Resource
    EcbuSteelbandService ecbuSteelbandService;
    @Resource
    EcbSteelbandService ecbSteelbandService;
    @Resource
    EcUserService ecUserService;
    @Resource
    EcbSteelbandModel ecbSteelbandModel;

    //deal
    public Map<String, Object> deal(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        int ecbsbId = Integer.parseInt(request.getParameter("ecbsbId"));
        BigDecimal unitPrice = new BigDecimal("0");
        if (request.getParameter("unitPrice") != null) {
            unitPrice = new BigDecimal(request.getParameter("unitPrice"));//单价
        }
        BigDecimal density = new BigDecimal("0");
        if (request.getParameter("density") != null) {
            density = new BigDecimal(request.getParameter("density"));//密度
        }
        String description = "";
        if (request.getParameter("description") != null) {
            description = request.getParameter("description");
        }
        EcbuSteelband record = new EcbuSteelband();
        record.setEcbsbId(ecbsbId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuSteelband ecbuSteelband = ecbuSteelbandService.getObject(record);
        if (ecbuSteelband == null) {//插入
            record.setStartType(false);
            record.setName("");
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            ecbuSteelbandService.insert(record);
            status = 3;//插入
            code = "200";
            msg = "插入数据";
        } else {
            record.setEcbusId(ecbuSteelband.getEcbusId());
            if (request.getParameter("unitPrice") != null) {
                record.setUnitPrice(unitPrice);
            }
            if (request.getParameter("density") != null) {
                record.setDensity(density);
            }
            if (request.getParameter("description") != null) {
                record.setDescription(description);
            }
            ecbuSteelbandService.update(record);
            status = 4;//更新数据
            code = "201";
            msg = "更新数据";
        }
        CommonFunction.getCommonMap(map, status, code, msg);
        ecbSteelbandModel.loadData(request);//txt文档
        return map;
    }

    //start
    public Map<String, Object> start(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        int ecbsbId = Integer.parseInt(request.getParameter("ecbsbId"));
        EcbuSteelband record = new EcbuSteelband();
        record.setEcbsbId(ecbsbId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuSteelband ecbuSteelband = ecbuSteelbandService.getObject(record);
        boolean startType;
        if (ecbuSteelband == null) {//插入数据
            EcbSteelband recordEcbSteelband = new EcbSteelband();
            recordEcbSteelband.setEcbsbId(ecbsbId);
            EcbSteelband ecbSteelband = ecbSteelbandService.getObject(recordEcbSteelband);
            record.setEcbsbId(ecbsbId);
            record.setEcCompanyId(ecUser.getEcCompanyId());
            record.setStartType(true);
            record.setName("");
            record.setUnitPrice(ecbSteelband.getUnitPrice());
            record.setDensity(ecbSteelband.getDensity());
            record.setDescription("");
            ecbuSteelbandService.insert(record);
            status = 3;//启用成功
            code = "200";
            msg = "数据启用成功";
        } else {
            startType = ecbuSteelband.getStartType();
            if (!startType) {
                startType = true;
                status = 3;
                code = "200";
                msg = "数据启用成功";
            } else {
                startType = false;
                status = 4;
                code = "201";
                msg = "数据禁用成功";
            }
            record.setEcbusId(ecbuSteelband.getEcbusId());
            record.setStartType(startType);
            //System.out.println(CommonFunction.getGson().toJson(record));
            ecbuSteelbandService.update(record);
        }
        CommonFunction.getCommonMap(map, status, code, msg);
        ecbSteelbandModel.loadData(request);//txt文档
        return map;
    }

    //getList
    public Map<String, Object> getList(HttpServletRequest request) {
LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        EcbuSteelband record = new EcbuSteelband();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        if ("1".equals(startType)) {
            record.setStartType(true);
        }
        List<EcbuSteelband> list = ecbuSteelbandService.getList(record);
        map.put("list", list);
        status = 3;
        code = "200";
        msg = "正常获取数据";
        CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    /***===数据模型===***/
    //deal
    public void deal(EcbuSteelband record) {
        EcbuSteelband ecbuSteelband = ecbuSteelbandService.getObject(record);
        if (ecbuSteelband == null) {
            ecbuSteelbandService.insert(record);
        } else {
            ecbuSteelbandService.update(record);
        }
    }

    //getObjectPassEcCompanyIdAndEcbsbId
    public EcbuSteelband getObjectPassEcCompanyIdAndEcbsbId(int ecCompanyId, int ecbsbId) {
        EcbuSteelband record = new EcbuSteelband();
        record.setEcCompanyId(ecCompanyId);
        record.setEcbsbId(ecbsbId);
        return ecbuSteelbandService.getObject(record);
    }

    //getObjectPassSteelbandStr 通过钢带类型类型获取钢带 为计算成本提供数据
    public EcbuSteelband getObjectPassSteelbandStr(int ecuId, String objectStr) {
        EcbuSteelband object = null;
        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        EcbuSteelband record = new EcbuSteelband();
        record.setStartType(true);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcbuSteelband> list = ecbuSteelbandService.getList(record);
        for (EcbuSteelband ecbu_steelband : list) {
            int ecbsbId = ecbu_steelband.getEcbsbId();
            EcbSteelband recordEcbSteelband = new EcbSteelband();
            recordEcbSteelband.setEcbsbId(ecbsbId);
            EcbSteelband steelband = ecbSteelbandService.getObject(recordEcbSteelband);
            if (steelband.getAbbreviation().equals(objectStr)) {
                object = ecbu_steelband;
            }
        }
        return object;
    }

    //deletePassEcCompanyId
    public void deletePassEcCompanyId(int ecCompanyId) {
        EcbuSteelband record = new EcbuSteelband();
        record.setEcCompanyId(ecCompanyId);
        ecbuSteelbandService.delete(record);
    }

    //getObjectPassEcbusbId
    public EcbuSteelband getObjectPassEcbusbId(int ecbusbId) {
        EcbuSteelband record = new EcbuSteelband();
        record.setEcbusId(ecbusbId);
        return ecbuSteelbandService.getObject(record);
    }
}
