package org.jeecg.modules.cable.model.userCommon;

import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.controller.userCommon.image.bo.ImageBaseBo;
import org.jeecg.modules.cable.controller.userCommon.image.bo.ImageBo;
import org.jeecg.modules.cable.controller.userCommon.image.bo.ImageDealBo;
import org.jeecg.modules.cable.entity.userCommon.EcducImages;
import org.jeecg.modules.cable.service.userCommon.EcducImagesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class EcducImagesModel {
    @Resource
    EcducImagesService ecducImagesService;
    @Resource
    EcduciPositionModel ecduciPositionModel;

    // getList
    public List<EcducImages> getList(ImageBo bo) {
        Integer ecducId = bo.getEcducId();
        EcducImages record = new EcducImages();
        record.setEcducId(ecducId);
        List<EcducImages> list = ecducImagesService.getList(record);
        return list;
    }

    // getObject
    public EcducImages getObject(ImageBaseBo bo) {
        Integer ecduciId = bo.getEcduciId();
        return getObjectPassEcduciId(ecduciId);
    }

    // deal
    @SneakyThrows
    public void deal(ImageDealBo bo) {
        Integer ecducId = bo.getEcducId();
        String path = bo.getPath();
        EcducImages record = new EcducImages();
        record.setEcducId(ecducId);
        record.setImageUrl(path);
        record.setAddTime(System.currentTimeMillis());
        ecducImagesService.insert(record);
    }

    // delete
    @Transactional(rollbackFor = Exception.class)
    public void delete(ImageBaseBo bo) {
        Integer ecduciId = bo.getEcduciId();
        EcducImages record = new EcducImages();
        record.setEcduciId(ecduciId);
        ecducImagesService.delete(record);
        ecduciPositionModel.deletePassEcduciId(ecduciId);
    }

    /***===数据模型===***/
    public EcducImages getObjectPassEcduciId(Integer ecduciId) {
        EcducImages record = new EcducImages();
        record.setEcduciId(ecduciId);
        return ecducImagesService.getObject(record);
    }
}
