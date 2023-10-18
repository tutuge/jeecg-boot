package org.jeecg.modules.cable.model.user;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.user.unit.vo.UnitVo;
import org.jeecg.modules.cable.entity.user.EccUnit;
import org.jeecg.modules.cable.service.user.EccUnitService;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public UnitVo getList(HttpServletRequest request) {

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

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
        return new UnitVo(list, count);
    }

    //getObject
    public EccUnit getObject(HttpServletRequest request) {

        int eccuId = Integer.parseInt(request.getParameter("eccuId"));
        return getObjectPassEccuId(eccuId);

    }

    //deal
    public String deal(HttpServletRequest request) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        int eccuId = Integer.parseInt(request.getParameter("eccuId"));
        String silkName = request.getParameter("silkName");//丝型号
        int ecbuluId = Integer.parseInt(request.getParameter("ecbuluId"));
        String description = request.getParameter("description");
        EccUnit record = new EccUnit();
        record.setEccuId(eccuId);
        record.setSilkName(silkName);
        EccUnit ecProfit = eccUnitService.getObject(record);
        String msg = "";
        if (ecProfit != null) {
            throw new RuntimeException("名称已占用");
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

                msg = "正常新增数据";
            } else {//修改
                record.setEccuId(eccuId);
                record.setSilkName(silkName);
                record.setEcbuluId(ecbuluId);
                record.setDescription(description);
                record.setAddTime(System.currentTimeMillis());
                record.setUpdateTime(System.currentTimeMillis());
                eccUnitService.update(record);

                msg = "正常更新数据";
            }
        }

        return msg;
    }

    //start
    public String start(HttpServletRequest request) {

        int eccuId = Integer.parseInt(request.getParameter("eccuId"));
        EccUnit eccUnit = getObjectPassEccuId(eccuId);
        boolean startType = eccUnit.getStartType();
        String msg = "";
        if (!startType) {
            startType = true;
            msg = "启用成功";
        } else {
            startType = false;

            msg = "禁用成功";
        }
        EccUnit record = new EccUnit();
        record.setEccuId(eccuId);
        record.setStartType(startType);
        eccUnitService.update(record);
        return msg;
    }

    //sort
    public void sort(HttpServletRequest request) {

        int eccuId = Integer.parseInt(request.getParameter("eccuId"));
        int sortId = Integer.parseInt(request.getParameter("sortId"));
        EccUnit record = new EccUnit();
        record.setEccuId(eccuId);
        record.setSortId(sortId);
        eccUnitService.update(record);

    }

    //delete
    public void delete(HttpServletRequest request) {

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
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
    }

    /***===数据模型===***/

//getObjectPassEccuId
    public EccUnit getObjectPassEccuId(int eccuId) {
        EccUnit record = new EccUnit();
        record.setEccuId(eccuId);
        return eccUnitService.getObject(record);
    }
}
