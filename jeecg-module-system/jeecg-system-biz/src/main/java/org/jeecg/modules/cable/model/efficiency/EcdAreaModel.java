package org.jeecg.modules.cable.model.efficiency;

import org.jeecg.modules.cable.entity.efficiency.EcdArea;
import org.jeecg.modules.cable.entity.quality.EcuArea;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.modules.cable.service.efficiency.EcdAreaService;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.jeecg.modules.cable.tools.TxtUtils;
import com.google.gson.reflect.TypeToken;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EcdAreaModel {
    @Resource
    EcdAreaService ecdAreaService;
    @Resource
    EcUserService ecUserService;


    //getObject
    public Map<String, Object> getObject(HttpServletRequest request) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        int ecqulId = Integer.parseInt(request.getParameter("ecqulId"));
        EcdArea record = new EcdArea();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setEcqulId(ecqulId);
        EcdArea ecdArea = ecdAreaService.getObject(record);
        if (ecdArea == null) {
            status = 3;//未获取到截面数据
            code = "103";
            msg = "未获取到截面数据";
        } else {
            String ip = CommonFunction.getIp(request);
            String base_path;
            if ("127.0.0.1".equals(ip) || "192.168.1.6".equals(ip)) {
                base_path = "D:/java/java_data/";
            } else {
                base_path = "/home/";
            }
            if (!new File(base_path + ecdArea.getTxtUrl()).exists()) {
                base_path = "/home/";
            }
            String txtContent = TxtUtils.readTxtFile(base_path + ecdArea.getTxtUrl()).get(1);
            List<EcuArea> listArea = CommonFunction.getGson().fromJson(txtContent, new TypeToken<List<EcuArea>>() {
            }.getType());
            map.put("listArea", listArea);
            status = 4;//正常获取数据
            code = "200";
            msg = "正常获取数据";
        }
        CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    /***===数据模型===***/
    //deal
    @SneakyThrows
    public void deal(int ecCompanyId, int ecqulId, List<String> txtList) {
        String base_path = "D:/java/java_data/";
        if (!new File(base_path).exists()) {
            base_path = "/home/";
        }
        String filePath = CommonFunction.pathTxtArea(base_path, String.valueOf(ecCompanyId), "ecdArea", String.valueOf(ecqulId)) + "/ecdArea.txt";
        //log.info(base_path + filePath);
        //log.info(CommonFunction.getGson().toJson(txtList));
        TxtUtils.writeTxtFile(base_path + filePath, txtList);
        EcdArea record = new EcdArea();
        record.setEcCompanyId(ecCompanyId);
        record.setEcqulId(ecqulId);
        record.setTxtUrl(filePath);
        record.setEffectTime(System.currentTimeMillis());
        EcdArea ecdArea = ecdAreaService.getObject(record);
        if (ecdArea == null) {//插入
            ecdAreaService.insert(record);
        } else {//更新
            ecdAreaService.update(record);
        }
    }

    //isExistsPassEcqulId 通过质量等级ID判断该截面是否存在
    public boolean isExistsPassEcqulId(int ecqulId, String areaStr) {
        boolean isExists = false;
        log.info("ecqulId + " + ecqulId);
        EcdArea record = new EcdArea();
        record.setEcqulId(ecqulId);
        log.info("record + " + CommonFunction.getGson().toJson(record));
        EcdArea ecdArea = ecdAreaService.getObject(record);
        String base_path = "D:/java/java_data/";
        if (!new File(base_path).exists()) {
            base_path = "/home/";
        }
        log.info("ecdArea + " + CommonFunction.getGson().toJson(ecdArea));
        String txtContent = TxtUtils.readTxtFile(base_path + ecdArea.getTxtUrl()).get(1);
        List<EcuArea> listArea = CommonFunction.getGson().fromJson(txtContent, new TypeToken<List<EcuArea>>() {
        }.getType());
        for (EcuArea ecuArea : listArea) {
            if (ecuArea.getAreaStr().equals(areaStr)) {
                isExists = true;
                break;
            }
        }
        return isExists;
    }
}
