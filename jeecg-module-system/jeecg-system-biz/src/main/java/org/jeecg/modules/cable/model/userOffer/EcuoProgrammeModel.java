package org.jeecg.modules.cable.model.userOffer;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.entity.userOffer.EcuoProgramme;
import org.jeecg.modules.cable.model.user.EcUserModel;
import org.jeecg.modules.cable.service.userOffer.EcuoProgrammeService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EcuoProgrammeModel {
    @Resource
    EcuoProgrammeService ecuoProgrammeService;

    //deal
    public String deal(HttpServletRequest request) {

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer ecuopId = Integer.parseInt(request.getParameter("ecuopId"));
        String programmeName = request.getParameter("programmeName");
        String coreStr = request.getParameter("coreStr");
        String areaStr = request.getParameter("areaStr");
        BigDecimal addPercent = new BigDecimal(request.getParameter("addPercent"));
        EcuoProgramme record = new EcuoProgramme();

        String msg="";
        if (ecuopId == 0) {//插入
            record.setEcCompanyId(ecUser.getEcCompanyId());
            record.setProgrammeName(programmeName);
            EcuoProgramme ecuoProgramme = ecuoProgrammeService.getObject(record);
            if (ecuoProgramme != null) {
                throw new RuntimeException("方案名称已占用");
            } else {
                Integer sortId = 1;
                record = new EcuoProgramme();
                record.setEcCompanyId(ecUser.getEcCompanyId());
                ecuoProgramme = ecuoProgrammeService.getObject(record);
                if (ecuoProgramme != null) {
                    sortId = ecuoProgramme.getSortId() + 1;
                }
                record.setSortId(sortId);
                record.setProgrammeName(programmeName);
                record.setCoreStr(coreStr);
                record.setAreaStr(areaStr);
                record.setAddPercent(addPercent);
                ecuoProgrammeService.insert(record);

                msg = "正常新增数据";
            }
        } else {
            record.setEcuopId(ecuopId);
            record.setEcCompanyId(ecUser.getEcCompanyId());
            record.setProgrammeName(programmeName);
            EcuoProgramme ecuoProgramme = ecuoProgrammeService.getObject(record);
            if (ecuoProgramme != null) {
                throw new RuntimeException("方案名称已占用");
            } else {
                record.setCoreStr(coreStr);
                record.setAreaStr(areaStr);
                record.setAddPercent(addPercent);
                ecuoProgrammeService.update(record);
                msg = "正常更新数据";
            }
        }
        return msg;
    }

    //getList
    public List<EcuoProgramme> getList() {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        EcuoProgramme record = new EcuoProgramme();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        return ecuoProgrammeService.getList(record);
    }

    //getObject
    public EcuoProgramme getObject(HttpServletRequest request) {
        Integer ecuopId = Integer.parseInt(request.getParameter("ecuopId"));
        EcuoProgramme record = new EcuoProgramme();
        record.setEcuopId(ecuopId);
        return ecuoProgrammeService.getObject(record);
    }

    //sort
    public void sort(HttpServletRequest request) {

        Integer ecuopId = Integer.parseInt(request.getParameter("ecuopId"));
        Integer sortId = Integer.parseInt(request.getParameter("sortId"));
        EcuoProgramme record = new EcuoProgramme();
        record.setEcuopId(ecuopId);
        record.setSortId(sortId);
        ecuoProgrammeService.update(record);
    }

    //delete
    public void delete(HttpServletRequest request) {

        Integer ecuopId = Integer.parseInt(request.getParameter("ecuopId"));
        EcuoProgramme record = new EcuoProgramme();
        record.setEcuopId(ecuopId);
        EcuoProgramme ecuoProgramme = ecuoProgrammeService.getObject(record);
        Integer sortId = ecuoProgramme.getSortId();
        record = new EcuoProgramme();
        record.setEcCompanyId(ecuoProgramme.getEcCompanyId());
        record.setSortId(sortId);
        List<EcuoProgramme> list = ecuoProgrammeService.getList(record);
        Integer ecuop_id;
        for (EcuoProgramme programme : list) {
            ecuop_id = programme.getEcuopId();
            sortId = programme.getSortId() - 1;
            record.setEcuopId(ecuop_id);
            record.setSortId(sortId);
            ecuoProgrammeService.update(record);
        }
        record = new EcuoProgramme();
        record.setEcuopId(ecuopId);
    }

    /***===数据模型===***/
    public EcuoProgramme getObjectPassEcuopId(Integer ecuopId) {
        EcuoProgramme record = new EcuoProgramme();
        record.setEcuopId(ecuopId);
        return ecuoProgrammeService.getObject(record);
    }
}
