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
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class EcuoProgrammeModel {
    @Resource
    EcuoProgrammeService ecuoProgrammeService;


    @Transactional(rollbackFor = Exception.class)
    public String deal(ProgrammeDealBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();


        Integer ecuopId = bo.getEcuopId();
        String programmeName = bo.getProgrammeName();
        EcuoProgramme record = new EcuoProgramme();
        BeanUtils.copyProperties(bo, record);

        String msg = "";
        if (ObjectUtil.isNull(ecuopId)) {// 插入
            // 如果是新增，首先验证名称在本公司下是否占用
            record.setEcCompanyId(sysUser.getEcCompanyId());
            record.setProgrammeName(programmeName);
            EcuoProgramme ecuoProgramme = ecuoProgrammeService.getObject(record);
            if (ecuoProgramme != null) {
                throw new RuntimeException("方案名称已占用");
            }
            Integer sortId = 1;
            // 查询此公司下的最新的排序
            record.setEcCompanyId(sysUser.getEcCompanyId());
            ecuoProgramme = ecuoProgrammeService.getObject(record);
            if (ecuoProgramme != null) {
                sortId = ecuoProgramme.getSortId() + 1;
            }
            record.setSortId(sortId);
            ecuoProgrammeService.insert(record);
            msg = "正常新增数据";
        } else {
            // 如果是修改，还需要排除当前行的名称再验证是否被占用了名称
            record.setEcuopId(ecuopId);
            record.setEcCompanyId(sysUser.getEcCompanyId());
            record.setProgrammeName(programmeName);
            EcuoProgramme ecuoProgramme = ecuoProgrammeService.getObject(record);
            if (ecuoProgramme != null) {
                throw new RuntimeException("方案名称已占用");
            } else {
                ecuoProgrammeService.update(record);
                msg = "正常更新数据";
            }
        }
        return msg;
    }


    public List<EcuoProgramme> getList() {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        EcuoProgramme record = new EcuoProgramme();
        record.setEcCompanyId(sysUser.getEcCompanyId());
        return ecuoProgrammeService.getList(record);
    }


    public EcuoProgramme getObject(ProgrammeBaseBo bo) {
        Integer ecuopId = bo.getEcuopId();
        EcuoProgramme record = new EcuoProgramme();
        record.setEcuopId(ecuopId);
        return ecuoProgrammeService.getObject(record);
    }


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


    @Transactional(rollbackFor = Exception.class)
    public void delete(ProgrammeBaseBo bo) {
        Integer ecuopId = bo.getEcuopId();
        EcuoProgramme record = new EcuoProgramme();
        record.setEcuopId(ecuopId);
        // 删除前先给其他后面的数据重新排序
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
        ecuoProgrammeService.delete(record);
    }

    /***===数据模型===***/


    public EcuoProgramme getObjectPassEcuopId(Integer ecuopId) {
        EcuoProgramme record = new EcuoProgramme();
        record.setEcuopId(ecuopId);
        return ecuoProgrammeService.getObject(record);
    }
}
