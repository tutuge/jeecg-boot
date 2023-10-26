package org.jeecg.modules.cable.model.userOffer;

import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.userOffer.programme.bo.ProgrammeBaseBo;
import org.jeecg.modules.cable.controller.userOffer.programme.bo.ProgrammeDealBo;
import org.jeecg.modules.cable.controller.userOffer.programme.bo.ProgrammeSortBo;
import org.jeecg.modules.cable.entity.userOffer.EcuoProgramme;
import org.jeecg.modules.cable.service.userOffer.EcuoProgrammeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class EcuoProgrammeModel {
    @Resource
    EcuoProgrammeService ecuoProgrammeService;

    // deal
    public String deal(ProgrammeDealBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        Integer ecuopId = bo.getEcuopId();
        String programmeName = bo.getProgrammeName();
        String coreStr = bo.getCoreStr();
        String areaStr = bo.getAreaStr();
        BigDecimal addPercent = bo.getAddPercent();

        EcuoProgramme record = new EcuoProgramme();

        String msg = "";
        if (ObjectUtil.isNull(ecuopId)) {// 插入
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

    // getList
    public List<EcuoProgramme> getList() {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        EcuoProgramme record = new EcuoProgramme();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        return ecuoProgrammeService.getList(record);
    }

    // getObject
    public EcuoProgramme getObject(ProgrammeBaseBo bo) {
        Integer ecuopId = bo.getEcuopId();
        EcuoProgramme record = new EcuoProgramme();
        record.setEcuopId(ecuopId);
        return ecuoProgrammeService.getObject(record);
    }

    // sort
    @Transactional(rollbackFor = Exception.class)
    public void sort(List<ProgrammeSortBo> bos) {
        for (ProgrammeSortBo bo : bos) {
            Integer ecuopId = bo.getEcuopId();
            Integer sortId = bo.getSortId();
            EcuoProgramme record = new EcuoProgramme();
            record.setEcuopId(ecuopId);
            record.setSortId(sortId);
            ecuoProgrammeService.update(record);
        }
    }

    // delete
    public void delete(ProgrammeBaseBo bo) {

        Integer ecuopId = bo.getEcuopId();
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
