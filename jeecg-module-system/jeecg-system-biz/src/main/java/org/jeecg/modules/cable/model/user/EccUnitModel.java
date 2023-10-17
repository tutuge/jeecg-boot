package org.jeecg.modules.cable.model.user;

import org.jeecg.common.system.vo.EcUser;
import org.jeecg.modules.cable.entity.user.EccUnit;
import org.jeecg.modules.cable.service.user.EccUnitService;
import org.jeecg.modules.cable.tools.CommonFunction;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EccUnitModel {
    @Resource
    EcuLoginModel ecuLoginModel;
    @Resource
    EccUnitService eccUnitService;
    @Resource
    EcUserModel ecUserModel;

    //getList
    public Map<String, Object> getList(HttpServletRequest request) {

            EcUser ecUser = ecUserModel.getObjectPassEcuId(ecuId);
            EccUnit record = new EccUnit();
            record.setEcCompanyId(ecUser.getEcCompanyId());
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
            List<EccUnit> list = eccUnitService.getList(record);
            long count = eccUnitService.getCount(record);
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

            int eccuId = Integer.parseInt(request.getParameter("eccuId"));
            EccUnit eccUnit = getObjectPassEccuId(eccuId);
            map.put("object", eccUnit);
            status = 3;//正常获取数据
            code = "200";
            msg = "正常获取数据";
            CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }

    //deal
    public Map<String, Object> deal(HttpServletRequest request) {

            EcUser ecUser = ecUserModel.getObjectPassEcuId(ecuId);
            int eccuId = Integer.parseInt(request.getParameter("eccuId"));
            String silkName = request.getParameter("silkName");//丝型号
            int ecbuluId = Integer.parseInt(request.getParameter("ecbuluId"));
            String description = request.getParameter("description");
            EccUnit record = new EccUnit();
            record.setEccuId(eccuId);
            record.setSilkName(silkName);
            EccUnit ecProfit = eccUnitService.getObject(record);
            if (ecProfit != null) {
                status = 3;//名称已占用
                code = "103";
                msg = "名称已占用";
            } else {
                if (eccuId == 0) {//插入
                    int sortId = 1;
                    record = new EccUnit();
                    record.setEcCompanyId(ecUser.getEcCompanyId());
                    ecProfit = eccUnitService.getObject(record);
                    if (ecProfit != null) {
                        sortId = ecProfit.getSortId() + 1;
                    }
                    record.setSilkName(silkName);
                    record.setStartType(true);
                    record.setSortId(sortId);
                    record.setSilkName(silkName);
                    record.setEcbuluId(ecbuluId);
                    record.setDescription(description);
                    record.setAddTime(System.currentTimeMillis());
                    record.setUpdateTime(System.currentTimeMillis());
                    //log.info("record + " + CommonFunction.getGson().toJson(record));
                    eccUnitService.insert(record);
                    status = 3;//正常新增数据
                    code = "200";
                    msg = "正常新增数据";
                } else {//修改
                    record.setEccuId(eccuId);
                    record.setSilkName(silkName);
                    record.setEcbuluId(ecbuluId);
                    record.setDescription(description);
                    record.setAddTime(System.currentTimeMillis());
                    record.setUpdateTime(System.currentTimeMillis());
                    eccUnitService.update(record);
                    status = 4;//正常更新数据
                    code = "201";
                    msg = "正常更新数据";
                }
            }
            CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }

    //start
    public Map<String, Object> start(HttpServletRequest request) {

            int eccuId = Integer.parseInt(request.getParameter("eccuId"));
            EccUnit eccUnit = getObjectPassEccuId(eccuId);
            boolean startType = eccUnit.getStartType();
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
            EccUnit record = new EccUnit();
            record.setEccuId(eccuId);
            record.setStartType(startType);
            eccUnitService.update(record);
            CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }

    //sort
    public Map<String, Object> sort(HttpServletRequest request) {

            int eccuId = Integer.parseInt(request.getParameter("eccuId"));
            int sortId = Integer.parseInt(request.getParameter("sortId"));
            EccUnit record = new EccUnit();
            record.setEccuId(eccuId);
            record.setSortId(sortId);
            eccUnitService.update(record);
            status = 3;
            code = "200";
            msg = "数据操作成功";
            CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }

    //delete
    public Map<String, Object> delete(HttpServletRequest request) {

            EcUser ecUser = ecUserModel.getObjectPassEcuId(ecuId);
            int eccuId = Integer.parseInt(request.getParameter("eccuId"));
            EccUnit record = new EccUnit();
            record.setEccuId(eccuId);
            EccUnit eccUnit = eccUnitService.getObject(record);
            int sortId = eccUnit.getSortId();
            record = new EccUnit();
            record.setEcCompanyId(ecUser.getEcCompanyId());
            record.setSortId(sortId);
            List<EccUnit> list = eccUnitService.getList(record);
            int eccu_id;
            for (EccUnit unit : list) {
                eccu_id = unit.getEccuId();
                sortId = unit.getSortId() - 1;
                record.setEccuId(eccu_id);
                record.setSortId(sortId);
                eccUnitService.update(record);
            }
            record = new EccUnit();
            record.setEccuId(eccuId);
            eccUnitService.delete(record);
            status = 3;//数据操作成功
            code = "200";
            msg = "数据操作成功";
            CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }

    /***===数据模型===***/

    //getObjectPassEccuId
    public EccUnit getObjectPassEccuId(int eccuId) {
        EccUnit record = new EccUnit();
        record.setEccuId(eccuId);
        return eccUnitService.getObject(record);
    }
}
