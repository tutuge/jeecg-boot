package org.jeecg.modules.cable.model.certs;

import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.cert.bo.CertsBo;
import org.jeecg.modules.cable.entity.certs.EcuqCerts;
import org.jeecg.modules.cable.service.certs.EcuqCertsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EcuqCertsModel {
    @Resource
    EcuqCertsService ecuqCertsService;


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
        record.setEcCompanyId(sysUser.getEcCompanyId());
        List<EcuqCerts> list = ecuqCertsService.getList(record);
        long count = ecuqCertsService.getCount(record);
        map.put("list", list);
        map.put("count", count);
        return map;
    }


    public EcuqCerts getObject() {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecuId = sysUser.getUserId();
        EcuqCerts record = new EcuqCerts();
        record.setEcuId(ecuId);
        return ecuqCertsService.getObject(record);
    }


    @Transactional(rollbackFor = Exception.class)
    public String deal(HttpServletRequest request) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecuId = sysUser.getUserId();
        Integer ecuqcId = Integer.parseInt(request.getParameter("ecuqcId"));
        String certsName = request.getParameter("certsName");
        EcuqCerts record = new EcuqCerts();
        record.setEcuId(ecuId);
        record.setCertsName(certsName);
        EcuqCerts ecuqCerts = ecuqCertsService.getObject(record);
        String msg = "";
        if (ecuqCerts != null) {
            throw new RuntimeException("名称已占用");
        } else {
            record = new EcuqCerts();
            if (ObjectUtil.isNull(ecuqcId)) {// 插入
                record.setEcCompanyId(sysUser.getEcCompanyId());
                record.setEcuId(ecuId);
                record.setCertsName(certsName);
                record.setStartType(true);
                record.setDefaultType(false);
                ecuqCertsService.insert(record);
                msg = "正常插入数据";
            } else {// 更新
                record.setEcuqcId(ecuqcId);
                record.setCertsName(certsName);
                ecuqCertsService.update(record);
                msg = "正常更新数据";
            }
        }
        return msg;
    }


    public String start(HttpServletRequest request) {
        Integer ecuqcId = Integer.parseInt(request.getParameter("ecuqcId"));
        EcuqCerts record = new EcuqCerts();
        record.setEcuqcId(ecuqcId);
        EcuqCerts ecuqCerts = ecuqCertsService.getObject(record);
        Boolean startType = ecuqCerts.getStartType();

        String msg = "";
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        record = new EcuqCerts();
        record.setEcuqcId(ecuqCerts.getEcuqcId());
        record.setStartType(startType);
        if (!startType) {
            record.setDefaultType(false);
        }
        ecuqCertsService.update(record);
        return msg;
    }


    public String defaultType(HttpServletRequest request) {
        Integer ecuqcId = Integer.parseInt(request.getParameter("ecuqcId"));
        EcuqCerts record = new EcuqCerts();
        record.setEcuqcId(ecuqcId);
        EcuqCerts ecuqCerts = ecuqCertsService.getObject(record);
        Boolean defaultType = ecuqCerts.getDefaultType();
        String msg = "";
        if (!defaultType) {
            defaultType = true;

            msg = "数据默认成功";
        } else {
            defaultType = false;

            msg = "数据非默认成功";
        }
        record = new EcuqCerts();
        record.setEcuqcId(ecuqCerts.getEcuqcId());
        record.setDefaultType(defaultType);
        ecuqCertsService.update(record);
        return msg;
    }


    public void delete(HttpServletRequest request) {
        Integer ecuqcId = Integer.parseInt(request.getParameter("ecuqcId"));
        EcuqCerts record = new EcuqCerts();
        record.setEcuqcId(ecuqcId);
        ecuqCertsService.delete(record);
    }
}
