package org.jeecg.modules.cable.model.efficiency;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.google.gson.reflect.TypeToken;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class EcduPccModel {

    @Value("${txt.path}")
    private String txtPath;

    @Resource
    EcduPccService ecduPccService;
    @Resource
    EcdPccService ecdPccService;
    @Resource
    EcProvinceModel ecProvinceModel;
    @Resource
    EcbudMoneyService ecbudMoneyService;
    @Resource
    EcbuDeliveryService ecbuDeliveryService;


    public List<EcProvince> getObject(PccBo bo) {
        // 获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer typeId = bo.getTypeId();
        EcduPcc ecduPcc = ecduPccService.getByTypeCompany(typeId, sysUser.getEcCompanyId());
        if (ecduPcc == null) {
            throw new RuntimeException("尚未建立数据");
        }
        String txtContent = TxtUtils.readTxtFile(txtPath + ecduPcc.getTxtUrl()).get(1);
        List<EcProvince> listProvince = CommonFunction.getGson().fromJson(txtContent,
                new TypeToken<List<EcProvince>>() {
                }.getType());
        return listProvince;
    }

    /***===数据模型===***/
    // load
    public void load(Integer typeId, Integer ecCompanyId) {
        List<EcProvince> listProvince = ecProvinceModel.getListStart();
        EcduPcc ecduPcc = ecduPccService.getByTypeCompany(typeId, ecCompanyId);
        EcdPcc recordEcdPcc = new EcdPcc();
        recordEcdPcc.setTypeId(typeId);
        EcdPcc object = ecdPccService.getObject(recordEcdPcc);
        String areaJson = CommonFunction.getTxtContent(txtPath, object.getTxtUrl());
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
            if (ObjUtil.isNull(objectListBean)) {
                objectListBean = new ArrayList<>();
            }
            String areaStr = CommonFunction.getTxtContent(txtPath, ecduPcc.getTxtUrl());// 用户表中的txtUrl中的内容
            //if (StrUtil.isNotBlank(areaStr)) {
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
            //}
        }
    }


    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    public void deal(Integer typeId, Integer ecCompanyId, List<String> txtList) {
        String path = CommonFunction.pathTxtEcduPcc(txtPath, String.valueOf(ecCompanyId), "ecduPcc") + "/ecduPcc.txt";
        String filePath = txtPath + path;
        TxtUtils.writeTxtFile(filePath, txtList);
        ecduPccService.deleteByTypeCompany(typeId, ecCompanyId);
        EcduPcc record = new EcduPcc();
        record.setTypeId(typeId);
        record.setEcCompanyId(ecCompanyId);
        record.setTxtUrl(path);
        record.setEffectTime(System.currentTimeMillis());
        ecduPccService.insert(record);
    }

    // dealProvince 增加单独省的逻辑
    @SneakyThrows
    public void dealProvince(Integer typeId, Integer ecCompanyId, List<String> txtList) {
        String path = CommonFunction.pathTxtEcduPcc(txtPath, String.valueOf(ecCompanyId), "ecduPcc") + "/ecduPccProvince.txt";
        String filePath = txtPath + path;
        TxtUtils.writeTxtFile(filePath, txtList);
        EcduPcc record = new EcduPcc();
        record.setTypeId(typeId);
        record.setEcCompanyId(ecCompanyId);
        record.setTxtUrl(path);
        record.setEffectTime(System.currentTimeMillis());
        ecduPccService.deleteByTypeCompany(typeId, ecCompanyId);
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
