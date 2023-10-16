package org.jeecg.modules.cable.model.userEcable;

import org.jeecg.modules.cable.entity.systemEcable.EcbMicatape;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.modules.cable.entity.userEcable.EcbuMicatape;
import org.jeecg.modules.cable.model.systemEcable.EcbMicatapeModel;
import org.jeecg.modules.cable.service.systemEcable.EcbMicatapeService;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userEcable.EcbuMicatapeService;
import org.jeecg.modules.cable.tools.CommonFunction;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EcbuMicatapeModel {
    @Resource
    EcbuMicatapeService ecbuMicatapeService;
    @Resource
    EcbMicatapeService ecbMicatapeService;
    @Resource
    EcUserService ecUserService;
    @Resource
    EcbMicatapeModel ecbMicatapeModel;

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
        int ecbmId = Integer.parseInt(request.getParameter("ecbmId"));
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
        EcbuMicatape record = new EcbuMicatape();
        record.setEcbmId(ecbmId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuMicatape ecbuMicatape = ecbuMicatapeService.getObject(record);
        if (ecbuMicatape == null) {//插入
            record.setStartType(false);
            record.setName("");
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            ecbuMicatapeService.insert(record);
            status = 3;//插入
            code = "200";
            msg = "插入数据";
        } else {
            record.setEcbumId(ecbuMicatape.getEcbumId());
            if (request.getParameter("unitPrice") != null) {
                record.setUnitPrice(unitPrice);
            }
            if (request.getParameter("density") != null) {
                record.setDensity(density);
            }
            if (request.getParameter("description") != null) {
                record.setDescription(description);
            }
            ecbuMicatapeService.update(record);
            status = 4;//更新数据
            code = "201";
            msg = "更新数据";
        }
        CommonFunction.getCommonMap(map, status, code, msg);
        ecbMicatapeModel.loadData(request);//加截txt
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
        int ecbmId = Integer.parseInt(request.getParameter("ecbmId"));
        EcbuMicatape record = new EcbuMicatape();
        record.setEcbmId(ecbmId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuMicatape ecbuMicatape = ecbuMicatapeService.getObject(record);
        boolean startType;
        if (ecbuMicatape == null) {//插入数据
            EcbMicatape recordEcbMicatape = new EcbMicatape();
            recordEcbMicatape.setEcbmId(ecbmId);
            EcbMicatape ecbMicatape = ecbMicatapeService.getObject(recordEcbMicatape);
            record.setEcbmId(ecbmId);
            record.setEcCompanyId(ecUser.getEcCompanyId());
            record.setStartType(true);
            record.setName("");
            record.setUnitPrice(ecbMicatape.getUnitPrice());
            record.setDensity(ecbMicatape.getDensity());
            record.setDescription("");
            ecbuMicatapeService.insert(record);
            status = 3;//启用成功
            code = "200";
            msg = "数据启用成功";
        } else {
            startType = ecbuMicatape.getStartType();
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
            record.setEcbumId(ecbuMicatape.getEcbumId());
            record.setStartType(startType);
            //System.out.println(CommonFunction.getGson().toJson(record));
            ecbuMicatapeService.update(record);
        }
        CommonFunction.getCommonMap(map, status, code, msg);
        ecbMicatapeModel.loadData(request);//加截txt
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
        EcbuMicatape record = new EcbuMicatape();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        if ("1".equals(startType)) {
            record.setStartType(true);
        }
        List<EcbuMicatape> list = ecbuMicatapeService.getList(record);
        map.put("list", list);
        status = 3;
        code = "200";
        msg = "正常获取数据";
        CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    /***===数据模型===***/
    //deal
    public void deal(EcbuMicatape record) {
        EcbuMicatape ecbuMicatape = ecbuMicatapeService.getObject(record);
        if (ecbuMicatape == null) {
            ecbuMicatapeService.insert(record);
        } else {
            ecbuMicatapeService.update(record);
        }
    }

    //getObjectPassEcCompanyIdAndEcbmId
    public EcbuMicatape getObjectPassEcCompanyIdAndEcbmId(int ecCompanyId, int ecbmId) {
        EcbuMicatape record = new EcbuMicatape();
        record.setEcCompanyId(ecCompanyId);
        record.setEcbmId(ecbmId);
        return ecbuMicatapeService.getObject(record);
    }

    //getObjectPassMicatapeStr 通过屏蔽类型类型获取屏蔽 为计算成本提供数据
    public EcbuMicatape getObjectPassMicatapeStr(int ecuId) {
        EcbuMicatape object;
        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        EcbuMicatape record = new EcbuMicatape();
        record.setStartType(true);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcbuMicatape> list = ecbuMicatapeService.getList(record);
        object = list.get(0);
        return object;
    }

    //deletePassEcCompanyId
    public void deletePassEcCompanyId(int ecCompanyId) {
        EcbuMicatape record = new EcbuMicatape();
        record.setEcCompanyId(ecCompanyId);
        ecbuMicatapeService.delete(record);
    }

    //getObjectPassEcbumId
    public EcbuMicatape getObjectPassEcbumId(int ecbumId) {
        EcbuMicatape record = new EcbuMicatape();
        record.setEcbumId(ecbumId);
        return ecbuMicatapeService.getObject(record);
    }
}
