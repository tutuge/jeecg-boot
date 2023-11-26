package org.jeecg.modules.cable.model.systemDelivery;

import cn.hutool.core.util.ObjUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.controller.systemDelivery.price.bo.EcbdPriceBaseBo;
import org.jeecg.modules.cable.controller.systemDelivery.price.bo.EcbdPriceDealBo;
import org.jeecg.modules.cable.controller.systemDelivery.price.bo.EcbdPriceListBo;
import org.jeecg.modules.cable.controller.systemDelivery.price.bo.EcbdPriceSortBo;
import org.jeecg.modules.cable.controller.systemDelivery.price.vo.EcbdPriceListVo;
import org.jeecg.modules.cable.entity.pcc.EcProvince;
import org.jeecg.modules.cable.entity.systemDelivery.EcbdPrice;
import org.jeecg.modules.cable.service.pcc.EcProvinceService;
import org.jeecg.modules.cable.service.systemDelivery.EcbdPriceService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class EcbdPriceModel {
    @Resource
    EcbdPriceService ecbdPriceService;
    @Resource
    EcProvinceService ecProvinceService;


    public EcbdPriceListVo getList(EcbdPriceListBo bo) {
        EcbdPrice record = new EcbdPrice();
        record.setStartType(bo.getStartType());
        List<EcbdPrice> list = ecbdPriceService.getList(record);
        long count = ecbdPriceService.getCount(record);
        return new EcbdPriceListVo(list, count);
    }


    @Transactional(rollbackFor = Exception.class)
    public void deal(EcbdPriceDealBo bo) {
        Integer ecbdpId = bo.getEcbdpId();
        BigDecimal firstPrice = ObjUtil.isNotNull(bo.getFirstPrice()) ? bo.getFirstPrice() : new BigDecimal(0);
        BigDecimal price1 = ObjUtil.isNotNull(bo.getPrice1()) ? bo.getPrice1() : new BigDecimal(0);
        BigDecimal price2 = ObjUtil.isNotNull(bo.getPrice2()) ? bo.getPrice2() : new BigDecimal(0);
        BigDecimal price3 = ObjUtil.isNotNull(bo.getPrice3()) ? bo.getPrice3() : new BigDecimal(0);
        BigDecimal price4 = ObjUtil.isNotNull(bo.getPrice4()) ? bo.getPrice4() : new BigDecimal(0);
        BigDecimal price5 = ObjUtil.isNotNull(bo.getPrice5()) ? bo.getPrice5() : new BigDecimal(0);

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

    
    // load
    public void load(Integer ecbdId) {
        EcbdPrice record = new EcbdPrice();
        record.setEcbdId(ecbdId);
        List<EcbdPrice> priceList = ecbdPriceService.getList(record);
        Boolean startType = true;
        Integer sortId = 1;
        if (priceList.isEmpty()) {
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

    
    // getListPassEcbdId
    public List<EcbdPrice> getListPassEcbdId(Integer ecbdId) {
        EcbdPrice record = new EcbdPrice();
        record.setEcbdId(ecbdId);
        return ecbdPriceService.getList(record);
    }
}
