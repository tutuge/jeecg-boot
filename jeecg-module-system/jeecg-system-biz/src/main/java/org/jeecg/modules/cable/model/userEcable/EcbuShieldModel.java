package org.jeecg.modules.cable.model.userEcable;

import org.jeecg.modules.cable.entity.systemEcable.EcbShield;
import org.jeecg.modules.cable.entity.user.EcUser;
import org.jeecg.modules.cable.entity.userEcable.EcbuShield;
import org.jeecg.modules.cable.model.systemEcable.EcbShieldModel;
import org.jeecg.modules.cable.service.systemEcable.EcbShieldService;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userEcable.EcbuShieldService;
import org.jeecg.modules.cable.tools.CommonFunction;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EcbuShieldModel {
    @Resource
    EcbuShieldService ecbuShieldService;
    @Resource
    EcbShieldService ecbShieldService;
    @Resource
    EcUserService ecUserService;
    @Resource
    EcbShieldModel ecbShieldModel;

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
        int ecbsId = Integer.parseInt(request.getParameter("ecbsId"));
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
        EcbuShield record = new EcbuShield();
        record.setEcbsId(ecbsId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuShield ecbuShield = ecbuShieldService.getObject(record);
        if (ecbuShield == null) {//插入
            record.setStartType(false);
            record.setName("");
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            ecbuShieldService.insert(record);
            status = 3;//插入
            code = "200";
            msg = "插入数据";
        } else {
            record.setEcbusId(ecbuShield.getEcbusId());
            if (request.getParameter("unitPrice") != null) {
                record.setUnitPrice(unitPrice);
            }
            if (request.getParameter("density") != null) {
                record.setDensity(density);
            }
            if (request.getParameter("description") != null) {
                record.setDescription(description);
            }
            ecbuShieldService.update(record);
            status = 4;//更新数据
            code = "201";
            msg = "更新数据";
        }
        CommonFunction.getCommonMap(map, status, code, msg);
        ecbShieldModel.loadData(request);//txt文档
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
        int ecbsId = Integer.parseInt(request.getParameter("ecbsId"));
        EcbuShield record = new EcbuShield();
        record.setEcbsId(ecbsId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuShield ecbuShield = ecbuShieldService.getObject(record);
        boolean startType;
        if (ecbuShield == null) {//插入数据
            EcbShield recordEcbShield = new EcbShield();
            recordEcbShield.setEcbsId(ecbsId);
            EcbShield ecbShield = ecbShieldService.getObject(recordEcbShield);
            record.setEcbsId(ecbsId);
            record.setEcCompanyId(ecUser.getEcCompanyId());
            record.setStartType(true);
            record.setName("");
            record.setUnitPrice(ecbShield.getUnitPrice());
            record.setDensity(ecbShield.getDensity());
            record.setDescription("");
            ecbuShieldService.insert(record);
            status = 3;//启用成功
            code = "200";
            msg = "数据启用成功";
        } else {
            startType = ecbuShield.getStartType();
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
            record.setEcbusId(ecbuShield.getEcbusId());
            record.setStartType(startType);
            //log.info(CommonFunction.getGson().toJson(record));
            ecbuShieldService.update(record);
        }
        CommonFunction.getCommonMap(map, status, code, msg);
        ecbShieldModel.loadData(request);//txt文档
        return map;
    }

    //getList
    public Map<String, Object> getList(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        String startType = request.getParameter("startType");
        EcbuShield record = new EcbuShield();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        if ("1".equals(startType)) {
            record.setStartType(true);
        }
        List<EcbuShield> list = ecbuShieldService.getList(record);
        map.put("list", list);
        status = 3;
        code = "200";
        msg = "正常获取数据";
        CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    /***===数据模型===***/
    //deal
    public void deal(EcbuShield record) {
        EcbuShield ecbuShield = ecbuShieldService.getObject(record);
        if (ecbuShield == null) {
            ecbuShieldService.insert(record);
        } else {
            ecbuShieldService.update(record);
        }
    }

    //getObjectPassEcCompanyIdAndEcbsId
    public EcbuShield getObjectPassEcCompanyIdAndEcbsId(int ecCompanyId, int ecbsId) {
        EcbuShield record = new EcbuShield();
        record.setEcCompanyId(ecCompanyId);
        record.setEcbsId(ecbsId);
        return ecbuShieldService.getObject(record);
    }

    //getObjectPassShieldStr 通过屏蔽类型类型获取屏蔽 为计算成本提供数据
    public EcbuShield getObjectPassShieldStr(int ecuId, String objectStr) {
        EcbuShield object = null;
        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        EcbuShield record = new EcbuShield();
        record.setStartType(true);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcbuShield> list = ecbuShieldService.getList(record);
        for (EcbuShield ecbu_shield : list) {
            int ecbsId = ecbu_shield.getEcbsId();
            EcbShield recordEcbShield = new EcbShield();
            recordEcbShield.setEcbsId(ecbsId);
            EcbShield shield = ecbShieldService.getObject(recordEcbShield);
            if (shield.getAbbreviation().equals(objectStr)) {
                object = ecbu_shield;
            }
        }
        return object;
    }

    //deletePassEcCompanyId
    public void deletePassEcCompanyId(int ecCompanyId) {
        EcbuShield record = new EcbuShield();
        record.setEcCompanyId(ecCompanyId);
        ecbuShieldService.delete(record);
    }
}
