package org.jeecg.modules.cable.model.userEcable;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.userEcable.bo.EcbuConductorBo;
import org.jeecg.modules.cable.controller.userEcable.bo.EcbuConductorListBo;
import org.jeecg.modules.cable.controller.userEcable.bo.EcbuConductorStartBo;
import org.jeecg.modules.cable.entity.systemEcable.EcbConductor;
import org.jeecg.modules.cable.entity.userEcable.EcbuConductor;
import org.jeecg.modules.cable.model.systemEcable.EcbConductorModel;
import org.jeecg.modules.cable.service.systemEcable.EcbConductorService;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userEcable.EcbuConductorService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class EcbuConductorModel {
    @Resource
    EcbuConductorService ecbuConductorService;
    @Resource
    EcbConductorService ecbConductorService;
    @Resource
    EcUserService ecUserService;
    @Resource
    EcbConductorModel ecbConductorModel;

    //deal
    public void deal(EcbuConductorBo bo) {
//        Map<String, Object> map = new HashMap<>();
//        int status;
//        String code;
//        String msg;
//        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
//        EcUser recordEcUser = new EcUser();
//        recordEcUser.setEcuId(ecuId);
//        EcUser ecUser = ecUserService.getObject(recordEcUser);
//        int ecbcId = Integer.parseInt(request.getParameter("ecbcId"));
        BigDecimal unitPrice = bo.getUnitPrice();
//        if (request.getParameter("unitPrice") != null) {
//            unitPrice = new BigDecimal(request.getParameter("unitPrice"));//单价
//        }
        BigDecimal density = bo.getDensity();
//        if (request.getParameter("density") != null) {
//            density = new BigDecimal(request.getParameter("density"));//密度
//        }
        BigDecimal resistivity = bo.getResistivity();
//        if (request.getParameter("resistivity") != null) {
//            resistivity = new BigDecimal(request.getParameter("resistivity"));//电阻率
//        }
        String description = bo.getDescription();
//        if (request.getParameter("description") != null) {
//            description = request.getParameter("description");
//        }
        EcbuConductor record = new EcbuConductor();
        record.setEcbcId(bo.getEcbcId());
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuConductor ecbuConductor = ecbuConductorService.getObject(record);
        if (ecbuConductor == null) {//插入
            record.setStartType(false);
            record.setName("");
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setResistivity(resistivity);
            record.setDescription(description);
            ecbuConductorService.insert(record);
//            status = 3;//插入
//            code = "200";
//            msg = "插入数据";
        } else {
            record.setEcbucId(ecbuConductor.getEcbucId());
//            if (request.getParameter("unitPrice") != null) {
            record.setUnitPrice(unitPrice);
//            }
//            if (request.getParameter("density") != null) {
            record.setDensity(density);
//            }
//            if (request.getParameter("resistivity") != null) {
            record.setResistivity(resistivity);
//            }
//            if (request.getParameter("description") != null) {
            record.setDescription(description);
//            }
            ecbuConductorService.update(record);
//            status = 4;//更新数据
//            code = "201";
//            msg = "更新数据";
        }
//        CommonFunction.getCommonMap(map, status, code, msg);
        ecbConductorModel.loadData();//加截txt
    }

    //start
    public String start(EcbuConductorStartBo bo) {
//        Map<String, Object> map = new HashMap<>();
//        int status;
//        String code;
//        String msg;
//        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
//        EcUser recordEcUser = new EcUser();
//        recordEcUser.setEcuId(ecuId);
//        EcUser ecUser = ecUserService.getObject(recordEcUser);
//        int ecbcId = Integer.parseInt(request.getParameter("ecbcId"));


        EcbuConductor record = new EcbuConductor();
        Integer ecbcId = bo.getEcbcId();
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        record.setEcbcId(ecbcId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuConductor ecbuConductor = ecbuConductorService.getObject(record);
        boolean startType;
        String msg = "";
        if (ecbuConductor == null) {//插入数据
            EcbConductor recordEcbConductor = new EcbConductor();
            recordEcbConductor.setEcbcId(ecbcId);
            EcbConductor ecbConductor = ecbConductorService.getObject(recordEcbConductor);
            record.setEcbcId(ecbcId);
            record.setEcCompanyId(ecUser.getEcCompanyId());
            record.setStartType(true);
            record.setName("");
            record.setUnitPrice(ecbConductor.getUnitPrice());
            record.setDensity(ecbConductor.getDensity());
            record.setResistivity(ecbConductor.getResistivity());
            record.setDescription("");
            ecbuConductorService.insert(record);
//            status = 3;//启用成功
//            code = "200";
            msg = "数据启用成功";
        } else {
            startType = ecbuConductor.getStartType();
            if (!startType) {
                startType = true;
//                status = 3;
//                code = "200";
                msg = "数据启用成功";
            } else {
                startType = false;
//                status = 4;
//                code = "201";
                msg = "数据禁用成功";
            }
            record.setEcbucId(ecbuConductor.getEcbucId());
            record.setStartType(startType);
            //System.out.println(CommonFunction.getGson().toJson(record));
            ecbuConductorService.update(record);
        }
//        CommonFunction.getCommonMap(map, status, code, msg);
        ecbConductorModel.loadData();//加截txt
        return msg;
    }

    //getList
    public List<EcbuConductor> getList(EcbuConductorListBo bo) {
//        Map<String, Object> map = new HashMap<>();
//        int status;
//        String code;
//        String msg;
//        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
//        EcUser recordEcUser = new EcUser();
//        recordEcUser.setEcuId(ecuId);
//        EcUser ecUser = ecUserService.getObject(recordEcUser);
//        String startType = request.getParameter("startType");
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        EcbuConductor record = new EcbuConductor();
        record.setEcCompanyId(ecUser.getEcCompanyId());
//        if ("1".equals(startType)) {
        record.setStartType(bo.getStartType());

        return ecbuConductorService.getList(record);
//        map.put("list", list);
//        status = 3;
//        code = "200";
//        msg = "正常获取数据";
//        CommonFunction.getCommonMap(map, status, code, msg);
//        return map;
    }

    /***===数据模型===***/
    //insert
    public void deal(EcbuConductor record) {
        EcbuConductor ecbuConductor = ecbuConductorService.getObject(record);
        if (ecbuConductor == null) {
            ecbuConductorService.insert(record);
        } else {
            ecbuConductorService.update(record);
        }
    }

    //getObjectPassEcbcIdAndEcCompanyId
    public EcbuConductor getObjectPassEcbcIdAndEcCompanyId(int ecbcId, int ecCompanyId) {
        EcbuConductor record = new EcbuConductor();
        record.setEcbcId(ecbcId);
        record.setEcCompanyId(ecCompanyId);
        //log.info("record + " + CommonFunction.getGson().toJson(record));
        return ecbuConductorService.getObject(record);
    }

    //getObjectPassEcCompanyId
    public EcbuConductor getObjectPassEcCompanyId(int ecCompanyId) {
        EcbuConductor record = new EcbuConductor();
        record.setEcCompanyId(ecCompanyId);
        //log.info("record + " + CommonFunction.getGson().toJson(record));
        return ecbuConductorService.getObject(record);
    }

    //deletePassEcCompanyId
    public void deletePassEcCompanyId(int ecCompanyId) {
        EcbuConductor record = new EcbuConductor();
        record.setEcCompanyId(ecCompanyId);
        ecbuConductorService.delete(record);
    }

    //getObjectPassEcbucId
    public EcbuConductor getObjectPassEcbucId(int ecbucId) {
        EcbuConductor record = new EcbuConductor();
        record.setEcbucId(ecbucId);
        return ecbuConductorService.getObject(record);
    }
}
