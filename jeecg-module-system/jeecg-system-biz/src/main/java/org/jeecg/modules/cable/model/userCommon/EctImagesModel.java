package org.jeecg.modules.cable.model.userCommon;

import org.jeecg.common.system.vo.EcUser;
import org.jeecg.modules.cable.entity.userCommon.EctImages;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.service.userCommon.EctImagesService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.jeecg.modules.cable.tools.SavePath;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Random;

@Service
@Slf4j
public class EctImagesModel {
    @Resource
    EcuLoginModel ecuLoginModel;
    @Resource
    EctImagesService ectImagesService;

    //deal
    public Map<String, Object> deal(HttpServletRequest request, MultipartFile image) throws IOException {
        Map<String, Object> map;
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            String ip = CommonFunction.getIp(request);
            String rand = String.valueOf((new Random()).nextInt(999999999));
            String name = CommonFunction.getMd5Str(CommonFunction.getMd5Str(rand));
            String extend = FilenameUtils.getExtension(image.getOriginalFilename());
            String base_path;
            String path;
            String projectName = "lanchacha";
            String modelName = "user";
            String tableName = "ectImages";
            String typeId = request.getParameter("typeId");
            if ("127.0.0.1".equals(ip)) {
                base_path = "D:/java/java_data/";
            } else {
                base_path = "/home/";
            }
            path = SavePath.path(projectName, modelName, tableName, base_path) + "/" + name + "." + extend;
            image.transferTo(new File(base_path + path));
            EcUser recordEcUser = new EcUser();
            recordEcUser.setEcuId(ecuId);
            EctImages record = new EctImages();
            record.setTypeId(Integer.parseInt(typeId));
            record.setEcuId(ecuId);
            //ectImagesService.delete(record);//删除之前的数据
            record.setImageUrl(path);
            record.setAddTime(System.currentTimeMillis());
            ectImagesService.insert(record);
            map.put("path", "http://101.42.164.66:8001/home/" + path);
            status = 3;//正常获取数据
            code = "200";
            msg = "正常操作数据";
            map.put("basePath", base_path);
            map.put("record", CommonFunction.getGson().toJson(record));
            CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }
}
