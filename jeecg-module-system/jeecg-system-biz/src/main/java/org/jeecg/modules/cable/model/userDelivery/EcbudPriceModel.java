package org.jeecg.modules.cable.model.userDelivery;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import org.jeecg.modules.cable.controller.userDelivery.price.bo.EcbuPriceBaseBo;
import org.jeecg.modules.cable.controller.userDelivery.price.bo.EcbuPriceSortBo;
import org.jeecg.modules.cable.controller.userDelivery.price.bo.EcbudPriceBo;
import org.jeecg.modules.cable.controller.userDelivery.price.bo.EcbudPriceInsertBo;
import org.jeecg.modules.cable.controller.userDelivery.price.vo.EcbudPriceVo;
import org.jeecg.modules.cable.entity.pcc.EcProvince;
import org.jeecg.modules.cable.entity.userDelivery.EcbudModel;
import org.jeecg.modules.cable.entity.userDelivery.EcbudPrice;
import org.jeecg.modules.cable.service.pcc.EcProvinceService;
import org.jeecg.modules.cable.service.userDelivery.EcbudModelService;
import org.jeecg.modules.cable.service.userDelivery.EcbudPriceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EcbudPriceModel {
    @Resource
    EcbudPriceService ecbudPriceService;
    @Resource
    EcbudModelService ecbudModelService;
    @Resource
    EcProvinceService ecProvinceService;// 省

    // load 加载默认省信息
    public void load(Integer ecbudId) {
        EcbudPrice record = new EcbudPrice();
        record.setEcbudId(ecbudId);
        List<EcbudPrice> priceList = ecbudPriceService.getList(record);
        Boolean startType = true;
        Integer sortId = 1;
        if (priceList.isEmpty()) {
            record.setEcbudId(ecbudId);
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
                EcbudPrice ecbudPrice = ecbudPriceService.getLatestObject(record);
                if (ecbudPrice != null) {
                    sortId = ecbudPrice.getSortId() + 1;
                }
                Integer ecpId = province.getEcpId();
                record.setEcpId(ecpId);
                record.setSortId(sortId);
                record.setProvinceName(province.getProvinceName());
                ecbudPriceService.insert(record);
            }
        }
    }

    // getListAndCount
    public EcbudPriceVo getListAndCount(EcbudPriceBo bo) {
        Integer ecbudId = bo.getEcbudId();
        // 初始化省份信息
        load(ecbudId);
        EcbudPrice record = new EcbudPrice();
        record.setStartType(bo.getStartType());
        record.setEcbudId(ecbudId);
        List<EcbudPrice> list = ecbudPriceService.getList(record);
        long count = ecbudPriceService.getCount(record);
        return new EcbudPriceVo(list, count);
    }

    // getObject
    public EcbudPrice getObject(EcbuPriceBaseBo bo) {
        EcbudPrice record = new EcbudPrice();
        record.setEcbudpId(bo.getEcbudpId());
        return ecbudPriceService.getObject(record);
    }

    // deal
    @Transactional(rollbackFor = Exception.class)
    public String deal(EcbudPriceInsertBo bo) {

        Integer ecbudpId = bo.getEcbudpId();
        Integer ecbudId = bo.getEcbudId();
        String provinceName = bo.getProvinceName();
        BigDecimal firstPrice = ObjUtil.isNotNull(bo.getFirstPrice()) ? bo.getFirstPrice() : new BigDecimal(0);
        BigDecimal price1 = ObjUtil.isNotNull(bo.getPrice1()) ? bo.getPrice1() : new BigDecimal(0);
        BigDecimal price2 = ObjUtil.isNotNull(bo.getPrice2()) ? bo.getPrice2() : new BigDecimal(0);
        BigDecimal price3 = ObjUtil.isNotNull(bo.getPrice3()) ? bo.getPrice3() : new BigDecimal(0);
        BigDecimal price4 = ObjUtil.isNotNull(bo.getPrice4()) ? bo.getPrice4() : new BigDecimal(0);
        BigDecimal price5 = ObjUtil.isNotNull(bo.getPrice5()) ? bo.getPrice5() : new BigDecimal(0);

        EcbudPrice record = new EcbudPrice();
        record.setEcbudId(ecbudId);
        record.setProvinceName(provinceName);
        EcbudPrice ecbudPrice = ecbudPriceService.getObjectPassProvinceName(record);
        String msg = "";
        if (ecbudPrice != null) {
            throw new RuntimeException("名称已占用");
        }
        if (ObjectUtil.isNull(ecbudpId)) {// 插入
            Integer sortId = 1;
            ecbudPrice = ecbudPriceService.getLatestObject(record);
            if (ecbudPrice != null) {
                sortId = ecbudPrice.getSortId() + 1;
            }
            record.setSortId(sortId);
            record.setStartType(true);
            record.setEcpId(0);
            record.setProvinceName(provinceName);
            record.setFirstPrice(firstPrice);
            record.setPrice1(price1);
            record.setPrice2(price2);
            record.setPrice3(price3);
            record.setPrice4(price4);
            record.setPrice5(price5);
            ecbudPriceService.insert(record);
            msg = "正常插入数据";
        } else {// 更新
            record.setEcbudpId(ecbudpId);
            record.setProvinceName(provinceName);
            record.setFirstPrice(firstPrice);
            record.setPrice1(price1);
            record.setPrice2(price2);
            record.setPrice3(price3);
            record.setPrice4(price4);
            record.setPrice5(price5);
            ecbudPriceService.update(record);
            msg = "正常更新数据";
        }
        return msg;
    }

    // sort
    public void sort(List<EcbuPriceSortBo> bos) {
        for (EcbuPriceSortBo bo : bos) {
            Integer ecbudpId = bo.getEcbudpId();
            Integer sortId = bo.getSortId();
            EcbudPrice record = new EcbudPrice();
            record.setEcbudpId(ecbudpId);
            record.setSortId(sortId);
            ecbudPriceService.update(record);
        }
    }

    // delete
    @Transactional(rollbackFor = Exception.class)
    public void delete(EcbuPriceBaseBo bo) {

        Integer ecbudpId = bo.getEcbudpId();
        EcbudPrice record = new EcbudPrice();
        record.setEcbudpId(ecbudpId);
        EcbudPrice ecbudPrice = ecbudPriceService.getObject(record);
        Integer sortId = ecbudPrice.getSortId();
        record = new EcbudPrice();
        record.setSortId(sortId);
        record.setEcbudId(ecbudPrice.getEcbudId());
        List<EcbudPrice> list = ecbudPriceService.getListGreaterThanSortId(record);
        Integer ecbudp_id;
        for (EcbudPrice ecbud_price : list) {
            ecbudp_id = ecbud_price.getEcbudpId();
            sortId = ecbud_price.getSortId() - 1;
            record.setEcbudpId(ecbudp_id);
            record.setSortId(sortId);
            ecbudPriceService.update(record);
        }
        record = new EcbudPrice();
        record.setEcbudpId(ecbudpId);
        ecbudPriceService.delete(record);
    }

    // start
    public String start(EcbuPriceBaseBo bo) {
        Integer ecbudpId = bo.getEcbudpId();
        EcbudPrice record = new EcbudPrice();
        record.setEcbudpId(ecbudpId);
        EcbudPrice ecbudPrice = ecbudPriceService.getObject(record);
        Boolean startType = ecbudPrice.getStartType();
        String msg;
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        record = new EcbudPrice();
        record.setEcbudpId(ecbudPrice.getEcbudpId());
        record.setStartType(startType);
        ecbudPriceService.update(record);
        return msg;
    }

    /***===数据模型===***/
    // getPricePassEcbudIdAndAndProvinceNameAndWeight 通过省份和重量获取运费
    public Map<String, Object> getPricePassEcbudIdAndAndProvinceNameAndWeight(Integer ecbudId, String provinceName, BigDecimal weight) {
        Map<String, Object> map = new HashMap<>();
        BigDecimal price = new BigDecimal("0");
        BigDecimal unitPrice = new BigDecimal("0");
        weight = weight.divide(BigDecimal.ONE, 0, RoundingMode.UP);
        EcbudPrice record = new EcbudPrice();
        record.setEcbudId(ecbudId);
        record.setStartType(true);
        record.setProvinceName(provinceName);
        EcbudPrice object = ecbudPriceService.getObject(record);
        if (object != null) {
            EcbudModel recordEcbudModel = new EcbudModel();
            recordEcbudModel.setEcbudId(object.getEcbudId());
            EcbudModel model = ecbudModelService.getObject(recordEcbudModel);
            BigDecimal firstPrice = object.getFirstPrice();
            BigDecimal startWeight1 = new BigDecimal(model.getStartWeight1());
            BigDecimal endWeight1 = new BigDecimal(model.getEndWeight1());
            BigDecimal startWeight2 = new BigDecimal(model.getStartWeight2());
            BigDecimal endWeight2 = new BigDecimal(model.getEndWeight2());
            BigDecimal startWeight3 = new BigDecimal(model.getStartWeight3());
            BigDecimal endWeight3 = new BigDecimal(model.getEndWeight3());
            BigDecimal startWeight4 = new BigDecimal(model.getStartWeight4());
            BigDecimal endWeight4 = new BigDecimal(model.getEndWeight4());
            BigDecimal startWeight5 = new BigDecimal(model.getStartWeight5());
            BigDecimal endWeight5 = new BigDecimal(model.getEndWeight5());
            if (weight.compareTo(startWeight1) > -1 && weight.compareTo(endWeight1) < 1) {
                price = weight.multiply(object.getPrice1());
            } else if (weight.compareTo(startWeight2) > -1 && weight.compareTo(endWeight2) < 1) {
                price = weight.multiply(object.getPrice2());
            } else if (weight.compareTo(startWeight3) > -1 && weight.compareTo(endWeight3) < 1) {
                price = weight.multiply(object.getPrice3());
            } else if (weight.compareTo(startWeight4) > -1 && weight.compareTo(endWeight4) < 1) {
                price = weight.multiply(object.getPrice4());
            } else if (weight.compareTo(startWeight5) > -1 && weight.compareTo(endWeight5) < 1) {
                price = weight.multiply(object.getPrice5());
            }
            if (firstPrice.compareTo(price) > 0) {
                price = firstPrice;
            }
            unitPrice = price.divide(weight, 6, RoundingMode.HALF_UP);
        }
        map.put("price", price);
        map.put("unitPrice", unitPrice);
        return map;
    }

    /***===数据模型===***/
    // deal
    @Transactional(rollbackFor = Exception.class)
    public void deal(EcbudPrice record) {
        EcbudPrice recordEcbudPrice = new EcbudPrice();
        recordEcbudPrice.setEcbudId(record.getEcbudId());
        recordEcbudPrice.setEcpId(record.getEcpId());
        recordEcbudPrice.setProvinceName(record.getProvinceName());
        EcbudPrice ecbudMoney = ecbudPriceService.getObject(recordEcbudPrice);
        if (ecbudMoney != null) {
            ecbudPriceService.update(record);
        } else {
            ecbudPriceService.insert(record);
        }
    }

    // deletePassEcbudId
    public void deletePassEcbudId(Integer ecbudId) {
        EcbudPrice record = new EcbudPrice();
        record.setEcbudId(ecbudId);
        ecbudPriceService.delete(record);
    }
}
