package org.jeecg.modules.cable.model.efficiency;

import org.jeecg.modules.cable.entity.efficiency.EcdPcc;
import org.jeecg.modules.cable.entity.hand.EcdPccBean;
import org.jeecg.modules.cable.entity.pcc.EcProvince;
import org.jeecg.modules.cable.model.pcc.EcProvinceModel;
import org.jeecg.modules.cable.service.efficiency.EcdPccService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.jeecg.modules.cable.tools.TxtUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class EcdPccModel {
    @Resource
    EcdPccService ecdPccService;
    @Resource
    EcProvinceModel ecProvinceModel;

    //load
    public void load(HttpServletRequest request) {
        Integer typeId = Integer.parseInt(request.getParameter("typeId"));
        EcdPcc record = new EcdPcc();
        record.setTypeId(typeId);
        ecdPccService.delete(record);
        List<EcdPccBean> listEcdPcc = new ArrayList<>();
        List<EcProvince> listProvince = ecProvinceModel.getListContact();
        Integer i = 0;
        for (EcProvince ecProvince : listProvince) {
            EcdPccBean ecdPccBean = new EcdPccBean();
            ecdPccBean.setEcpId(ecProvince.getEcpId());
            ecdPccBean.setProvinceName(ecProvince.getProvinceName());
            listEcdPcc.add(ecdPccBean);
            System.out.println("i + " + i);
            i++;
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
        String base_path = "D:/java/java_data/";
        if (!new File(base_path).exists()) {
            base_path = "/home/";
        }
        String filePath = CommonFunction.pathTxtPcc(base_path) + "/ecdPcc.txt";
        TxtUtils.writeTxtFile(base_path + filePath, txtList);
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
