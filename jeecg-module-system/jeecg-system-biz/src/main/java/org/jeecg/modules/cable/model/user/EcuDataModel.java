package org.jeecg.modules.cable.model.user;

import org.jeecg.common.system.vo.EcUser;
import org.jeecg.modules.cable.entity.user.EcuData;
import org.jeecg.modules.cable.service.user.EcuDataService;
import org.jeecg.modules.cable.tools.CommonFunction;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EcuDataModel {
    @Resource
    EcuLoginModel ecuLoginModel;
    @Resource
    EcuDataService ecuDataService;
    @Resource
    EcUserModel ecUserModel;

    //getList
    public Map<String, Object> getList(HttpServletRequest request) {
        Map<String, Object> map;
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            EcUser ecUser = ecUserModel.getObjectPassEcuId(ecuId);
            EcuData record = new EcuData();
            if (ecUser.getTypeId() == 2) {
                record.setEcuId(ecuId);
            } else {
                record.setEcCompanyId(ecUser.getEcCompanyId());
            }
            if (request.getParameter("startType") != null) {
                boolean startType = true;
                if (!"0".equals(request.getParameter("startType"))) {
                    if ("2".equals(request.getParameter("startType"))) {
                        startType = false;
                    }
                    record.setStartType(startType);
                }
            }
            if (request.getParameter("pageNumber") != null) {
                int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
                int startNumber = (Integer.parseInt(request.getParameter("page")) - 1) * pageNumber;
                record.setStartNumber(startNumber);
                record.setPageNumber(pageNumber);
            }
            log.info("record + " + CommonFunction.getGson().toJson(record));
            List<EcuData> list = ecuDataService.getList(record);
            long count = ecuDataService.getCount(record);
            map.put("list", list);
            map.put("count", count);
            status = 3;//正常获取数据
            code = "200";
            msg = "正常获取数据";
            CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    //getObject
    public Map<String, Object> getObject(HttpServletRequest request) {
        Map<String, Object> map;
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            EcuData record = new EcuData();
            if (request.getParameter("ecudId") == null) {
                record.setEcuId(ecuId);
            } else {
                int ecudId = Integer.parseInt(request.getParameter("ecudId"));
                record.setEcudId(ecudId);
            }
            if (request.getParameter("startType") != null) {
                boolean startType = true;
                if (!"0".equals(request.getParameter("startType"))) {
                    if ("2".equals(request.getParameter("startType"))) {
                        startType = false;
                    }
                    record.setStartType(startType);
                }
            }
            log.info("record + " + CommonFunction.getGson().toJson(record));
            EcuData ecuData = ecuDataService.getObject(record);
            map.put("object", ecuData);
            status = 3;//正常获取数据
            code = "200";
            msg = "正常获取数据";
            CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    //deal
    public Map<String, Object> deal(HttpServletRequest request) {
        Map<String, Object> map;
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            EcUser ecUser = ecUserModel.getObjectPassEcuId(ecuId);
            int ecbusId = Integer.parseInt(request.getParameter("ecbusId"));
            String silkName = request.getParameter("silkName");
            EcuData ecuData = getObjectPassEcuId(ecuId);
            EcuData record = new EcuData();
            if (ecuData == null) {//插入
                record.setEcCompanyId(ecUser.getEcCompanyId());
                record.setEcuId(ecuId);
                record.setStartType(true);
                record.setEcbusId(ecbusId);
                record.setSilkName(silkName);
                record.setAddTime(System.currentTimeMillis());
                record.setUpdateTime(System.currentTimeMillis());
                log.info("record + " + CommonFunction.getGson().toJson(record));
                ecuDataService.insert(record);
                status = 3;//正常新增数据
                code = "200";
                msg = "正常新增数据";
            } else {//修改
                if (request.getParameter("ecudId") == null) {
                    record.setEcuId(ecuId);
                } else {
                    int ecudId = Integer.parseInt(request.getParameter("ecudId"));
                    record.setEcudId(ecudId);
                }
                record.setEcbusId(ecbusId);
                record.setSilkName(silkName);
                record.setUpdateTime(System.currentTimeMillis());
                ecuDataService.update(record);
                status = 4;//正常更新数据
                code = "201";
                msg = "正常更新数据";
            }
            CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    //start
    public Map<String, Object> start(HttpServletRequest request) {
        Map<String, Object> map;
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            int ecudId = Integer.parseInt(request.getParameter("ecudId"));
            EcuData ecuData = getObjectPassEcudId(ecudId);
            boolean startType = ecuData.getStartType();
            if (!startType) {
                startType = true;
                status = 3;
                code = "200";
                msg = "启用成功";
            } else {
                startType = false;
                status = 4;
                code = "201";
                msg = "禁用成功";
            }
            EcuData record = new EcuData();
            record.setEcudId(ecudId);
            record.setStartType(startType);
            ecuDataService.update(record);
            CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    /***===数据模型===***/
    //getObjectPassEcuId
    public EcuData getObjectPassEcuId(int ecuId) {
        EcuData record = new EcuData();
        record.setEcuId(ecuId);
        return ecuDataService.getObject(record);
    }

    //getObjectPassEcudId
    public EcuData getObjectPassEcudId(int ecudId) {
        EcuData record = new EcuData();
        record.setEcudId(ecudId);
        return ecuDataService.getObject(record);
    }
}
