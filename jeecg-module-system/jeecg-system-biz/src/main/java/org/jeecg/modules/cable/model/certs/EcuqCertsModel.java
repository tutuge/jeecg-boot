package org.jeecg.modules.cable.model.certs;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.cert.bo.CertsBo;
import org.jeecg.modules.cable.entity.certs.EcuqCerts;
import org.jeecg.modules.cable.model.user.EcUserModel;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.service.certs.EcuqCertsService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EcuqCertsModel {
    @Resource
    EcuLoginModel ecuLoginModel;
    @Resource
    EcuqCertsService ecuqCertsService;
    @Resource
    EcUserModel ecUserModel;

    //getList
    public Map<String, Object> getList(CertsBo certsBo) {
        Map<String, Object> map = new HashMap<>();
        EcuqCerts record = new EcuqCerts();
        if (certsBo.getStartType() != null) {
            boolean startType = true;
            if (!"0".equals(certsBo.getStartType())) {
                if ("2".equals(certsBo.getStartType())) {
                    startType = false;
                }
                record.setStartType(startType);
            }
        }
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcuqCerts> list = ecuqCertsService.getList(record);
        long count = ecuqCertsService.getCount(record);
        map.put("list", list);
        map.put("count", count);
        return map;
    }

    //getObject
    public Map<String, Object> getObject(HttpServletRequest request) {

            EcuqCerts ecuqCerts = getObjectPassEcuId(ecuId);
            map.put("object", ecuqCerts);
            status = 3;//正常获取数据
            code = "200";
            msg = "正常获取数据";
            CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }

    //deal
    public Map<String, Object> deal(HttpServletRequest request) {

            int ecuqcId = Integer.parseInt(request.getParameter("ecuqcId"));
            EcUser ecUser = ecUserModel.getObjectPassEcuId(ecuId);
            String certsName = request.getParameter("certsName");
            EcuqCerts record = new EcuqCerts();
            record.setEcuId(ecuId);
            record.setCertsName(certsName);
            EcuqCerts ecuqCerts = ecuqCertsService.getObject(record);
            if (ecuqCerts != null) {
                status = 3;//名称已占用
                code = "103";
                msg = "名称已占用";
            } else {
                record = new EcuqCerts();
                if (ecuqcId == 0) {//插入
                    record.setEcCompanyId(ecUser.getEcCompanyId());
                    record.setEcuId(ecuId);
                    record.setCertsName(certsName);
                    record.setStartType(true);
                    record.setDefaultType(false);
                    ecuqCertsService.insert(record);
                    status = 4;//正常插入数据
                    code = "200";
                    msg = "正常插入数据";
                } else {//更新
                    record.setEcuqcId(ecuqcId);
                    record.setCertsName(certsName);
                    ecuqCertsService.update(record);
                    status = 5;//正常更新数据
                    code = "201";
                    msg = "正常更新数据";
                }
            }
            CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }

    //start
    public Map<String, Object> start(HttpServletRequest request) {

            int ecuqcId = Integer.parseInt(request.getParameter("ecuqcId"));
            EcuqCerts record = new EcuqCerts();
            record.setEcuqcId(ecuqcId);
            EcuqCerts ecuqCerts = ecuqCertsService.getObject(record);
            boolean startType = ecuqCerts.getStartType();
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
            record = new EcuqCerts();
            record.setEcuqcId(ecuqCerts.getEcuqcId());
            record.setStartType(startType);
            if (!startType) {
                record.setDefaultType(false);
            }
            ecuqCertsService.update(record);
            CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }

    //defaultType
    public Map<String, Object> defaultType(HttpServletRequest request) {

            int ecuqcId = Integer.parseInt(request.getParameter("ecuqcId"));
            EcuqCerts record = new EcuqCerts();
            record.setEcuqcId(ecuqcId);
            EcuqCerts ecuqCerts = ecuqCertsService.getObject(record);
            boolean defaultType = ecuqCerts.getDefaultType();
            if (!defaultType) {
                defaultType = true;
                status = 3;
                code = "200";
                msg = "数据默认成功";
            } else {
                defaultType = false;
                status = 4;
                code = "201";
                msg = "数据非默认成功";
            }
            record = new EcuqCerts();
            record.setEcuqcId(ecuqCerts.getEcuqcId());
            record.setDefaultType(defaultType);
            ecuqCertsService.update(record);
            CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }

    //delete
    public Map<String, Object> delete(HttpServletRequest request) {

            int ecuqcId = Integer.parseInt(request.getParameter("ecuqcId"));
            EcuqCerts record = new EcuqCerts();
            record.setEcuqcId(ecuqcId);
            ecuqCertsService.delete(record);
            status = 3;//数据操作成功
            code = "200";
            msg = "数据操作成功";
            CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }

    /***===数据模型===***/
    //getObjectPassEcuId
    public EcuqCerts getObjectPassEcuId(int ecuId) {
        EcuqCerts record = new EcuqCerts();
        record.setEcuId(ecuId);
        return ecuqCertsService.getObject(record);
    }
}
