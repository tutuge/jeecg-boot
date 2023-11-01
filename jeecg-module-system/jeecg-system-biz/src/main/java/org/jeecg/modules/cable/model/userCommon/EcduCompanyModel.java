package org.jeecg.modules.cable.model.userCommon;

import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.userCommon.company.bo.CompanyBo;
import org.jeecg.modules.cable.controller.userCommon.company.bo.UCompanyBaseBo;
import org.jeecg.modules.cable.controller.userCommon.company.bo.UCompanyDealBo;
import org.jeecg.modules.cable.controller.userCommon.company.bo.UCompanySortBo;
import org.jeecg.modules.cable.controller.userCommon.company.vo.CompanyVo;
import org.jeecg.modules.cable.entity.userCommon.EcduCompany;
import org.jeecg.modules.cable.service.userCommon.EcduCompanyService;
import org.jeecg.modules.cable.service.userCommon.EctImagesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class EcduCompanyModel {
    @Resource
    EcduCompanyService ecduCompanyService;
    @Resource
    EctImagesService ectImagesService;

    // getListAndCount
    public CompanyVo getListAndCount(CompanyBo bo) {
        // 获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        EcduCompany record = new EcduCompany();
        record.setStartType(bo.getStartType());
        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcduCompany> list = ecduCompanyService.getList(record);
        long count = ecduCompanyService.getCount(record);
        return new CompanyVo(list, count);
    }

    // getObject
    public EcduCompany getObject(UCompanyBaseBo bo) {

        EcduCompany record = new EcduCompany();
        Integer ecducId = bo.getEcducId();
        record.setEcducId(ecducId);
        EcduCompany ecduCompany = ecduCompanyService.getObject(record);
        return ecduCompany;
    }

    // getObject
    public EcduCompany getObjectDefault() {
        // 获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        EcduCompany record = new EcduCompany();
        record.setDefaultType(true);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcduCompany ecduCompany = ecduCompanyService.getObject(record);
        return ecduCompany;
    }

    // deal
    @Transactional(rollbackFor = Exception.class)
    public String deal(UCompanyDealBo bo) {
        // 获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer ecuId = ecUser.getEcuId();

        Integer ecducId = bo.getEcducId();
        String abbreviation = bo.getAbbreviation();// 简称
        String fullName = bo.getFullName();// 全称
        Integer billPercentType = bo.getBillPercentType();
        String description = bo.getDescription();

        EcduCompany record = new EcduCompany();
        record.setEcducId(ecducId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setAbbreviation(abbreviation);
        record.setFullName(fullName);
        EcduCompany ecduCompany = ecduCompanyService.getObjectPassAbbreviationAndFullName(record);

        String msg = "";
        if (ecduCompany != null) {
            throw new RuntimeException("简称或者全称已占用");
        }
        String logoImg = bo.getLogoImg();
        if (ObjectUtil.isNull(ecducId)) {// 插入

            int sortId = 1;
            ecduCompany = ecduCompanyService.getLatestObject(record);
            if (ecduCompany != null) {
                sortId = ecduCompany.getSortId() + 1;
            }
            record.setEcCompanyId(ecUser.getEcCompanyId());
            record.setStartType(true);
            record.setSortId(sortId);
            record.setDefaultType(false);
            record.setAbbreviation(abbreviation);
            record.setFullName(fullName);
            record.setLogoImg(logoImg);

            record.setBillPercentType(billPercentType);
            record.setDescription(description);
            // System.out.println(CommonFunction.getGson().toJson(record));
            ecduCompanyService.insert(record);
            msg = "正常插入数据";
        } else {

            record.setLogoImg(logoImg);
            record.setEcducId(ecducId);
            record.setAbbreviation(abbreviation);
            record.setFullName(fullName);
            record.setBillPercentType(billPercentType);
            record.setDescription(description);
            // System.out.println(CommonFunction.getGson().toJson(record));
            ecduCompanyService.update(record);
            msg = "正常更新数据";
        }

        return msg;
    }

    // sort
    @Transactional(rollbackFor = Exception.class)
    public void sort(List<UCompanySortBo> bos) {
        for (UCompanySortBo bo : bos) {
            Integer ecducId = bo.getEcducId();
            Integer sortId = bo.getSortId();
            EcduCompany record = new EcduCompany();
            record.setEcducId(ecducId);
            record.setSortId(sortId);
            ecduCompanyService.update(record);
        }
    }

    // delete
    @Transactional(rollbackFor = Exception.class)
    public void delete(UCompanyBaseBo bo) {

        Integer ecducId = bo.getEcducId();
        EcduCompany record = new EcduCompany();
        record.setEcducId(ecducId);
        EcduCompany ecduCompany = ecduCompanyService.getObject(record);
        Integer sortId = ecduCompany.getSortId();
        record = new EcduCompany();
        record.setSortId(sortId);
        record.setEcCompanyId(ecduCompany.getEcCompanyId());
        List<EcduCompany> list = ecduCompanyService.getListGreaterThanSortId(record);
        Integer ecduc_id;
        for (EcduCompany ecbuCompany : list) {
            ecduc_id = ecbuCompany.getEcducId();
            sortId = ecbuCompany.getSortId() - 1;
            record.setEcducId(ecduc_id);
            record.setSortId(sortId);
            ecduCompanyService.update(record);
        }
        record = new EcduCompany();
        record.setEcducId(ecducId);
        ecduCompanyService.delete(record);
    }

    // start
    public String start(UCompanyBaseBo bo) {
        Integer ecducId = bo.getEcducId();
        EcduCompany record = new EcduCompany();
        record.setEcducId(ecducId);
        EcduCompany ecduCompany = ecduCompanyService.getObject(record);
        Boolean startType = ecduCompany.getStartType();
        String msg = "";
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        record = new EcduCompany();
        record.setEcducId(ecduCompany.getEcducId());
        record.setStartType(startType);
        ecduCompanyService.update(record);

        return msg;
    }

    // dealDefault
    public void dealDefault(UCompanyBaseBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        Integer ecducId = bo.getEcducId();
        EcduCompany record = new EcduCompany();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setDefaultType(false);
        ecduCompanyService.update(record);
        record.setEcducId(ecducId);
        record.setDefaultType(true);
        ecduCompanyService.update(record);
    }

    /***===数据模型===***/
    // deal
    @Transactional(rollbackFor = Exception.class)
    public void deal(EcduCompany record) {
        EcduCompany recordEcduCompany = new EcduCompany();
        recordEcduCompany.setEcCompanyId(record.getEcCompanyId());
        recordEcduCompany.setAbbreviation(record.getAbbreviation());
        recordEcduCompany.setFullName(record.getFullName());
        EcduCompany ecduCompany = ecduCompanyService.getObject(record);
        if (ecduCompany != null) {
            ecduCompanyService.update(record);
        } else {
            ecduCompanyService.insert(record);
        }
    }

    // deletePassEcCompanyId
    public void deletePassEcCompanyId(Integer ecCompanyId) {
        EcduCompany recordEcduCompany = new EcduCompany();
        recordEcduCompany.setEcCompanyId(ecCompanyId);
        ecduCompanyService.delete(recordEcduCompany);
    }
}
