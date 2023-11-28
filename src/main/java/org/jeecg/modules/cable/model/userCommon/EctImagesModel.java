package org.jeecg.modules.cable.model.userCommon;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.userCommon.image.bo.EctImageDealBo;
import org.jeecg.modules.cable.entity.userCommon.EctImages;
import org.jeecg.modules.cable.service.userCommon.EctImagesService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EctImagesModel {
    @Resource
    EctImagesService ectImagesService;


    public EctImages deal(EctImageDealBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecuId = sysUser.getUserId();
        EctImages record = new EctImages();
        record.setTypeId(bo.getTypeId());
        record.setEcuId(ecuId);
        // ectImagesService.delete(record);//删除之前的数据
        record.setImageUrl(bo.getPath());
        ectImagesService.insert(record);
        return record;
    }
}
