package org.jeecg.modules.cable.model.efficiency;

import com.google.gson.reflect.TypeToken;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.efficiency.bo.EcdCollectBo;
import org.jeecg.modules.cable.entity.efficiency.EcdCollect;
import org.jeecg.modules.cable.entity.quality.EcquLevel;
import org.jeecg.modules.cable.entity.systemEcable.*;
import org.jeecg.modules.cable.entity.userCommon.EcbuPcompany;
import org.jeecg.modules.cable.entity.userCommon.EcbuStore;
import org.jeecg.modules.cable.entity.userCommon.EcbulUnit;
import org.jeecg.modules.cable.service.efficiency.EcdCollectService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.jeecg.modules.cable.tools.TxtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EcdCollectModel {

    @Value("${txt.path}")
    private String txtPath;
    @Resource
    EcdCollectService ecdCollectService;


    public Map<String, Object> getObject(EcdCollectBo bo) {

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        // 数据类型
        Integer typeId = bo.getTypeId();

        EcdCollect record = new EcdCollect();
        record.setTypeId(typeId);
        record.setEcCompanyId(sysUser.getEcCompanyId());
        EcdCollect ecdCollect = ecdCollectService.getObject(record);

        Map<String, Object> map = new HashMap<>();
        if (ecdCollect == null) {
            if (typeId == 1) {
                throw new RuntimeException("未获取到仓库数据");
            } else {
                throw new RuntimeException("未获取到质量等级数据");
            }
        } else {
            String txtContent = TxtUtils.readTxtFile(txtPath + ecdCollect.getTxtUrl()).get(1);
            // System.out.println(txtContent);
            if (typeId == 1) {// 用户仓库
                List<EcbuStore> listStore = CommonFunction.getGson().fromJson(txtContent, new TypeToken<List<EcbuStore>>() {
                }.getType());
                map.put("listStore", listStore);
            } else if (typeId == 2) {// 质量等级
                List<EcquLevel> listLevel = CommonFunction.getGson().fromJson(txtContent, new TypeToken<List<EcquLevel>>() {
                }.getType());
                map.put("listLevel", listLevel);
            } else if (typeId == 3) {// 用户导体
                List<EcbConductor> listConductor = CommonFunction.getGson().fromJson(txtContent, new TypeToken<List<EcbConductor>>() {
                }.getType());
                map.put("listConductor", listConductor);
            } else if (typeId == 4) {// 用户云母带
                List<EcbMicaTape> listMicatape = CommonFunction.getGson().fromJson(txtContent, new TypeToken<List<EcbMicaTape>>() {
                }.getType());
                map.put("listMicatape", listMicatape);
            } else if (typeId == 5) {// 用户绝缘数据
                List<EcbInsulation> listInsulation = CommonFunction.getGson().fromJson(txtContent, new TypeToken<List<EcbInsulation>>() {
                }.getType());
                map.put("listInsulation", listInsulation);
            } else if (typeId == 6) {// 用户填充物数据
                List<EcbInfilling> listInfilling = CommonFunction.getGson().fromJson(txtContent, new TypeToken<List<EcbInfilling>>() {
                }.getType());
                map.put("listInfilling", listInfilling);
            } else if (typeId == 7) {// 用户包带数据
                List<EcbBag> listBag = CommonFunction.getGson().fromJson(txtContent, new TypeToken<List<EcbBag>>() {
                }.getType());
                map.put("listBag", listBag);
            } else if (typeId == 8) {// 用户屏蔽数据
                List<EcbShield> listShield = CommonFunction.getGson().fromJson(txtContent, new TypeToken<List<EcbShield>>() {
                }.getType());
                map.put("listShield", listShield);
            } else if (typeId == 9) {// 用户钢带数据
                List<EcbSteelBand> listSteelband = CommonFunction.getGson().fromJson(txtContent, new TypeToken<List<EcbSteelBand>>() {
                }.getType());
                map.put("listSteelband", listSteelband);
            } else if (typeId == 10) {// 用户单位长度数据
                List<EcbulUnit> listEcbulUnit = CommonFunction.getGson().fromJson(txtContent, new TypeToken<List<EcbulUnit>>() {
                }.getType());
                map.put("listEcbulUnit", listEcbulUnit);
            } else if (typeId == 11) {// 用户平台公司数据
                List<EcbuPcompany> listPcompany = CommonFunction.getGson().fromJson(txtContent, new TypeToken<List<EcbuPcompany>>() {
                }.getType());
                map.put("listPcompany", listPcompany);
            }
            map.put("path", txtPath + ecdCollect.getTxtUrl());
            return map;
        }

    }

    /***===数据模型===***/

    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    public void deal(Integer ecCompanyId, Integer typeId, List<String> txtList) {
        String filePath = null;
        if (typeId == 1) {// 仓库
            filePath = CommonFunction.pathTxt(txtPath, String.valueOf(ecCompanyId), "ecdCollect") + "/ecbuStore.txt";
        } else if (typeId == 2) {// 质量等级
            filePath = CommonFunction.pathTxt(txtPath, String.valueOf(ecCompanyId), "ecdCollect") + "/ecquLevel.txt";
        } else if (typeId == 3) {// 用户导体数据
            filePath = CommonFunction.pathTxt(txtPath, String.valueOf(ecCompanyId), "ecdCollect") + "/ecbuConductor.txt";
        } else if (typeId == 4) {// 用户云母带
            filePath = CommonFunction.pathTxt(txtPath, String.valueOf(ecCompanyId), "ecdCollect") + "/ecbuMicatape.txt";
        } else if (typeId == 5) {// 用户绝缘数据
            filePath = CommonFunction.pathTxt(txtPath, String.valueOf(ecCompanyId), "ecdCollect") + "/ecbuInsulation.txt";
        } else if (typeId == 6) {// 用户填充物数据
            filePath = CommonFunction.pathTxt(txtPath, String.valueOf(ecCompanyId), "ecdCollect") + "/ecbuInfilling.txt";
        } else if (typeId == 7) {// 用户包带数据
            filePath = CommonFunction.pathTxt(txtPath, String.valueOf(ecCompanyId), "ecdCollect") + "/ecbuBag.txt";
        } else if (typeId == 8) {// 用户屏蔽数据
            filePath = CommonFunction.pathTxt(txtPath, String.valueOf(ecCompanyId), "ecdCollect") + "/ecbuShield.txt";
        } else if (typeId == 9) {// 用户钢带数据
            filePath = CommonFunction.pathTxt(txtPath, String.valueOf(ecCompanyId), "ecdCollect") + "/ecbuSteelband.txt";
        } else if (typeId == 10) {// 用户单位长度数据
            filePath = CommonFunction.pathTxt(txtPath, String.valueOf(ecCompanyId), "ecdCollect") + "/ecbulUnit.txt";
        } else if (typeId == 11) {// 用户平台公司数据
            filePath = CommonFunction.pathTxt(txtPath, String.valueOf(ecCompanyId), "ecdCollect") + "/ecbuPcompany.txt";
        }
        TxtUtils.writeTxtFile(txtPath + filePath, txtList);
        EcdCollect record = new EcdCollect();
        record.setEcCompanyId(ecCompanyId);
        record.setTypeId(typeId);
        record.setTxtUrl(filePath);
        record.setEffectTime(System.currentTimeMillis());
        EcdCollect ecdCollect = ecdCollectService.getObject(record);
        if (ecdCollect == null) {// 插入
            ecdCollectService.insert(record);
        } else {// 更新
            ecdCollectService.update(record);
        }
    }
}
