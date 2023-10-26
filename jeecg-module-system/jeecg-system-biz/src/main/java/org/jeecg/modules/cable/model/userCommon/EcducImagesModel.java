package org.jeecg.modules.cable.model.userCommon;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.jeecg.modules.cable.controller.userCommon.image.bo.ImageBaseBo;
import org.jeecg.modules.cable.entity.userCommon.EcducImages;
import org.jeecg.modules.cable.service.userCommon.EcducImagesService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.jeecg.modules.cable.tools.SavePath;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class EcducImagesModel {
    @Resource
    EcducImagesService ecducImagesService;
    @Resource
    EcduciPositionModel ecduciPositionModel;

    //getList
    public List<EcducImages> getList(ImageBaseBo bo) {
        Integer ecducId = bo.getEcduciId();
        EcducImages record = new EcducImages();
        record.setEcducId(ecducId);
        List<EcducImages> list = ecducImagesService.getList(record);
        for (EcducImages ecducImages : list) {
            if (!"".equals(ecducImages.getImageUrl())) {
                ecducImages.setImageUrl("http://101.42.164.66:8001/home/" + ecducImages.getImageUrl());
            }
        }
        return list;
    }

    //getObject
    public EcducImages getObject(ImageBaseBo bo) {
        Integer ecduciId = bo.getEcduciId();
        EcducImages ecducImages = getObjectPassEcduciId(ecduciId);
        if (ecducImages != null) {
            ecducImages.setImageUrl("http://101.42.164.66:8001/home/" + ecducImages.getImageUrl());
        }
        return ecducImages;
    }

    //deal
    @SneakyThrows
    public void deal(ImageBaseBo bo, HttpServletRequest request, MultipartFile image) {

        Integer ecducId = bo.getEcduciId();
        String ip = CommonFunction.getIp(request);
        String rand = String.valueOf((new Random()).nextInt(999999999));
        String name = CommonFunction.getMd5Str(CommonFunction.getMd5Str(rand));
        String extend = FilenameUtils.getExtension(image.getOriginalFilename());
        String base_path;
        String path;
        String projectName = "lanchacha";
        String modelName = "user";
        String tableName = "ectImages";
        if ("127.0.0.1".equals(ip)) {
            base_path = "D:/java/java_data/";
        } else {
            base_path = "/home/";
        }
        path = SavePath.path(projectName, modelName, tableName, base_path) + "/" + name + "." + extend;
        image.transferTo(new File(base_path + path));
        EcducImages record = new EcducImages();
        record.setEcducId(ecducId);
        record.setImageUrl(path);
        record.setAddTime(System.currentTimeMillis());
        ecducImagesService.insert(record);
    }

    //delete
    public void delete(ImageBaseBo bo) {
        int ecduciId = bo.getEcduciId();
        EcducImages record = new EcducImages();
        record.setEcduciId(ecduciId);
        ecducImagesService.delete(record);
        ecduciPositionModel.deletePassEcduciId(ecduciId);
    }

    /***===数据模型===***/
//deal
    public EcducImages getObjectPassEcduciId(int ecduciId) {
        EcducImages record = new EcducImages();
        record.setEcduciId(ecduciId);
        return ecducImagesService.getObject(record);
    }
}
