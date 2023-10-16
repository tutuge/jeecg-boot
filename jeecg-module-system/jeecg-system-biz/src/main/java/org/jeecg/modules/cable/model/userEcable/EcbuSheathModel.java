package org.jeecg.modules.cable.model.userEcable;

import org.jeecg.modules.cable.entity.systemEcable.EcbSheath;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.modules.cable.entity.userEcable.EcbuSheath;
import org.jeecg.modules.cable.service.systemEcable.EcbSheathService;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userEcable.EcbuSheathService;
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
public class EcbuSheathModel {
    @Resource
    EcbuSheathService ecbuSheathService;
    @Resource
    EcbSheathService ecbSheathService;
    @Resource
    EcUserService ecUserService;

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
        EcbuSheath record = new EcbuSheath();
        record.setEcbsId(ecbsId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuSheath ecbuSheath = ecbuSheathService.getObject(record);
        if (ecbuSheath == null) {//插入
            record.setStartType(false);
            record.setName("");
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            ecbuSheathService.insert(record);
            status = 3;//插入
            code = "200";
            msg = "插入数据";
        } else {
            record.setEcbusId(ecbuSheath.getEcbusId());
            if (request.getParameter("unitPrice") != null) {
                record.setUnitPrice(unitPrice);
            }
            if (request.getParameter("density") != null) {
                record.setDensity(density);
            }
            if (request.getParameter("description") != null) {
                record.setDescription(description);
            }
            ecbuSheathService.update(record);
            status = 4;//更新数据
            code = "201";
            msg = "更新数据";
        }
        CommonFunction.getCommonMap(map, status, code, msg);
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
        EcbuSheath record = new EcbuSheath();
        record.setEcbsId(ecbsId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuSheath ecbuSheath = ecbuSheathService.getObject(record);
        boolean startType;
        if (ecbuSheath == null) {//插入数据
            EcbSheath recordEcbSheath = new EcbSheath();
            recordEcbSheath.setEcbsId(ecbsId);
            EcbSheath ecbSheath = ecbSheathService.getObject(recordEcbSheath);
            record.setEcbsId(ecbsId);
            record.setEcCompanyId(ecUser.getEcCompanyId());
            record.setStartType(true);
            record.setName("");
            record.setUnitPrice(ecbSheath.getUnitPrice());
            record.setDensity(ecbSheath.getDensity());
            record.setDescription("");
            ecbuSheathService.insert(record);
            status = 3;//启用成功
            code = "200";
            msg = "数据启用成功";
        } else {
            startType = ecbuSheath.getStartType();
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
            record.setEcbusId(ecbuSheath.getEcbusId());
            record.setStartType(startType);
            ecbuSheathService.update(record);
        }
        CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    //getList
    public Map<String, Object> getList(HttpServletRequest request) {
LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        EcbuSheath record = new EcbuSheath();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        if ("1".equals(startType)) {
            record.setStartType(true);
        }
        List<EcbuSheath> list = ecbuSheathService.getList(record);
        map.put("list", list);
        status = 3;
        code = "200";
        msg = "正常获取数据";
        CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    /***===数据模型===***/
    //deal
    public void deal(EcbuSheath record) {
        EcbuSheath ecbuSheath = ecbuSheathService.getObject(record);
        if (ecbuSheath == null) {
            ecbuSheathService.insert(record);
        } else {
            ecbuSheathService.update(record);
        }
    }

    //getObjectPassEcCompanyIdAndEcbsId
    public EcbuSheath getObjectPassEcCompanyIdAndEcbsId(int ecCompanyId, int ecbsId) {
        EcbuSheath record = new EcbuSheath();
        record.setEcCompanyId(ecCompanyId);
        record.setEcbsId(ecbsId);
        return ecbuSheathService.getObject(record);
    }

    //getObjectPassSheathStr 通过屏蔽类型类型获取屏蔽 为计算成本提供数据
    public EcbuSheath getObjectPassSheathStr(int ecuId, String objectStr) {
        EcbuSheath object = null;
        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        EcbuSheath record = new EcbuSheath();
        record.setStartType(true);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcbuSheath> list = ecbuSheathService.getList(record);
        for (EcbuSheath ecbu_sheath : list) {
            int ecbsid = ecbu_sheath.getEcbsId();
            EcbSheath recordEcbSheath = new EcbSheath();
            recordEcbSheath.setEcbsId(ecbsid);
            EcbSheath sheath = ecbSheathService.getObject(recordEcbSheath);
            if ("聚氯乙烯（一代）".equals(objectStr)) {
                objectStr = "D1";
            }
            if (sheath.getAbbreviation().equals(objectStr)) {
                object = ecbu_sheath;
            }
        }
        return object;
    }

    //deletePassEcCompanyId
    public void deletePassEcCompanyId(int ecCompanyId) {
        EcbuSheath record = new EcbuSheath();
        record.setEcCompanyId(ecCompanyId);
        ecbuSheathService.delete(record);
    }

    //getObjectPassEcbusid
    public EcbuSheath getObjectPassEcbusid(int ecbusid) {
        EcbuSheath record = new EcbuSheath();
        record.setEcbusId(ecbusid);
        return ecbuSheathService.getObject(record);
    }
}
