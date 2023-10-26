package org.jeecg.modules.cable.model.user;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.entity.user.EcuData;
import org.jeecg.modules.cable.service.user.EcuDataService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EcuDataModel {

    @Resource
    EcuDataService ecuDataService;
    @Resource
    EcUserModel ecUserModel;

    //getList
    public Map<String, Object> getList(HttpServletRequest request) {

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer ecuId = ecUser.getEcuId();
        EcuData record = new EcuData();
        if (ecUser.getTypeId() == 2) {
            record.setEcuId(ecuId);
        } else {
            record.setEcCompanyId(ecUser.getEcCompanyId());
        }
        if (request.getParameter("startType") != null) {
            Boolean startType = true;
            if (!"0".equals(request.getParameter("startType"))) {
                if ("2".equals(request.getParameter("startType"))) {
                    startType = false;
                }
                record.setStartType(startType);
            }
        }
        if (request.getParameter("pageNumber") != null) {
            Integer pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
            Integer startNumber = (Integer.parseInt(request.getParameter("page")) - 1) * pageNumber;
            record.setStartNumber(startNumber);
            record.setPageNumber(pageNumber);
        }
        log.info("record + " + CommonFunction.getGson().toJson(record));
        List<EcuData> list = ecuDataService.getList(record);
        long count = ecuDataService.getCount(record);
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("count", count);
        return map;
    }

    //getObject
    public EcuData getObject(HttpServletRequest request) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer ecuId = ecUser.getEcuId();
        EcuData record = new EcuData();
        if (request.getParameter("ecudId") == null) {
            record.setEcuId(ecuId);
        } else {
            Integer ecudId = Integer.parseInt(request.getParameter("ecudId"));
            record.setEcudId(ecudId);
        }
        if (request.getParameter("startType") != null) {
            Boolean startType = true;
            if (!"0".equals(request.getParameter("startType"))) {
                if ("2".equals(request.getParameter("startType"))) {
                    startType = false;
                }
                record.setStartType(startType);
            }
        }
        log.info("record + " + CommonFunction.getGson().toJson(record));
        return ecuDataService.getObject(record);
    }

    //deal
    public String deal(HttpServletRequest request) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer ecuId = ecUser.getEcuId();

        Integer ecbusId = Integer.parseInt(request.getParameter("ecbusId"));
        String silkName = request.getParameter("silkName");
        EcuData ecuData = getObjectPassEcuId(ecuId);
        EcuData record = new EcuData();
        String msg = "";
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

            msg = "正常新增数据";
        } else {//修改
            if (request.getParameter("ecudId") == null) {
                record.setEcuId(ecuId);
            } else {
                Integer ecudId = Integer.parseInt(request.getParameter("ecudId"));
                record.setEcudId(ecudId);
            }
            record.setEcbusId(ecbusId);
            record.setSilkName(silkName);
            record.setUpdateTime(System.currentTimeMillis());
            ecuDataService.update(record);
            msg = "正常更新数据";
        }

        return msg;
    }

    //start
    public String start(HttpServletRequest request) {

        Integer ecudId = Integer.parseInt(request.getParameter("ecudId"));
        EcuData ecuData = getObjectPassEcudId(ecudId);
        Boolean startType = ecuData.getStartType();
        String msg;
        if (!startType) {
            startType = true;
            msg = "启用成功";
        } else {
            startType = false;
            msg = "禁用成功";
        }
        EcuData record = new EcuData();
        record.setEcudId(ecudId);
        record.setStartType(startType);
        ecuDataService.update(record);

        return msg;
    }

    /***===数据模型===***/
//getObjectPassEcuId
    public EcuData getObjectPassEcuId(Integer ecuId) {
        EcuData record = new EcuData();
        record.setEcuId(ecuId);
        return ecuDataService.getObject(record);
    }

    //getObjectPassEcudId
    public EcuData getObjectPassEcudId(Integer ecudId) {
        EcuData record = new EcuData();
        record.setEcudId(ecudId);
        return ecuDataService.getObject(record);
    }
}
