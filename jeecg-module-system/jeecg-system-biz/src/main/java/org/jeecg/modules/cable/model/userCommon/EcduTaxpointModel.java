package org.jeecg.modules.cable.model.userCommon;

import org.jeecg.modules.cable.entity.systemEcable.EcdTaxpoint;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.modules.cable.entity.userCommon.EcduTaxpoint;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userCommon.EcdTaxpointService;
import org.jeecg.modules.cable.service.userCommon.EcduTaxpointService;
import org.jeecg.modules.cable.tools.CommonFunction;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EcduTaxpointModel {
    @Resource
    EcduTaxpointService ecduTaxpointService;
    @Resource
    EcUserService ecUserService;
    @Resource
    EcdTaxpointService ecdTaxpointService;//系统税点

    //getListAndCount
    public Map<String, Object> getListAndCount(HttpServletRequest request) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        EcduTaxpoint record = new EcduTaxpoint();
record.setStartType(bo.getStartType());
        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcduTaxpoint> list = ecduTaxpointService.getList(record);
        for (EcduTaxpoint ecduTaxpoint : list) {
            int ecdtId = ecduTaxpoint.getEcdtId();
            EcdTaxpoint recordEcdTaxpoint = new EcdTaxpoint();
            recordEcdTaxpoint.setEcdtId(ecdtId);
            EcdTaxpoint ecdTaxpoint = ecdTaxpointService.getObject(recordEcdTaxpoint);
            ecduTaxpoint.setEcdTaxpoint(ecdTaxpoint);
        }
        long count = ecduTaxpointService.getCount(record);
        map.put("list", list);
        map.put("count", count);
        status = 3;//正常获取数据
        code = "200";
        msg = "正常获取数据";
        CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }

    //deal
    public Map<String, Object> deal(HttpServletRequest request) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        int ecdtId = Integer.parseInt(request.getParameter("ecdtId"));
        String name = request.getParameter("name");//自定义名称
        BigDecimal percentCommon = new BigDecimal(request.getParameter("percentCommon"));//普票税点
        BigDecimal percentSpecial = new BigDecimal(request.getParameter("percentSpecial"));//专票税点
        String description = request.getParameter("description");
        EcduTaxpoint record = new EcduTaxpoint();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setEcdtId(ecdtId);
        EcduTaxpoint ecduTaxpoint = ecduTaxpointService.getObject(record);
        if (ecduTaxpoint == null) {
            record.setEcdtId(ecdtId);
            record.setEcCompanyId(ecUser.getEcCompanyId());
            record.setStartType(true);
            record.setName(name);
            record.setPercentCommon(percentCommon);
            record.setPercentSpecial(percentSpecial);
            record.setDescription(description);
            ecduTaxpointService.insert(record);
            status = 3;//正常插入数据
            code = "200";
            msg = "新增数据成功";
        } else {
            record.setEcdutId(ecduTaxpoint.getEcdutId());
            record.setName(name);
            record.setPercentCommon(percentCommon);
            record.setPercentSpecial(percentSpecial);
            record.setDescription(description);
            ecduTaxpointService.updateByPrimaryKeySelective(record);
            status = 4;//正常更新数据
            code = "201";
            msg = "更新数据成功";
        }
        CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }

    //start
    public Map<String, Object> start(HttpServletRequest request) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        int ecdtId = Integer.parseInt(request.getParameter("ecdtId"));
        EcduTaxpoint record = new EcduTaxpoint();
        record.setEcdtId(ecdtId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcduTaxpoint ecduTaxpoint = ecduTaxpointService.getObject(record);
        if (ecduTaxpoint == null) {
            EcdTaxpoint recordEcdTaxpoint = new EcdTaxpoint();
            recordEcdTaxpoint.setEcdtId(ecdtId);
            EcdTaxpoint ecdTaxpoint = ecdTaxpointService.getObject(recordEcdTaxpoint);
            record.setEcdtId(ecdtId);
            record.setEcCompanyId(ecUser.getEcCompanyId());
            record.setStartType(true);
            record.setName("");
            record.setPercentCommon(ecdTaxpoint.getPercentCommon());
            record.setPercentSpecial(ecdTaxpoint.getPercentSpecial());
            record.setDescription("");
            ecduTaxpointService.insert(record);
            status = 3;//启用成功
            code = "200";
            msg = "数据启用成功";
        } else {
            boolean startType = ecduTaxpoint.getStartType();
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
            record.setEcdutId(ecduTaxpoint.getEcdutId());
            record.setStartType(startType);
            ecduTaxpointService.updateByPrimaryKeySelective(record);
        }
        CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }

    //delete
    public Map<String, Object> delete(HttpServletRequest request) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        int ecdtId = Integer.parseInt(request.getParameter("ecdtId"));
        EcduTaxpoint record = new EcduTaxpoint();
        record.setEcdtId(ecdtId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        ecduTaxpointService.deletePassEcCompanyIdAndEcdtId(record);
        status = 3;//删除数据
        code = "200";
        msg = "正常删除数据";
        CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }

    //getObject
    public Map<String, Object> getObject(HttpServletRequest request) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        EcduTaxpoint record = new EcduTaxpoint();
        if (request.getParameter("ecdtId") != null) {
            int ecdtId = Integer.parseInt(request.getParameter("ecdtId"));
            record.setEcdtId(ecdtId);
        }
record.setStartType(bo.getStartType());
        record.setEcCompanyId(ecUser.getEcCompanyId());
        map.put("object", ecduTaxpointService.getObject(record));
        status = 3;//获取数据
        code = "200";
        msg = "正常获取数据";
        CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }

    //dealPercent
    public Map<String, Object> dealPercent(HttpServletRequest request) {

        int ecdutId = Integer.parseInt(request.getParameter("ecdutId"));
        EcduTaxpoint record = new EcduTaxpoint();
        record.setEcdutId(ecdutId);
        if (request.getParameter("percentCommon") != null) {
            BigDecimal percentCommon = new BigDecimal(request.getParameter("percentCommon"));
            record.setPercentCommon(percentCommon);
        }
        if (request.getParameter("percentSpecial") != null) {
            BigDecimal percentSpecial = new BigDecimal(request.getParameter("percentSpecial"));
            record.setPercentSpecial(percentSpecial);
        }
        System.out.println(CommonFunction.getGson().toJson(record));
        ecduTaxpointService.updateByPrimaryKeySelective(record);
        status = 3;//数据操作成功
        code = "200";
        msg = "数据操作成功";
        CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }
}
