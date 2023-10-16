package org.jeecg.modules.cable.model.efficiency;

import org.jeecg.modules.cable.entity.efficiency.EcdPcc;
import org.jeecg.modules.cable.entity.efficiency.EcduPcc;
import org.jeecg.modules.cable.entity.hand.EcdPccBean;
import org.jeecg.modules.cable.entity.pcc.EcProvince;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.modules.cable.entity.userDelivery.EcbuDelivery;
import org.jeecg.modules.cable.entity.userDelivery.EcbudMoney;
import org.jeecg.modules.cable.model.pcc.EcProvinceModel;
import org.jeecg.modules.cable.service.efficiency.EcdPccService;
import org.jeecg.modules.cable.service.efficiency.EcduPccService;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userDelivery.EcbuDeliveryService;
import org.jeecg.modules.cable.service.userDelivery.EcbudMoneyService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.jeecg.modules.cable.tools.TxtUtils;
import com.google.gson.reflect.TypeToken;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.StringReader;
import java.util.*;

@Service
@Slf4j
public class EcduPccModel {
    @Resource
    EcduPccService ecduPccService;
    @Resource
    EcUserService ecUserService;
    @Resource
    EcdPccService ecdPccService;
    @Resource
    EcProvinceModel ecProvinceModel;
    @Resource
    EcbudMoneyService ecbudMoneyService;
    @Resource
    EcbuDeliveryService ecbuDeliveryService;

