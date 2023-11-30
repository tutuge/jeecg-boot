package org.jeecg.modules.cable.model.systemDelivery;

import cn.hutool.core.util.ObjUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.controller.systemDelivery.money.bo.*;
import org.jeecg.modules.cable.controller.systemDelivery.money.vo.EcbdMoneyListVo;
import org.jeecg.modules.cable.entity.pcc.EcProvince;
import org.jeecg.modules.cable.entity.systemDelivery.EcbdMoney;
import org.jeecg.modules.cable.service.pcc.EcProvinceService;
import org.jeecg.modules.cable.service.systemDelivery.EcbdMoneyService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class EcbdMoneyModel {
    @Resource
    EcbdMoneyService ecbdMoneyService;
    @Resource
    EcProvinceService ecProvinceService;


    public EcbdMoneyListVo getList(EcbdMoneyListBo bo) {
        EcbdMoney record = new EcbdMoney();
        record.setStartType(bo.getStartType());
        record.setEcbdId(bo.getEcbdId());
        List<EcbdMoney> list = ecbdMoneyService.getList(record);
        long count = ecbdMoneyService.getCount(record);
        return new EcbdMoneyListVo(list, count);
    }


    @Transactional(rollbackFor = Exception.class)
    public String deal(EcbdMoneyDealBo bo) {
        Integer ecbdmId = bo.getEcbdmId();
        Integer ecbdId = bo.getEcbdId();
        String provinceName = bo.getProvinceName();
        Integer firstWeight = bo.getFirstWeight();
        BigDecimal firstMoney = bo.getFirstMoney();
        BigDecimal continueMoney = bo.getContinueMoney();

        EcbdMoney money = new EcbdMoney();
        money.setEcbdmId(ecbdmId);
        money.setProvinceName(provinceName);
        EcbdMoney passProvinceName = ecbdMoneyService.getObjectPassProvinceName(money);
        if (passProvinceName != null) {
            throw new RuntimeException("名称已占用");
        }
        String msg = "";
        EcbdMoney record = new EcbdMoney();
        record.setEcbdmId(ecbdmId);
        record.setEcbdId(ecbdId);
        record.setProvinceName(provinceName);
        record.setFirstMoney(firstMoney);
        record.setFirstWeight(firstWeight);
        record.setContinueMoney(continueMoney);
        if (ObjUtil.isNull(ecbdmId)) {
            msg = "正常插入数据";
            int sortId = 1;
            EcbdMoney latestObject = ecbdMoneyService.getLatestObject(record);
            if (latestObject != null) {
                sortId = latestObject.getSortId() + 1;
            }
            record.setStartType(true);
            record.setSortId(sortId);
            ecbdMoneyService.insert(record);
        } else {
            msg = "正常更新数据";
            ecbdMoneyService.update(record);
        }
        return msg;
    }

    //sort
    public void sort(List<EcbdMoneySortBo> bos) {
        for (EcbdMoneySortBo bo : bos) {
            Integer ecbdmId = bo.getEcbdmId();
            Integer sortId = bo.getSortId();
            EcbdMoney record = new EcbdMoney();
            record.setEcbdmId(ecbdmId);
            record.setSortId(sortId);
            ecbdMoneyService.update(record);
        }
    }

    public String start(EcbdMoneyBaseBo bo) {
        Integer ecbdmId = bo.getEcbdmId();
        EcbdMoney record = new EcbdMoney();
        record.setEcbdmId(ecbdmId);
        EcbdMoney ecbdPrice = ecbdMoneyService.getObject(record);
        Boolean startType = ecbdPrice.getStartType();
        String msg;
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        record = new EcbdMoney();
        record.setEcbdmId(ecbdPrice.getEcbdmId());
        record.setStartType(startType);
        ecbdMoneyService.update(record);
        return msg;
    }


    //load
    public void load(EcbdMoneyBo bo) {
        Integer ecbdId = bo.getEcbdId();
        EcbdMoney record = new EcbdMoney();
        record.setEcbdId(ecbdId);
        List<EcbdMoney> list_price = ecbdMoneyService.getList(record);
        Boolean startType = true;
        Integer sortId = 1;
        log.info("list_price + " + CommonFunction.getGson().toJson(list_price));
        if (list_price.isEmpty()) {
            record.setEcbdId(ecbdId);
            record.setStartType(startType);
            record.setFirstWeight(0);
            record.setFirstMoney(new BigDecimal("0"));
            record.setContinueMoney(new BigDecimal("0"));
            EcProvince recordProvince = new EcProvince();
            recordProvince.setStartType(true);
            List<EcProvince> list = ecProvinceService.getList(recordProvince);
            for (EcProvince province : list) {
                EcbdMoney ecbudMoney = ecbdMoneyService.getObject(record);
                if (ecbudMoney != null) {
                    sortId = ecbudMoney.getSortId() + 1;
                }
                Integer ecpId = province.getEcpId();
                record.setEcpId(ecpId);
                record.setSortId(sortId);
                record.setProvinceName(province.getProvinceName());
                ecbdMoneyService.insert(record);
            }
        }
    }


    //getListPassEcbdId
    public List<EcbdMoney> getListPassEcbdId(Integer ecbdId) {
        EcbdMoney record = new EcbdMoney();
        record.setEcbdId(ecbdId);
        return ecbdMoneyService.getList(record);
    }

    public void delete(EcbdMoneyBaseBo bo) {
        Integer ecbdmId = bo.getEcbdmId();
        ecbdMoneyService.deleteById(ecbdmId);
    }

    @Transactional(rollbackFor = Exception.class)
    public String weight(List<EcbMoneyWeightBo> bos) {
        for (EcbMoneyWeightBo bo : bos) {
            Integer ecbdmId = bo.getEcbdmId();
            Integer firstWeight = bo.getFirstWeight();
            EcbdMoney record = new EcbdMoney();
            record.setEcbdmId(ecbdmId);
            record.setFirstWeight(firstWeight);
            ecbdMoneyService.update(record);
        }
        return "修改成功";
    }
}
