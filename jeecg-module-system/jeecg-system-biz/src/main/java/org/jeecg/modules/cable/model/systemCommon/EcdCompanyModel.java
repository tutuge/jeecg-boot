package org.jeecg.modules.cable.model.systemCommon;

import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.controller.systemCommon.company.bo.EcdCompanyBaseBo;
import org.jeecg.modules.cable.controller.systemCommon.company.bo.EcdCompanyDealBo;
import org.jeecg.modules.cable.controller.systemCommon.company.bo.EcdCompanyListBo;
import org.jeecg.modules.cable.controller.systemCommon.company.bo.EcdCompanySortBo;
import org.jeecg.modules.cable.controller.systemCommon.company.vo.EcdCompanyListVo;
import org.jeecg.modules.cable.entity.systemCommon.EcdCompany;
import org.jeecg.modules.cable.service.systemCommon.EcdCompanyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class EcdCompanyModel {
    @Resource
    EcdCompanyService ecdCompanyService;

    // deal
    @Transactional(rollbackFor = Exception.class)
    public String deal(EcdCompanyDealBo bo) {
        Integer ecdcId = bo.getEcdcId();
        String abbreviation = bo.getAbbreviation();// 简称
        String fullName = bo.getFullName();// 全称
        Integer billPercentType = bo.getBillPercentType();
        String description = bo.getDescription();

        EcdCompany record = new EcdCompany();
        record.setEcdcId(ecdcId);
        record.setAbbreviation(abbreviation);
        record.setFullName(fullName);
        EcdCompany ecdCompany = ecdCompanyService.getObject(record);
        String msg;
        if (ecdCompany != null) {
            throw new RuntimeException("简称或者全称已占用");
        } else {
//            EcatImages ecatImages;
//            EcatImages recordImages = new EcatImages();
//            String logoImg = "";
//            String sealImg = "";
//            long targetTime = System.currentTimeMillis() - 10 * 60 * 1000L;//取10分钟以内的图片
//            recordImages.setTypeId(1);
//            recordImages.setEcaId(ecaId);
//            recordImages.setAddTime(targetTime);
//            ecatImages = ecatImagesModel.getObject(recordImages);
            if (ObjectUtil.isNull(ecdcId)) {// 插入
//                if (ecatImages != null) {
//                    logoImg = ecatImages.getImageUrl();
//                }
//                recordImages.setTypeId(2);
//                ecatImages = ecatImagesModel.getObject(recordImages);
//                if (ecatImages != null) {
//                    sealImg = ecatImages.getImageUrl();
//                }
                Integer sortId = 1;
                record = new EcdCompany();
                ecdCompany = ecdCompanyService.getObject(record);
                if (ecdCompany != null) {
                    sortId = ecdCompany.getSortId() + 1;
                }
                record.setStartType(true);
                record.setSortId(sortId);
                record.setAbbreviation(abbreviation);
                record.setFullName(fullName);
//                record.setLogoImg(logoImg);
//                record.setSealImg(sealImg);
                record.setBillPercentType(billPercentType);
                record.setDescription(description);
                // log.info(CommonFunction.getGson().toJson(record));
                ecdCompanyService.insert(record);

                msg = "正常插入数据";
            } else {
//                if (ecatImages != null) {
//                    logoImg = ecatImages.getImageUrl();
//                    record.setLogoImg(logoImg);
//                }
//                recordImages.setTypeId(2);
//                ecatImages = ecatImagesModel.getObject(recordImages);
//                //log.info("ecatImages + " + CommonFunction.getGson().toJson(ecatImages));
//                if (ecatImages != null) {
//                    sealImg = ecatImages.getImageUrl();
//                    //log.info("sealImg + " + sealImg);
//                    record.setSealImg(sealImg);
//                }
                record.setEcdcId(ecdcId);
                record.setAbbreviation(abbreviation);
                record.setFullName(fullName);
                record.setBillPercentType(billPercentType);
                record.setDescription(description);
                // log.info(CommonFunction.getGson().toJson(record));
                ecdCompanyService.update(record);

                msg = "正常更新数据";
            }
        }

        return msg;
    }

    // getList
    public EcdCompanyListVo getList(EcdCompanyListBo bo) {
        EcdCompany record = new EcdCompany();
        record.setStartType(bo.getStartType());
        List<EcdCompany> list = ecdCompanyService.getList(record);
        Long count = ecdCompanyService.getCount(record);
        return new EcdCompanyListVo(list, count);
    }

    // getObject
    public EcdCompany getObject(EcdCompanyBaseBo bo) {
        Integer ecdcId = bo.getEcdcId();
        EcdCompany record = new EcdCompany();
        record.setEcdcId(ecdcId);
        EcdCompany ecdCompany = ecdCompanyService.getObject(record);
        return ecdCompany;
    }

    // sort
    public void sort(List<EcdCompanySortBo> bos) {
        for (EcdCompanySortBo bo : bos) {
            Integer ecdcId = bo.getEcdcId();
            Integer sortId = bo.getSortId();
            EcdCompany record = new EcdCompany();
            record.setEcdcId(ecdcId);
            record.setSortId(sortId);
            ecdCompanyService.update(record);
        }
    }

    // start
    public String start(EcdCompanyBaseBo bo) {
        Integer ecdcId = bo.getEcdcId();
        EcdCompany record = new EcdCompany();
        record.setEcdcId(ecdcId);
        EcdCompany ecdCompany = ecdCompanyService.getObject(record);
        String msg;
        Boolean startType = ecdCompany.getStartType();
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        record = new EcdCompany();
        record.setEcdcId(ecdCompany.getEcdcId());
        record.setStartType(startType);
        ecdCompanyService.update(record);
        return msg;
    }

    // delete
    @Transactional(rollbackFor = Exception.class)
    public void delete(EcdCompanyBaseBo bo) {

        Integer ecdcId = bo.getEcdcId();
        EcdCompany record = new EcdCompany();
        record.setEcdcId(ecdcId);
        EcdCompany ecdCompany = ecdCompanyService.getObject(record);
        Integer sortId = ecdCompany.getSortId();
        record = new EcdCompany();
        record.setSortId(sortId);
        List<EcdCompany> list = ecdCompanyService.getList(record);
        Integer ecdc_id;
        for (EcdCompany ecd_company : list) {
            ecdc_id = ecd_company.getEcdcId();
            sortId = ecd_company.getSortId() - 1;
            record.setEcdcId(ecdc_id);
            record.setSortId(sortId);
            ecdCompanyService.update(record);
        }
        record = new EcdCompany();
        record.setEcdcId(ecdcId);
        ecdCompanyService.delete(record);
    }

    /***===数据模型===***/
    public List<EcdCompany> getListStart() {
        EcdCompany record = new EcdCompany();
        record.setStartType(true);
        return ecdCompanyService.getList(record);
    }
}