    //getObject
    public Map<String, Object> getObject(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        int typeId = Integer.parseInt(request.getParameter("typeId"));
        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        EcduPcc record = new EcduPcc();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setTypeId(typeId);
        EcduPcc ecduPcc = ecduPccService.getObject(record);
        if (ecduPcc == null) {
            status = 3;//尚未建立数据
            code = "103";
            msg = "尚未建立数据";
        } else {
            String ip = CommonFunction.getIp(request);
            String base_path;
            if ("127.0.0.1".equals(ip) || "192.168.1.6".equals(ip)) {
                base_path = "D:/java/java_data/";
            } else {
                base_path = "/home/";
            }
            if (!new File(base_path + ecduPcc.getTxtUrl()).exists()) {
                base_path = "/home/";
            }
            String txtContent = TxtUtils.readTxtFile(base_path + ecduPcc.getTxtUrl()).get(1);
            List<EcProvince> listProvince = CommonFunction.getGson().fromJson(txtContent,
                    new TypeToken<List<EcProvince>>() {
                    }.getType());
            map.put("listProvince", listProvince);
            status = 4;//正常获取数据
            code = "200";
            msg = "正常获取数据";
        }
        CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    /***===数据模型===***/
    //load
    public void load(HttpServletRequest request, int typeId, int ecuId) {
        int ecCompanyId;
        if (request.getParameter("ecCompanyId") != null) {
            ecCompanyId = Integer.parseInt(request.getParameter("ecCompanyId"));
        } else {
            EcUser recordEcUser = new EcUser();
            recordEcUser.setEcuId(ecuId);
            EcUser ecUser = ecUserService.getObject(recordEcUser);
            ecCompanyId = ecUser.getEcCompanyId();
        }
        List<EcProvince> listProvince = ecProvinceModel.getListStart();
        EcduPcc recordEcduPcc = new EcduPcc();
        recordEcduPcc.setTypeId(typeId);
        recordEcduPcc.setEcCompanyId(ecCompanyId);
        EcduPcc ecduPcc = ecduPccService.getObject(recordEcduPcc);
        EcdPcc recordEcdPcc = new EcdPcc();
        recordEcdPcc.setTypeId(typeId);
        EcdPcc object = ecdPccService.getObject(recordEcdPcc);
        String areaJson = CommonFunction.getTxtContent(request, object.getTxtUrl());
        List<String> txtList = new ArrayList<>();
        List<String> txtListProvince = new ArrayList<>();
        txtList.add(areaJson);
        deal(typeId, ecCompanyId, txtList);
        object = ecdPccService.getObject(recordEcdPcc);
        ecduPcc = ecduPccService.getObject(recordEcduPcc);
        log.info("object + " + CommonFunction.getGson().toJson(object));
        if (ecduPcc != null && !Objects.equals(object.getEffectTime(), ecduPcc.getEffectTime())) {
            List<EcdPccBean> objectListBean = CommonFunction.getGson().fromJson(areaJson,
                    new TypeToken<List<EcdPccBean>>() {
                    }.getType());//原始数据
            String areaStr = CommonFunction.getTxtContent(request, ecduPcc.getTxtUrl());//用户表中的txtUrl中的内容
            //areaStr = CommonFunction.getGson().toJson(areaStr);
            List<EcdPccBean> listBean = CommonFunction.getGson().fromJson(new StringReader(areaStr),
                    new TypeToken<List<EcdPccBean>>() {
                    }.getType());//用户的数据
            //log.info(String.valueOf(listBean.size()));
            EcdPccBean objectBean;
            List<EcdPccBean> listNew = new ArrayList<>();
            List<EcdPccBean> listNewProvince = new ArrayList<>();
            if (listBean != null) {
                for (EcdPccBean ecdPccBean : listBean) {
                    if (ecdPccBean.getEcpId() == 0) {
                        objectBean = new EcdPccBean();
                        objectBean.setEcpId(0);
                        objectBean.setProvinceName(ecdPccBean.getProvinceName());
                        listNew.add(objectBean);
                        listNewProvince.add(objectBean);
                    }
                }
            }
            for (EcdPccBean ecdPccBean : objectListBean) {
                objectBean = new EcdPccBean();
                objectBean.setEcpId(ecdPccBean.getEcpId());
                objectBean.setProvinceName(ecdPccBean.getProvinceName());
                listNew.add(objectBean);
            }
            List<EcbudMoney> listMoney = getListMoneyCustom(ecCompanyId);
            for (EcbudMoney ecbudMoney : listMoney) {
                String provinceName = ecbudMoney.getProvinceName();
                boolean isExists = isExists(listNew, provinceName);
                if (!isExists) {
                    log.info("provinceName + " + provinceName);
                    objectBean = new EcdPccBean();
                    objectBean.setEcpId(ecbudMoney.getEcpId());
                    objectBean.setProvinceName(ecbudMoney.getProvinceName());
                    listNew.add(objectBean);
                }
            }
            for (EcProvince ecProvince : listProvince) {
                objectBean = new EcdPccBean();
                objectBean.setEcpId(ecProvince.getEcpId());
                objectBean.setProvinceName(ecProvince.getProvinceName());
                listNewProvince.add(objectBean);
            }
            //自定义区域
            txtList = new ArrayList<>();
            txtList.add(CommonFunction.getGson().toJson(listNew));
            deal(typeId, ecCompanyId, txtList);
            //自定义省
            txtListProvince.add(CommonFunction.getGson().toJson(listNewProvince));
            dealProvince(2, ecCompanyId, txtListProvince);
        }
    }

    //deal
    @SneakyThrows
    public void deal(int typeId, int ecCompanyId, List<String> txtList) {
        String base_path = "D:/java/java_data/";
        if (!new File(base_path).exists()) {
            base_path = "/home/";
        }
        String path = CommonFunction.pathTxtEcduPcc(base_path, String.valueOf(ecCompanyId), "ecduPcc") +
                "/ecduPcc.txt";
        String filePath = base_path + path;
        TxtUtils.writeTxtFile(filePath, txtList);
        EcduPcc record = new EcduPcc();
        record.setTypeId(typeId);
        record.setEcCompanyId(ecCompanyId);
        record.setTxtUrl(path);
        record.setEffectTime(System.currentTimeMillis());
        ecduPccService.delete(record);
        ecduPccService.insert(record);
    }

    //dealProvince 增加单独省的逻辑
    @SneakyThrows
    public void dealProvince(int typeId, int ecCompanyId, List<String> txtList) {
        String base_path = "D:/java/java_data/";
        if (!new File(base_path).exists()) {
            base_path = "/home/";
        }
        String path = CommonFunction.pathTxtEcduPcc(base_path, String.valueOf(ecCompanyId), "ecduPcc") +
                "/ecduPccProvince.txt";
        String filePath = base_path + path;
        TxtUtils.writeTxtFile(filePath, txtList);
        EcduPcc record = new EcduPcc();
        record.setTypeId(typeId);
        record.setEcCompanyId(ecCompanyId);
        record.setTxtUrl(path);
        record.setEffectTime(System.currentTimeMillis());
        ecduPccService.delete(record);
        ecduPccService.insert(record);
    }

    //isExists
    public boolean isExists(List<EcdPccBean> list, String name) {
        boolean isContains = false;
        for (EcdPccBean ecdPccBean : list) {
            String provinceName = ecdPccBean.getProvinceName();
            if (name.equals(provinceName)) {
                return true;
            }
        }
        return isContains;
    }

    //getListMoneyCustomer 获取自定义省的列表
    public List<EcbudMoney> getListMoneyCustom(int ecCompanyId) {
        List<EcbudMoney> listNew = new ArrayList<>();
        List<EcbudMoney> listMoney;
        EcbuDelivery recordDelivery = new EcbuDelivery();
        recordDelivery.setEcCompanyId(ecCompanyId);
        recordDelivery.setStartType(true);
        List<EcbuDelivery> list = ecbuDeliveryService.getList(recordDelivery);
        for (EcbuDelivery ecbuDelivery : list) {
            int ecbudId = ecbuDelivery.getEcbudId();
            EcbudMoney record = new EcbudMoney();
            record.setEcbudId(ecbudId);
            record.setEcpId(0);
            listMoney = ecbudMoneyService.getList(record);
            listNew.addAll(listMoney);
        }
        return listNew;
    }
}
