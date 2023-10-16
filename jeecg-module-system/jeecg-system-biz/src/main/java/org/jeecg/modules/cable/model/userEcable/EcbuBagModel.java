package org.jeecg.modules.cable.model.userEcable;

import org.jeecg.modules.cable.entity.systemEcable.EcbBag;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.modules.cable.entity.userEcable.EcbuBag;
import org.jeecg.modules.cable.model.systemEcable.EcbBagModel;
import org.jeecg.modules.cable.service.systemEcable.EcbBagService;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userEcable.EcbuBagService;
import org.jeecg.modules.cable.tools.CommonFunction;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EcbuBagModel {
    @Resource
    EcbuBagService ecbuBagService;
    @Resource
    EcbBagService ecbBagService;
    @Resource
    EcUserService ecUserService;
    @Resource
    EcbBagModel ecbBagModel;

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
        int ecbbId = Integer.parseInt(request.getParameter("ecbbId"));
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
        EcbuBag record = new EcbuBag();
        record.setEcbbId(ecbbId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuBag ecbuBag = ecbuBagService.getObject(record);
        if (ecbuBag == null) {//插入
            record.setStartType(false);
            record.setName("");
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            ecbuBagService.insert(record);
            status = 3;//插入
            code = "200";
            msg = "插入数据";
        } else {
            record.setEcbubId(ecbuBag.getEcbubId());
            if (request.getParameter("unitPrice") != null) {
                record.setUnitPrice(unitPrice);
            }
            if (request.getParameter("density") != null) {
                record.setDensity(density);
            }
            if (request.getParameter("description") != null) {
                record.setDescription(description);
            }
            ecbuBagService.update(record);
            status = 4;//更新数据
            code = "201";
            msg = "更新数据";
        }
        CommonFunction.getCommonMap(map, status, code, msg);
        ecbBagModel.loadData(request);//txt文档
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
        int ecbbId = Integer.parseInt(request.getParameter("ecbbId"));
        EcbuBag record = new EcbuBag();
        record.setEcbbId(ecbbId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuBag ecbuBag = ecbuBagService.getObject(record);
        boolean startType;
        if (ecbuBag == null) {//插入数据
            EcbBag recordEcbBag = new EcbBag();
            recordEcbBag.setEcbbId(ecbbId);
            EcbBag ecbBag = ecbBagService.getObject(recordEcbBag);
            record.setEcbbId(ecbbId);
            record.setEcCompanyId(ecUser.getEcCompanyId());
            record.setStartType(true);
            record.setName("");
            record.setUnitPrice(ecbBag.getUnitPrice());
            record.setDensity(ecbBag.getDensity());
            record.setDescription("");
            ecbuBagService.insert(record);
            status = 3;//启用成功
            code = "200";
            msg = "数据启用成功";
        } else {
            startType = ecbuBag.getStartType();
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
            record.setEcbubId(ecbuBag.getEcbubId());
            record.setStartType(startType);
            //System.out.println(CommonFunction.getGson().toJson(record));
            ecbuBagService.update(record);
        }
        CommonFunction.getCommonMap(map, status, code, msg);
        ecbBagModel.loadData(request);//txt文档
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
        EcbuBag record = new EcbuBag();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        if ("1".equals(startType)) {
            record.setStartType(true);
        }
        List<EcbuBag> list = ecbuBagService.getList(record);
        map.put("list", list);
        status = 3;
        code = "200";
        msg = "正常获取数据";
        CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    /***===数据模型===***/
    //deal
    public void deal(EcbuBag record) {
        EcbuBag ecbuBag = ecbuBagService.getObject(record);
        if (ecbuBag == null) {
            ecbuBagService.insert(record);
        } else {
            ecbuBagService.update(record);
        }
    }

    //getObjectPassEcCompanyIdAndEcbbId
    public EcbuBag getObjectPassEcCompanyIdAndEcbbId(int ecCompanyId, int ecbbId) {
        EcbuBag record = new EcbuBag();
        record.setEcCompanyId(ecCompanyId);
        record.setEcbbId(ecbbId);
        return ecbuBagService.getObject(record);
    }

    //getObjectPassBagStr 通过包带类型类型获取包带 为计算成本提供数据
    public EcbuBag getObjectPassBagStr(int ecuId, String objectStr) {
        EcbuBag object = null;
        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        EcbuBag record = new EcbuBag();
        record.setStartType(true);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcbuBag> list = ecbuBagService.getList(record);
        for (EcbuBag ecbu_bag : list) {
            int ecbbId = ecbu_bag.getEcbbId();
            EcbBag recordEcbBag = new EcbBag();
            recordEcbBag.setEcbbId(ecbbId);
            EcbBag bag = ecbBagService.getObject(recordEcbBag);
            if (bag.getAbbreviation().equals(objectStr)) {
                object = ecbu_bag;
            }
        }
        return object;
    }

    //deletePassEcCompanyId
    public void deletePassEcCompanyId(int ecCompanyId) {
        EcbuBag record = new EcbuBag();
        record.setEcCompanyId(ecCompanyId);
        ecbuBagService.delete(record);
    }

    //getObjectPassEcbubId
    public EcbuBag getObjectPassEcbubId(int ecbubId) {
        EcbuBag record = new EcbuBag();
        record.setEcbubId(ecbubId);
        return ecbuBagService.getObject(record);
    }
}
