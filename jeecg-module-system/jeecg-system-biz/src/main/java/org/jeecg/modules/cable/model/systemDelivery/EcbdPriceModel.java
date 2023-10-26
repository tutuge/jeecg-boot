package org.jeecg.modules.cable.model.systemDelivery;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.controller.systemDelivery.price.bo.*;
import org.jeecg.modules.cable.controller.systemDelivery.price.vo.EcbdPriceListVo;
import org.jeecg.modules.cable.entity.pcc.EcProvince;
import org.jeecg.modules.cable.entity.systemDelivery.EcbdPrice;
import org.jeecg.modules.cable.service.pcc.EcProvinceService;
import org.jeecg.modules.cable.service.systemDelivery.EcbdPriceService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EcbdPriceModel {
    @Resource
    EcbdPriceService ecbdPriceService;
    @Resource
    EcProvinceService ecProvinceService;

    //getList
    public EcbdPriceListVo getList(EcbdPriceListBo bo) {
        EcbdPrice record = new EcbdPrice();
        record.setStartType(bo.getStartType());
        List<EcbdPrice> list = ecbdPriceService.getList(record);
        long count = ecbdPriceService.getCount(record);
        return new EcbdPriceListVo(list, count);
    }

    //deal
    public void deal(EcbdPriceDealBo bo) {
        Integer ecbdpId = bo.getEcbdpId();
        BigDecimal firstPrice = new BigDecimal(0);
        BigDecimal price1 = new BigDecimal(0);
        BigDecimal price2 = new BigDecimal(0);
        BigDecimal price3 = new BigDecimal(0);
        BigDecimal price4 = new BigDecimal(0);
        BigDecimal price5 = new BigDecimal(0);
        if (ecbdpId != 0) {
            firstPrice = bo.getFirstPrice();
            price1 = bo.getPrice1();
            price2 = bo.getPrice2();
            price3 = bo.getPrice3();
            price4 = bo.getPrice4();
            price5 = bo.getPrice5();
        }
        EcbdPrice record = new EcbdPrice();
        record.setEcbdpId(ecbdpId);
        record = new EcbdPrice();
        record.setEcbdpId(ecbdpId);
        record.setFirstPrice(firstPrice);
        record.setPrice1(price1);
        record.setPrice2(price2);
        record.setPrice3(price3);
        record.setPrice4(price4);
        record.setPrice5(price5);
        ecbdPriceService.update(record);
    }

    //sort
    public void sort(List<EcbdPriceSortBo> bos) {
        for (EcbdPriceSortBo bo : bos) {
            Integer ecbdpId = bo.getEcbdpId();
            Integer sortId = bo.getSortId();
            EcbdPrice record = new EcbdPrice();
            record.setEcbdpId(ecbdpId);
            record.setSortId(sortId);
            ecbdPriceService.update(record);
        }
    }

    //start 启用、禁用
    public String start(EcbdPriceBaseBo bo) {

        Integer ecbdpId = bo.getEcbdpId();
        EcbdPrice record = new EcbdPrice();
        record.setEcbdpId(ecbdpId);
        EcbdPrice ecbdPrice = ecbdPriceService.getObject(record);
        Boolean startType = ecbdPrice.getStartType();
        String msg;
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        record = new EcbdPrice();
        record.setEcbdpId(ecbdPrice.getEcbdpId());
        record.setStartType(startType);
        ecbdPriceService.update(record);
        return msg;
    }

    /***===数据模型===***/
    //load
    public void load(EcbdPriceLoadBo bo) {

        Integer ecbdId = bo.getEcbdId();
        EcbdPrice record = new EcbdPrice();
        record.setEcbdId(ecbdId);
        List<EcbdPrice> list_price = ecbdPriceService.getList(record);
        Boolean startType = true;
        Integer sortId = 1;
        if (list_price.isEmpty()) {
            record.setEcbdId(ecbdId);
            record.setStartType(startType);
            record.setFirstPrice(new BigDecimal("0"));
            record.setPrice1(new BigDecimal("0"));
            record.setPrice2(new BigDecimal("0"));
            record.setPrice3(new BigDecimal("0"));
            record.setPrice4(new BigDecimal("0"));
            record.setPrice5(new BigDecimal("0"));
            EcProvince recordProvince = new EcProvince();
            recordProvince.setStartType(true);
            List<EcProvince> list = ecProvinceService.getList(recordProvince);
            for (EcProvince province : list) {
                EcbdPrice ecbdPrice = ecbdPriceService.getObject(record);
                if (ecbdPrice != null) {
                    sortId = ecbdPrice.getSortId() + 1;
                }
                Integer ecpId = province.getEcpId();
                record.setEcpId(ecpId);
                record.setSortId(sortId);
                record.setProvinceName(province.getProvinceName());
                log.info("record + " + CommonFunction.getGson().toJson(record));
                ecbdPriceService.insert(record);
            }
        }

    }

    /***===数据模型===***/
    //getListPassEcbdId
    public List<EcbdPrice> getListPassEcbdId(Integer ecbdId) {
        EcbdPrice record = new EcbdPrice();
        record.setEcbdId(ecbdId);
        return ecbdPriceService.getList(record);
    }
}
