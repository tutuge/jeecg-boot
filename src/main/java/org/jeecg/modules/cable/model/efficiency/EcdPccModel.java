package org.jeecg.modules.cable.model.efficiency;

import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.jeecg.modules.cable.controller.pcc.bo.EcdPccBo;
import org.jeecg.modules.cable.entity.efficiency.EcdPcc;
import org.jeecg.modules.cable.entity.hand.EcdPccBean;
import org.jeecg.modules.cable.entity.pcc.EcProvince;
import org.jeecg.modules.cable.model.pcc.EcProvinceModel;
import org.jeecg.modules.cable.service.efficiency.EcdPccService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.jeecg.modules.cable.tools.TxtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class EcdPccModel {
    @Value("${txt.path}")
    private String txtPath;
    @Resource
    EcdPccService ecdPccService;
    @Resource
    EcProvinceModel ecProvinceModel;

    // load
    public void load(EcdPccBo bo) {
        Integer typeId = bo.getTypeId();
        EcdPcc record = new EcdPcc();
        record.setTypeId(typeId);
        ecdPccService.delete(record);
        List<EcdPccBean> listEcdPcc = new ArrayList<>();
        List<EcProvince> listProvince = ecProvinceModel.getListContact();
        for (EcProvince ecProvince : listProvince) {
            EcdPccBean ecdPccBean = new EcdPccBean();
            ecdPccBean.setEcpId(ecProvince.getEcpId());
            ecdPccBean.setProvinceName(ecProvince.getProvinceName());
            listEcdPcc.add(ecdPccBean);
        }
        String areaJson = CommonFunction.getGson().toJson(listEcdPcc);
        List<String> txtList = new ArrayList<>();
        txtList.add(areaJson);
        deal(typeId, txtList);
    }

    /***===数据模型===***/
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    public void deal(Integer typeId, List<String> txtList) {
        String filePath = CommonFunction.pathTxtPcc(txtPath) + "/ecdPcc.txt";
        TxtUtils.writeTxtFile(txtPath + filePath, txtList);
        String path = "lanchacha/txt/pcc/ecdPcc/ecdPcc.txt";
        EcdPcc record = new EcdPcc();
        record.setTypeId(typeId);
        record.setTxtUrl(path);
        record.setEffectTime(System.currentTimeMillis());
        EcdPcc ecdPcc = ecdPccService.getObject(record);
        if (ecdPcc != null) {
            record = new EcdPcc();
            record.setTypeId(typeId);
            ecdPccService.delete(record);
        }
        ecdPccService.insert(record);
    }
}
