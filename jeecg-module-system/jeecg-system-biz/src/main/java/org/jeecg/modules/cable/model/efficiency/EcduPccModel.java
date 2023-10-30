package org.jeecg.modules.cable.model.efficiency;

import cn.hutool.core.util.StrUtil;
import com.google.gson.reflect.TypeToken;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.ServletUtils;
import org.jeecg.modules.cable.controller.efficiency.bo.PccBo;
import org.jeecg.modules.cable.entity.efficiency.EcdPcc;
import org.jeecg.modules.cable.entity.efficiency.EcduPcc;
import org.jeecg.modules.cable.entity.hand.EcdPccBean;
import org.jeecg.modules.cable.entity.pcc.EcProvince;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    // getObject
    public List<EcProvince> getObject(PccBo bo) {
        // 获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer typeId = bo.getTypeId();

        EcduPcc record = new EcduPcc();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setTypeId(typeId);
        EcduPcc ecduPcc = ecduPccService.getObject(record);
        if (ecduPcc == null) {
            throw new RuntimeException("尚未建立数据");
        } else {
            String ip = ServletUtils.getClientIP();
            String basePath;
            if ("127.0.0.1".equals(ip)) {
                basePath = "D:/java/java_data/";
            } else {
                basePath = "/home/";
            }
            if (!new File(basePath + ecduPcc.getTxtUrl()).exists()) {
                basePath = "/home/";
            }
            String txtContent = TxtUtils.readTxtFile(basePath + ecduPcc.getTxtUrl()).get(1);
            List<EcProvince> listProvince = CommonFunction.getGson().fromJson(txtContent,
                    new TypeToken<List<EcProvince>>() {
                    }.getType());
            return listProvince;
        }
    }

    /***===数据模型===***/
    // load
    public void load(Integer typeId, Integer ecuId) {
        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        Integer ecCompanyId = ecUser.getEcCompanyId();

        List<EcProvince> listProvince = ecProvinceModel.getListStart();
        EcduPcc recordEcduPcc = new EcduPcc();
        recordEcduPcc.setTypeId(typeId);
        recordEcduPcc.setEcCompanyId(ecCompanyId);
        EcduPcc ecduPcc = ecduPccService.getObject(recordEcduPcc);
        EcdPcc recordEcdPcc = new EcdPcc();
        recordEcdPcc.setTypeId(typeId);
        EcdPcc object = ecdPccService.getObject(recordEcdPcc);
        String areaJson = CommonFunction.getTxtContent(object.getTxtUrl());
        areaJson = StrUtil.isBlank(areaJson) ? "" : areaJson;
        List<String> txtList = new ArrayList<>();
        List<String> txtListProvince = new ArrayList<>();
        txtList.add(areaJson);
        deal(typeId, ecCompanyId, txtList);
        object = ecdPccService.getObject(recordEcdPcc);
        log.info("object + " + CommonFunction.getGson().toJson(object));
        if (ecduPcc != null && !Objects.equals(object.getEffectTime(), ecduPcc.getEffectTime())) {
            List<EcdPccBean> objectListBean = CommonFunction.getGson().fromJson(areaJson,
                    new TypeToken<List<EcdPccBean>>() {
                    }.getType());// 原始数据
            String areaStr = CommonFunction.getTxtContent(ecduPcc.getTxtUrl());// 用户表中的txtUrl中的内容
            if (StrUtil.isNotBlank(areaStr)) {
                List<EcdPccBean> listBean = CommonFunction.getGson().fromJson(new StringReader(areaStr),
                        new TypeToken<List<EcdPccBean>>() {
                        }.getType());// 用户的数据
                // log.info(String.valueOf(listBean.size()));
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
                    Boolean isExists = isExists(listNew, provinceName);
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
                // 自定义区域
                txtList = new ArrayList<>();
                txtList.add(CommonFunction.getGson().toJson(listNew));
                deal(typeId, ecCompanyId, txtList);
                // 自定义省
                txtListProvince.add(CommonFunction.getGson().toJson(listNewProvince));
                dealProvince(2, ecCompanyId, txtListProvince);
            }
        }
    }

    // deal
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    public void deal(Integer typeId, Integer ecCompanyId, List<String> txtList) {
        String base_path = "D:/java/java_data/";
        if (!new File(base_path).exists()) {
            base_path = "/home/";
        }
        String path = CommonFunction.pathTxtEcduPcc(base_path, String.valueOf(ecCompanyId), "ecduPcc") + "/ecduPcc.txt";
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

    // dealProvince 增加单独省的逻辑
    @SneakyThrows
    public void dealProvince(Integer typeId, Integer ecCompanyId, List<String> txtList) {
        String basePath = "D:/java/java_data/";
        if (!new File(basePath).exists()) {
            basePath = "/home/";
        }
        String path = CommonFunction.pathTxtEcduPcc(basePath, String.valueOf(ecCompanyId), "ecduPcc") + "/ecduPccProvince.txt";
        String filePath = basePath + path;
        TxtUtils.writeTxtFile(filePath, txtList);
        EcduPcc record = new EcduPcc();
        record.setTypeId(typeId);
        record.setEcCompanyId(ecCompanyId);
        record.setTxtUrl(path);
        record.setEffectTime(System.currentTimeMillis());
        ecduPccService.delete(record);
        ecduPccService.insert(record);
    }

    // isExists
    public Boolean isExists(List<EcdPccBean> list, String name) {
        Boolean isContains = false;
        for (EcdPccBean ecdPccBean : list) {
            String provinceName = ecdPccBean.getProvinceName();
            if (name.equals(provinceName)) {
                return true;
            }
        }
        return isContains;
    }

    // getListMoneyCustomer 获取自定义省的列表
    public List<EcbudMoney> getListMoneyCustom(Integer ecCompanyId) {
        List<EcbudMoney> listNew = new ArrayList<>();
        List<EcbudMoney> listMoney;
        EcbuDelivery recordDelivery = new EcbuDelivery();
        recordDelivery.setEcCompanyId(ecCompanyId);
        recordDelivery.setStartType(true);
        List<EcbuDelivery> list = ecbuDeliveryService.getList(recordDelivery);
        for (EcbuDelivery ecbuDelivery : list) {
            Integer ecbudId = ecbuDelivery.getEcbudId();
            EcbudMoney record = new EcbudMoney();
            record.setEcbudId(ecbudId);
            record.setEcpId(0);
            listMoney = ecbudMoneyService.getList(record);
            listNew.addAll(listMoney);
        }
        return listNew;
    }
}
