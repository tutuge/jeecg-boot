package org.jeecg.modules.cable.model.systemDelivery;

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

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class EcbdMoneyModel {
    @Resource
    EcbdMoneyService ecbdMoneyService;
    @Resource
    EcProvinceService ecProvinceService;

    //getList
    public EcbdMoneyListVo getList(EcbdMoneyListBo bo) {

        EcbdMoney record = new EcbdMoney();
        record.setStartType(bo.getStartType());
        List<EcbdMoney> list = ecbdMoneyService.getList(record);
        long count = ecbdMoneyService.getCount(record);

        return new EcbdMoneyListVo(list, count);
    }

    //deal
    public void deal(EcbdMoneyDealBo bo) {
        Integer ecbdmId = bo.getEcbdmId();
        Integer firstWeight = bo.getFirstWeight();
        BigDecimal firstMoney = bo.getFirstMoney();
        BigDecimal continueMoney = bo.getContinueMoney();

        EcbdMoney record = new EcbdMoney();
        record.setEcbdmId(ecbdmId);
        record.setFirstMoney(firstMoney);
        record.setFirstWeight(firstWeight);
        record.setContinueMoney(continueMoney);
        ecbdMoneyService.update(record);
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

    //start 启用、禁用
    public String start(EcbdMoneyBaseBo bo) {

        Integer ecbdmId = bo.getEcbdmId();
        EcbdMoney record = new EcbdMoney();
        record.setEcbdmId(ecbdmId);
        EcbdMoney ecbdPrice = ecbdMoneyService.getObject(record);
        boolean startType = ecbdPrice.getStartType();
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

    /***===数据模型===***/
    //load
    public void load(EcbdMoneyBo bo) {
        Integer ecbdId = bo.getEcbdId();
        EcbdMoney record = new EcbdMoney();
        record.setEcbdId(ecbdId);
        List<EcbdMoney> list_price = ecbdMoneyService.getList(record);
        boolean startType = true;
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

    /***===数据模型===***/
    //getListPassEcbdId
    public List<EcbdMoney> getListPassEcbdId(Integer ecbdId) {
        EcbdMoney record = new EcbdMoney();
        record.setEcbdId(ecbdId);
        return ecbdMoneyService.getList(record);
    }
}
