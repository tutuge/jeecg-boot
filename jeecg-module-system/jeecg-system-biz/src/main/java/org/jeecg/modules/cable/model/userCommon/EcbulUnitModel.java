package org.jeecg.modules.cable.model.userCommon;

import org.jeecg.common.system.vo.EcUser;
import org.jeecg.modules.cable.entity.userCommon.EcbulUnit;
import org.jeecg.modules.cable.model.efficiency.EcdCollectModel;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userCommon.EcbulUnitService;
import org.jeecg.modules.cable.tools.CommonFunction;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EcbulUnitModel {
    @Resource
    EcbulUnitService ecbulUnitService;
    @Resource
    EcUserService ecUserService;
    @Resource
    EcdCollectModel ecdCollectModel;

    //getListAndCount
    public Map<String, Object> getListAndCount(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        EcbulUnit record = new EcbulUnit();
        if (request.getParameter("startType") != null) {
            boolean startType = true;
            if (!"0".equals(request.getParameter("startType"))) {
                if ("2".equals(request.getParameter("startType"))) {
                    startType = false;
                }
                record.setStartType(startType);
            }
        }
        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcbulUnit> list = ecbulUnitService.getList(record);
        long count = ecbulUnitService.getCount(record);
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
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        EcbulUnit record = new EcbulUnit();
        if (request.getParameter("ecbuluId") != null) {
            int ecbuluId = Integer.parseInt(request.getParameter("ecbuluId"));
            record.setEcbuluId(ecbuluId);
        }
        map.put("object", ecbulUnitService.getObject(record));
        status = 3;//正常获取数据
        code = "200";
        msg = "正常获取数据";
        CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

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
        int ecbuluId = Integer.parseInt(request.getParameter("ecbuluId"));
        String lengthName = request.getParameter("lengthName");
        int meterNumber = Integer.parseInt(request.getParameter("meterNumber"));
        String description = request.getParameter("description");
        EcbulUnit record = new EcbulUnit();
        record.setEcbuluId(ecbuluId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setLengthName(lengthName);
        EcbulUnit ecbulUnit = ecbulUnitService.getObjectPassLengthName(record);
        if (ecbulUnit != null) {
            status = 3;//名称已占用
            code = "103";
            msg = "名称已占用";
        } else {
            if (ecbuluId == 0) {//插入
                int sortId = 1;
                ecbulUnit = ecbulUnitService.getLatestObject(record);
                if (ecbulUnit != null) {
                    sortId = ecbulUnit.getSortId() + 1;
                }
                record = new EcbulUnit();
                record.setEcCompanyId(ecUser.getEcCompanyId());
                record.setStartType(true);
                record.setSortId(sortId);
                record.setLengthName(lengthName);
                record.setMeterNumber(meterNumber);
                record.setDescription(description);
                ecbulUnitService.insert(record);
                status = 4;//正常插入数据
                code = "200";
                msg = "正常插入数据";
            } else {//更新
                record = new EcbulUnit();
                record.setEcbuluId(ecbuluId);
                record.setLengthName(lengthName);
                record.setMeterNumber(meterNumber);
                record.setDescription(description);
                ecbulUnitService.update(record);
                status = 5;//正常更新数据
                code = "201";
                msg = "正常更新数据";
            }
        }
        CommonFunction.getCommonMap(map, status, code, msg);
        loadData(request);
        return map;
    }

    //sort
    public Map<String, Object> sort(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        int ecbuluId = Integer.parseInt(request.getParameter("ecbuluId"));
        int sortId = Integer.parseInt(request.getParameter("sortId"));
        EcbulUnit record = new EcbulUnit();
        record.setEcbuluId(ecbuluId);
        record.setSortId(sortId);
        ecbulUnitService.update(record);
        status = 3;//数据操作成功
        code = "200";
        msg = "数据操作成功";
        CommonFunction.getCommonMap(map, status, code, msg);
        loadData(request);
        return map;
    }

    //delete
    public Map<String, Object> delete(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        int ecbuluId = Integer.parseInt(request.getParameter("ecbuluId"));
        EcbulUnit record = new EcbulUnit();
        record.setEcbuluId(ecbuluId);
        EcbulUnit ecbulUnit = ecbulUnitService.getObject(record);
        int sortId = ecbulUnit.getSortId();
        record = new EcbulUnit();
        record.setSortId(sortId);
        record.setEcbuluId(ecbulUnit.getEcbuluId());
        List<EcbulUnit> list = ecbulUnitService.getListGreaterThanSortId(record);
        int ecbulu_id;
        for (EcbulUnit ecbud_price : list) {
            ecbulu_id = ecbud_price.getEcbuluId();
            sortId = ecbud_price.getSortId() - 1;
            record.setEcbuluId(ecbulu_id);
            record.setSortId(sortId);
            ecbulUnitService.update(record);
        }
        record = new EcbulUnit();
        record.setEcbuluId(ecbuluId);
        ecbulUnitService.delete(record);
        status = 3;//数据操作成功
        code = "200";
        msg = "数据操作成功";
        CommonFunction.getCommonMap(map, status, code, msg);
        loadData(request);
        return map;
    }

    //start
    public Map<String, Object> start(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        int ecbuluId = Integer.parseInt(request.getParameter("ecbuluId"));
        EcbulUnit record = new EcbulUnit();
        record.setEcbuluId(ecbuluId);
        EcbulUnit ecbulUnit = ecbulUnitService.getObject(record);
        boolean startType = ecbulUnit.getStartType();
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
        record = new EcbulUnit();
        record.setEcbuluId(ecbulUnit.getEcbuluId());
        record.setStartType(startType);
        ecbulUnitService.update(record);
        CommonFunction.getCommonMap(map, status, code, msg);
        loadData(request);
        return map;
    }

    //load 加载用户包带数据为txt文档
    public void loadData(HttpServletRequest request) {
        int ecCompanyId = 0;
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        ecCompanyId = ecUser.getEcCompanyId();
        EcbulUnit record = new EcbulUnit();
        record.setStartType(true);
        record.setEcCompanyId(ecCompanyId);
        System.out.println(CommonFunction.getGson().toJson(record));
        List<EcbulUnit> list = ecbulUnitService.getList(record);
        List<String> txtList = new ArrayList<>();
        txtList.add(CommonFunction.getGson().toJson(list));
        ecdCollectModel.deal(ecCompanyId, 10, txtList);
    }

    /***===数据模型===***/
    //insert
    public void deal(EcbulUnit record) {
        EcbulUnit recordEcbulUnit = new EcbulUnit();
        recordEcbulUnit.setEcCompanyId(record.getEcCompanyId());
        recordEcbulUnit.setLengthName(record.getLengthName());
        EcbulUnit ecbulUnit = ecbulUnitService.getObject(record);
        if (ecbulUnit != null) {
            ecbulUnitService.update(record);
        } else {
            ecbulUnitService.insert(record);
        }
    }

    //deletePassEcCompanyId
    public void deletePassEcCompanyId(int ecCompanyId) {
        EcbulUnit record = new EcbulUnit();
        record.setEcCompanyId(ecCompanyId);
        ecbulUnitService.delete(record);
    }

    //getObjectPassEcCompanyIdAndLengthName
    public EcbulUnit getObjectPassEcCompanyIdAndLengthName(int ecCompanyId, String lengthName) {
        EcbulUnit record = new EcbulUnit();
        record.setEcCompanyId(ecCompanyId);
        record.setLengthName(lengthName);
        return ecbulUnitService.getObject(record);
    }
}
