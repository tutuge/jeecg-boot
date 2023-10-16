package org.jeecg.modules.cable.model.userCommon;

import org.jeecg.modules.cable.entity.userCommon.EcducImages;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.service.userCommon.EcducImagesService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.jeecg.modules.cable.tools.SavePath;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@Slf4j
public class EcducImagesModel {
    @Resource
    EcuLoginModel ecuLoginModel;
    @Resource
    EcducImagesService ecducImagesService;
    @Resource
    EcduciPositionModel ecduciPositionModel;

    //getList
    public Map<String, Object> getList(HttpServletRequest request) {
        Map<String, Object> map;
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            int ecducId = Integer.parseInt(request.getParameter("ecducId"));
            EcducImages record = new EcducImages();
            record.setEcducId(ecducId);
            List<EcducImages> list = ecducImagesService.getList(record);
            for (EcducImages ecducImages : list) {
                if (!"".equals(ecducImages.getImageUrl())) {
                    ecducImages.setImageUrl("http://101.42.164.66:8001/home/" + ecducImages.getImageUrl());
                }
            }
            map.put("list", list);
            status = 3;//正常获取数据
            code = "200";
            msg = "正常获取数据";
            CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    //getObject
    public Map<String, Object> getObject(HttpServletRequest request) {
        Map<String, Object> map;
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            int ecduciId = Integer.parseInt(request.getParameter("ecduciId"));
            EcducImages ecducImages = getObjectPassEcduciId(ecduciId);
            if (ecducImages != null) {
                ecducImages.setImageUrl("http://101.42.164.66:8001/home/" + ecducImages.getImageUrl());
            }
            map.put("object", ecducImages);
            status = 3;//正常获取数据
            code = "200";
            msg = "正常获取数据";
            CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    //deal
    @SneakyThrows
    public Map<String, Object> deal(HttpServletRequest request, MultipartFile image) {
        Map<String, Object> map;
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            int ecducId = Integer.parseInt(request.getParameter("ecducId"));
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
            status = 3;//正常插入数据
            code = "200";
            msg = "正常插入数据";
            CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    //delete
    public Map<String, Object> delete(HttpServletRequest request) {
        Map<String, Object> map;
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            int ecduciId = Integer.parseInt(request.getParameter("ecduciId"));
            EcducImages record = new EcducImages();
            record.setEcduciId(ecduciId);
            ecducImagesService.delete(record);
            ecduciPositionModel.deletePassEcduciId(ecduciId);
            status = 3;//正常操作数据
            code = "200";
            msg = "正常操作数据";
            CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    /***===数据模型===***/
    //deal
    public EcducImages getObjectPassEcduciId(int ecduciId) {
        EcducImages record = new EcducImages();
        record.setEcduciId(ecduciId);
        return ecducImagesService.getObject(record);
    }
}
