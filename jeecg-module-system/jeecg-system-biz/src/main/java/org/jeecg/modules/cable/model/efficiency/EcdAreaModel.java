package org.jeecg.modules.cable.model.efficiency;

import com.google.gson.reflect.TypeToken;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.ServletUtils;
import org.jeecg.modules.cable.controller.efficiency.bo.EcdAreaBo;
import org.jeecg.modules.cable.entity.efficiency.EcdArea;
import org.jeecg.modules.cable.entity.quality.EcuArea;
import org.jeecg.modules.cable.service.efficiency.EcdAreaService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.jeecg.modules.cable.tools.TxtUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

@Service
@Slf4j
public class EcdAreaModel {
    @Resource
    EcdAreaService ecdAreaService;


    // getObject
    public List<EcuArea> getObject(EcdAreaBo bo) {
        // 获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        Integer ecqulId = bo.getEcqulId();

        EcdArea record = new EcdArea();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setEcqulId(ecqulId);
        EcdArea ecdArea = ecdAreaService.getObject(record);
        if (ecdArea == null) {
            throw new RuntimeException("未获取到截面数据");
        } else {
            String ip = ServletUtils.getClientIP();
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
            return listArea;
        }
    }

    /***===数据模型===***/
    // deal
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    public void deal(Integer ecCompanyId, Integer ecqulId, List<String> txtList) {
        String base_path = "D:/java/java_data/";
        if (!new File(base_path).exists()) {
            base_path = "/home/";
        }
        String filePath = CommonFunction.pathTxtArea(base_path, String.valueOf(ecCompanyId), "ecdArea", String.valueOf(ecqulId)) + "/ecdArea.txt";
        // log.info(base_path + filePath);
        // log.info(CommonFunction.getGson().toJson(txtList));
        TxtUtils.writeTxtFile(base_path + filePath, txtList);
        EcdArea record = new EcdArea();
        record.setEcCompanyId(ecCompanyId);
        record.setEcqulId(ecqulId);
        record.setTxtUrl(filePath);
        record.setEffectTime(System.currentTimeMillis());
        EcdArea ecdArea = ecdAreaService.getObject(record);
        if (ecdArea == null) {// 插入
            ecdAreaService.insert(record);
        } else {// 更新
            ecdAreaService.update(record);
        }
    }

    // isExistsPassEcqulId 通过质量等级ID判断该截面是否存在
    public Boolean isExistsPassEcqulId(Integer ecqulId, String areaStr) {
        Boolean isExists = false;
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
