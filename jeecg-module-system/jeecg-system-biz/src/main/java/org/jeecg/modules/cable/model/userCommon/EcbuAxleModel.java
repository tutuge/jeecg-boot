package org.jeecg.modules.cable.model.userCommon;

import org.jeecg.common.system.vo.EcUser;
import org.jeecg.modules.cable.entity.userCommon.EcbuAxle;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userCommon.EcbuAxleService;
import org.jeecg.modules.cable.tools.CommonFunction;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EcbuAxleModel {
    @Resource
    EcbuAxleService ecbuAxleService;
    @Resource
    EcUserService ecUserService;//用户

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
        EcbuAxle record = new EcbuAxle();
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
        List<EcbuAxle> list = ecbuAxleService.getList(record);
        long count = ecbuAxleService.getCount(record);
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
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        EcbuAxle record = new EcbuAxle();
        if (request.getParameter("ecbuaId") != null) {
            int ecbuaId = Integer.parseInt(request.getParameter("ecbuaId"));
            record.setEcbuaId(ecbuaId);
        }
        map.put("object", ecbuAxleService.getObject(record));
        status = 3;//正常获取数据
        code = "200";
        msg = "正常获取数据";
        CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }

    //deal 提交
    public Map<String, Object> deal(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        int ecbuaId = Integer.parseInt(request.getParameter("ecbuaId"));
        String axleName = request.getParameter("axleName");
        BigDecimal axleHeight = new BigDecimal(request.getParameter("axleHeight"));
        BigDecimal circleDiameter = new BigDecimal(request.getParameter("circleDiameter"));
        BigDecimal axleWidth = new BigDecimal(request.getParameter("axleWidth"));
        BigDecimal axleDeep = new BigDecimal(request.getParameter("axleDeep"));
        BigDecimal axleWeight = new BigDecimal(request.getParameter("axleWeight"));
        BigDecimal axlePrice = new BigDecimal(request.getParameter("axlePrice"));
        String description = request.getParameter("description");
        EcbuAxle record = new EcbuAxle();
        record.setEcbuaId(ecbuaId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setAxleName(axleName);
        EcbuAxle ecbuAxle = ecbuAxleService.getObjectPassAxleName(record);
        if (ecbuAxle != null) {
            status = 3;//名称已占用
            code = "103";
            msg = "名称已占用";
        } else {
            if (ecbuaId == 0) {//插入
                int sortId = 1;
                ecbuAxle = ecbuAxleService.getLatestObject(record);
                if (ecbuAxle != null) {
                    sortId = ecbuAxle.getSortId() + 1;
                }
                record = new EcbuAxle();
                record.setEcCompanyId(ecUser.getEcCompanyId());
                record.setEcCompanyId(ecUser.getEcCompanyId());
                record.setStartType(true);
                record.setSortId(sortId);
                record.setAxleName(axleName);
                record.setAxleHeight(axleHeight);
                record.setCircleDiameter(circleDiameter);
                record.setAxleWidth(axleWidth);
                record.setAxleDeep(axleDeep);
                record.setAxleWeight(axleWeight);
                record.setAxlePrice(axlePrice);
                record.setDescription(description);
                ecbuAxleService.insert(record);
                status = 4;//正常插入数据
                code = "200";
                msg = "正常插入数据";
            } else {//更新
                record.setEcbuaId(ecbuaId);
                record.setAxleName(axleName);
                record.setAxleHeight(axleHeight);
                record.setCircleDiameter(circleDiameter);
                record.setAxleWidth(axleWidth);
                record.setAxleDeep(axleDeep);
                record.setAxleWeight(axleWeight);
                record.setAxlePrice(axlePrice);
                record.setDescription(description);
                ecbuAxleService.updateByPrimaryKeySelective(record);
                status = 5;//正常更新数据
                code = "201";
                msg = "正常更新数据";
            }
        }
        CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }

    //sort
    public Map<String, Object> sort(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        int ecbuaId = Integer.parseInt(request.getParameter("ecbuaId"));
        int sortId = Integer.parseInt(request.getParameter("sortId"));
        EcbuAxle record = new EcbuAxle();
        record.setEcbuaId(ecbuaId);
        record.setSortId(sortId);
        ecbuAxleService.updateByPrimaryKeySelective(record);
        status = 3;//数据操作成功
        code = "200";
        msg = "数据操作成功";
        CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }

    //delete
    public Map<String, Object> delete(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        int ecbuaId = Integer.parseInt(request.getParameter("ecbuaId"));
        EcbuAxle record = new EcbuAxle();
        record.setEcbuaId(ecbuaId);
        EcbuAxle ecbuAxle = ecbuAxleService.getObject(record);
        int sortId = ecbuAxle.getSortId();
        record = new EcbuAxle();
        record.setSortId(sortId);
        record.setEcCompanyId(ecbuAxle.getEcCompanyId());
        List<EcbuAxle> list = ecbuAxleService.getListGreaterThanSortId(record);
        int ecbua_id;
        for (EcbuAxle ecbud_money : list) {
            ecbua_id = ecbud_money.getEcbuaId();
            sortId = ecbud_money.getSortId() - 1;
            record.setEcbuaId(ecbua_id);
            record.setSortId(sortId);
            ecbuAxleService.updateByPrimaryKeySelective(record);
        }
        ecbuAxleService.deleteByPrimaryKey(ecbuaId);
        status = 3;//数据操作成功
        code = "200";
        msg = "数据操作成功";
        CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }

    //start
    public Map<String, Object> start(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        int ecbuaId = Integer.parseInt(request.getParameter("ecbuaId"));
        EcbuAxle record = new EcbuAxle();
        record.setEcbuaId(ecbuaId);
        EcbuAxle ecbuAxle = ecbuAxleService.getObject(record);
        boolean startType = ecbuAxle.getStartType();
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
        record = new EcbuAxle();
        record.setEcbuaId(ecbuAxle.getEcbuaId());
        record.setStartType(startType);
        ecbuAxleService.updateByPrimaryKeySelective(record);
        CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }
}
