package org.jeecg.modules.cable.model.userCommon;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.userCommon.company.bo.CompanyBo;
import org.jeecg.modules.cable.controller.userCommon.company.bo.UserCompanyBaseBo;
import org.jeecg.modules.cable.controller.userCommon.company.bo.UserCompanyDealBo;
import org.jeecg.modules.cable.controller.userCommon.company.bo.UserCompanySortBo;
import org.jeecg.modules.cable.controller.userCommon.company.vo.CompanyVo;
import org.jeecg.modules.cable.entity.userCommon.EcduCompany;
import org.jeecg.modules.cable.service.userCommon.EcduCompanyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class EcduCompanyModel {
    @Resource
    EcduCompanyService ecduCompanyService;

    public CompanyVo getListAndCount(CompanyBo bo) {

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcduCompany record = new EcduCompany();
        record.setStartType(bo.getStartType());
        record.setEcCompanyId(sysUser.getEcCompanyId());
        List<EcduCompany> list = ecduCompanyService.getList(record);
        long count = ecduCompanyService.getCount(record);
        return new CompanyVo(list, count);
    }


    public EcduCompany getObject(UserCompanyBaseBo bo) {
        EcduCompany record = new EcduCompany();
        Integer ecducId = bo.getEcducId();
        record.setEcducId(ecducId);
        EcduCompany ecduCompany = ecduCompanyService.getObject(record);
        return ecduCompany;
    }


    public EcduCompany getObjectDefault() {

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcduCompany record = new EcduCompany();
        record.setDefaultType(true);
        record.setEcCompanyId(sysUser.getEcCompanyId());
        EcduCompany ecduCompany = ecduCompanyService.getObject(record);
        return ecduCompany;
    }


    @Transactional(rollbackFor = Exception.class)
    public String deal(UserCompanyDealBo bo) {

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecducId = bo.getEcducId();
        String abbreviation = bo.getAbbreviation();// 简称
        String fullName = bo.getFullName();// 全称
        Integer billPercentType = bo.getBillPercentType();
        String description = bo.getDescription();

        EcduCompany record = new EcduCompany();
        record.setEcducId(ecducId);
        record.setEcCompanyId(sysUser.getEcCompanyId());
        record.setAbbreviation(abbreviation);
        record.setFullName(fullName);
        List<EcduCompany> ecduCompanys = ecduCompanyService.getObjectPassAbbreviationAndFullName(record);

        String msg = "";
        if (CollUtil.isNotEmpty(ecduCompanys)) {
            throw new RuntimeException("简称或者全称已占用");
        }
        String logoImg = bo.getLogoImg();
        if (ObjectUtil.isNull(ecducId)) {// 插入
            int sortId = 1;
            EcduCompany ecduCompany = ecduCompanyService.getLatestObject(record);
            if (ecduCompany != null) {
                sortId = ecduCompany.getSortId() + 1;
            }
            record.setEcCompanyId(sysUser.getEcCompanyId());
            record.setStartType(true);
            record.setSortId(sortId);
            record.setDefaultType(false);
            record.setAbbreviation(abbreviation);
            record.setFullName(fullName);
            record.setLogoImg(logoImg);
            record.setBillPercentType(billPercentType);
            record.setDescription(description);
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


    @Transactional(rollbackFor = Exception.class)
    public void sort(List<UserCompanySortBo> bos) {
        for (UserCompanySortBo bo : bos) {
            Integer ecducId = bo.getEcducId();
            Integer sortId = bo.getSortId();
            EcduCompany record = new EcduCompany();
            record.setEcducId(ecducId);
            record.setSortId(sortId);
            ecduCompanyService.update(record);
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public void delete(UserCompanyBaseBo bo) {
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


    public String start(UserCompanyBaseBo bo) {
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

    @Transactional(rollbackFor = Exception.class)
    public void dealDefault(UserCompanyBaseBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecducId = bo.getEcducId();
        EcduCompany record = new EcduCompany();
        record.setEcCompanyId(sysUser.getEcCompanyId());
        record.setDefaultType(false);
        ecduCompanyService.update(record);
        record.setEcducId(ecducId);
        record.setDefaultType(true);
        ecduCompanyService.update(record);
    }


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
