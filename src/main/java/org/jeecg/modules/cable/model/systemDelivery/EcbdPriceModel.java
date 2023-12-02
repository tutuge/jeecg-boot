package org.jeecg.modules.cable.model.systemDelivery;

import cn.hutool.core.util.ObjUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.controller.systemDelivery.price.bo.EcbdPriceBaseBo;
import org.jeecg.modules.cable.controller.systemDelivery.price.bo.EcbdPriceDealBo;
import org.jeecg.modules.cable.controller.systemDelivery.price.bo.EcbdPriceListBo;
import org.jeecg.modules.cable.controller.systemDelivery.price.bo.EcbdPriceSortBo;
import org.jeecg.modules.cable.controller.systemDelivery.price.vo.EcbdPriceListVo;
import org.jeecg.modules.cable.entity.systemPcc.EcProvince;
import org.jeecg.modules.cable.entity.systemDelivery.EcbdPrice;
import org.jeecg.modules.cable.service.systemPcc.EcProvinceService;
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
        record.setEcbdId(bo.getEcbdId());
        List<EcbdPrice> list = ecbdPriceService.getList(record);
        long count = ecbdPriceService.getCount(record);
        return new EcbdPriceListVo(list, count);
    }


    @Transactional(rollbackFor = Exception.class)
    public String deal(EcbdPriceDealBo bo) {
        Integer ecbdpId = bo.getEcbdpId();
        Integer ecbdId = bo.getEcbdId();
        String provinceName = bo.getProvinceName();
        BigDecimal firstPrice = ObjUtil.isNotNull(bo.getFirstPrice()) ? bo.getFirstPrice() : BigDecimal.ZERO;
        BigDecimal price1 = ObjUtil.isNotNull(bo.getPrice1()) ? bo.getPrice1() : BigDecimal.ZERO;
        BigDecimal price2 = ObjUtil.isNotNull(bo.getPrice2()) ? bo.getPrice2() : BigDecimal.ZERO;
        BigDecimal price3 = ObjUtil.isNotNull(bo.getPrice3()) ? bo.getPrice3() : BigDecimal.ZERO;
        BigDecimal price4 = ObjUtil.isNotNull(bo.getPrice4()) ? bo.getPrice4() : BigDecimal.ZERO;
        BigDecimal price5 = ObjUtil.isNotNull(bo.getPrice5()) ? bo.getPrice5() : BigDecimal.ZERO;

        EcbdPrice price = new EcbdPrice();
        price.setEcbdpId(ecbdpId);
        price.setProvinceName(provinceName);
        EcbdPrice passProvinceName = ecbdPriceService.getObjectPassProvinceName(price);
        if (passProvinceName != null) {
            throw new RuntimeException("名称已占用");
        }
        String msg="";
        EcbdPrice record = new EcbdPrice();
        record.setEcbdpId(ecbdpId);
        record.setEcbdId(ecbdId);
        record.setFirstPrice(firstPrice);
        record.setPrice1(price1);
        record.setPrice2(price2);
        record.setPrice3(price3);
        record.setPrice4(price4);
        record.setPrice5(price5);
        if (ObjUtil.isNull(ecbdpId)) {
            msg = "正常插入数据";
            int sortId = 1;
            EcbdPrice latestObject = ecbdPriceService.getLatestObject(record);
            if (latestObject != null) {
                sortId = latestObject.getSortId() + 1;
            }
            record.setStartType(true);
            record.setSortId(sortId);
            ecbdPriceService.insert(record);
        } else {
            msg = "正常更新数据";
            ecbdPriceService.update(record);
        }
        return msg;
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
        int sortId = 1;
        if (priceList.isEmpty()) {
            record.setEcbdId(ecbdId);
            record.setStartType(startType);
            record.setFirstPrice(BigDecimal.ZERO);
            record.setPrice1(BigDecimal.ZERO);
            record.setPrice2(BigDecimal.ZERO);
            record.setPrice3(BigDecimal.ZERO);
            record.setPrice4(BigDecimal.ZERO);
            record.setPrice5(BigDecimal.ZERO);
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


    public List<EcbdPrice> getListPassEcbdId(Integer ecbdId) {
        EcbdPrice record = new EcbdPrice();
        record.setEcbdId(ecbdId);
        return ecbdPriceService.getList(record);
    }

    public void delete(EcbdPriceBaseBo bo) {
        ecbdPriceService.deleteById(bo.getEcbdpId());
    }
}
