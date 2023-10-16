package org.jeecg.modules.cable.model.userEcable;

import org.jeecg.modules.cable.entity.systemEcable.EcbInsulation;
import org.jeecg.modules.cable.entity.user.EcUser;
import org.jeecg.modules.cable.entity.userEcable.EcbuInsulation;
import org.jeecg.modules.cable.model.systemEcable.EcbInsulationModel;
import org.jeecg.modules.cable.service.systemEcable.EcbInsulationService;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userEcable.EcbuInsulationService;
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
public class EcbuInsulationModel {
    @Resource
    EcbuInsulationService ecbuInsulationService;
    @Resource
    EcbInsulationService ecbInsulationService;
    @Resource
    EcUserService ecUserService;
    @Resource
    EcbInsulationModel ecbInsulationModel;


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
        int ecbiId = Integer.parseInt(request.getParameter("ecbiId"));
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
        EcbuInsulation record = new EcbuInsulation();
        record.setEcbiId(ecbiId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuInsulation ecbuInsulation = ecbuInsulationService.getObject(record);
        if (ecbuInsulation == null) {//插入
            record.setStartType(false);
            record.setName("");
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            ecbuInsulationService.insert(record);
            status = 3;//插入
            code = "200";
            msg = "插入数据";
        } else {
            record.setEcbuiId(ecbuInsulation.getEcbuiId());
            if (request.getParameter("unitPrice") != null) {
                record.setUnitPrice(unitPrice);
            }
            if (request.getParameter("density") != null) {
                record.setDensity(density);
            }
            if (request.getParameter("description") != null) {
                record.setDescription(description);
            }
            ecbuInsulationService.update(record);
            status = 4;//更新数据
            code = "201";
            msg = "更新数据";
        }
        CommonFunction.getCommonMap(map, status, code, msg);
        ecbInsulationModel.loadData(request);//加截txt
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
        int ecbiId = Integer.parseInt(request.getParameter("ecbiId"));
        EcbuInsulation record = new EcbuInsulation();
        record.setEcbiId(ecbiId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuInsulation ecbuInsulation = ecbuInsulationService.getObject(record);
        boolean startType;
        if (ecbuInsulation == null) {//插入数据
            EcbInsulation recordEcbInsulation = new EcbInsulation();
            recordEcbInsulation.setEcbiId(ecbiId);
            EcbInsulation ecbInsulation = ecbInsulationService.getObject(recordEcbInsulation);
            record.setEcbiId(ecbiId);
            record.setEcCompanyId(ecUser.getEcCompanyId());
            record.setStartType(true);
            record.setName("");
            record.setUnitPrice(ecbInsulation.getUnitPrice());
            record.setDensity(ecbInsulation.getDensity());
            record.setDescription("");
            ecbuInsulationService.insert(record);
            status = 3;//启用成功
            code = "200";
            msg = "数据启用成功";
        } else {
            startType = ecbuInsulation.getStartType();
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
            record.setEcbuiId(ecbuInsulation.getEcbuiId());
            record.setStartType(startType);
            //System.out.println(CommonFunction.getGson().toJson(record));
            ecbuInsulationService.update(record);
        }
        CommonFunction.getCommonMap(map, status, code, msg);
        ecbInsulationModel.loadData(request);//加截txt
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
        EcbuInsulation record = new EcbuInsulation();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        if ("1".equals(startType)) {
            record.setStartType(true);
        }
        List<EcbuInsulation> list = ecbuInsulationService.getList(record);
        map.put("list", list);
        status = 3;
        code = "200";
        msg = "正常获取数据";
        CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    /***===数据模型===***/
    //deal
    public void deal(EcbuInsulation record) {
        EcbuInsulation ecbuInsulation = ecbuInsulationService.getObject(record);
        if (ecbuInsulation == null) {
            ecbuInsulationService.insert(record);
        } else {
            ecbuInsulationService.update(record);
        }
    }

    //getObjectPassEcCompanyIdAndEcbiId
    public EcbuInsulation getObjectPassEcCompanyIdAndEcbiId(int ecCompanyId, int ecbiId) {
        EcbuInsulation record = new EcbuInsulation();
        record.setEcCompanyId(ecCompanyId);
        record.setEcbiId(ecbiId);
        return ecbuInsulationService.getObject(record);
    }

    //getInsulationPassInsulationStr 通过绝缘类型获取绝缘 为计算成本提供数据
    public EcbuInsulation getInsulationPassInsulationStr(int ecuId, String insulationStr) {
        EcbuInsulation object = null;
        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        EcbuInsulation record = new EcbuInsulation();
        record.setStartType(true);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcbuInsulation> list = ecbuInsulationService.getList(record);
        //log.info("list + " + CommonFunction.getGson().toJson(list));
        for (EcbuInsulation ecbuInsulation : list) {
            int ecbiId = ecbuInsulation.getEcbiId();
            EcbInsulation recordEcbInsulation = new EcbInsulation();
            recordEcbInsulation.setEcbiId(ecbiId);
            EcbInsulation insulation = ecbInsulationService.getObject(recordEcbInsulation);
            if (insulation.getAbbreviation().equals(insulationStr)) {
                object = ecbuInsulation;
            }
        }
        return object;
    }

    //deletePassEcCompanyId
    public void deletePassEcCompanyId(int ecCompanyId) {
        EcbuInsulation record = new EcbuInsulation();
        record.setEcCompanyId(ecCompanyId);
        ecbuInsulationService.delete(record);
    }

    //getObjectPassEcbuiId
    public EcbuInsulation getObjectPassEcbuiId(int ecbuiId) {
        EcbuInsulation record = new EcbuInsulation();
        record.setEcbuiId(ecbuiId);
        return ecbuInsulationService.getObject(record);
    }

    //getInsulationPassFullName 通过绝缘类型获取绝缘
    public EcbuInsulation getInsulationPassFullName(int ecuId, String fullName) {
        EcbuInsulation object = null;
        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        EcbuInsulation record = new EcbuInsulation();
        record.setStartType(true);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcbuInsulation> list = ecbuInsulationService.getList(record);
        //log.info("list + " + CommonFunction.getGson().toJson(list));
        for (EcbuInsulation ecbuInsulation : list) {
            int ecbiId = ecbuInsulation.getEcbiId();
            EcbInsulation recordEcbInsulation = new EcbInsulation();
            recordEcbInsulation.setEcbiId(ecbiId);
            EcbInsulation insulation = ecbInsulationService.getObject(recordEcbInsulation);
            if (insulation.getFullName().equals(fullName)) {
                object = ecbuInsulation;
            }
        }
        return object;
    }
}
