package org.jeecg.modules.cable.model.userDelivery;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.userDelivery.price.bo.EcbuPriceBaseBo;
import org.jeecg.modules.cable.controller.userDelivery.price.bo.EcbuPriceSortBo;
import org.jeecg.modules.cable.controller.userDelivery.price.bo.EcbudPriceBo;
import org.jeecg.modules.cable.controller.userDelivery.price.bo.EcbudPriceInsertBo;
import org.jeecg.modules.cable.controller.userDelivery.price.vo.EcbudPriceVo;
import org.jeecg.modules.cable.domain.DeliveryPriceBo;
import org.jeecg.modules.cable.entity.systemPcc.EcProvince;
import org.jeecg.modules.cable.entity.userDelivery.EcbudPrice;
import org.jeecg.modules.cable.entity.userDelivery.EcbudWeight;
import org.jeecg.modules.cable.entity.userPcc.EcuProvince;
import org.jeecg.modules.cable.service.systemPcc.EcProvinceService;
import org.jeecg.modules.cable.service.userDelivery.EcbudPriceService;
import org.jeecg.modules.cable.service.userDelivery.EcbudWeightService;
import org.jeecg.modules.cable.service.userPcc.EcuProvinceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class EcbudPriceModel {
    @Resource
    EcbudPriceService ecbudPriceService;
    @Resource
    EcbudWeightService ecbudWeightService;
    @Resource
    EcProvinceService ecProvinceService;// 省
    @Resource
    private EcuProvinceService ecuProvinceService;

    // load 加载默认省信息
    public void load(Integer ecbudId) {
        EcbudPrice record = new EcbudPrice();
        record.setEcbudId(ecbudId);
        List<EcbudPrice> priceList = ecbudPriceService.getList(record);
        Boolean startType = true;
        int sortId = 1;
        if (priceList.isEmpty()) {
            EcProvince recordProvince = new EcProvince();
            recordProvince.setStartType(true);
            List<EcProvince> list = ecProvinceService.getList(recordProvince);
            EcbudPrice ecbudPrice = ecbudPriceService.getLatestObject(record);
            if (ecbudPrice != null) {
                sortId = ecbudPrice.getSortId() + 1;
            }
            for (EcProvince province : list) {
                record = new EcbudPrice();
                record.setEcbudId(ecbudId);
                record.setStartType(startType);
                record.setFirstPrice(BigDecimal.ZERO);
                record.setPrice1(BigDecimal.ZERO);
                record.setPrice2(BigDecimal.ZERO);
                record.setPrice3(BigDecimal.ZERO);
                record.setPrice4(BigDecimal.ZERO);
                record.setPrice5(BigDecimal.ZERO);
                Integer ecpId = province.getEcpId();
                record.setEcpId(ecpId);
                record.setSortId(sortId);
                record.setProvinceName(province.getProvinceName());
                ecbudPriceService.insert(record);
                sortId++;
            }
        }
        EcbudWeight recordEcbudWeight = new EcbudWeight();
        recordEcbudWeight.setEcbudId(ecbudId);
        EcbudWeight model = ecbudWeightService.getObject(recordEcbudWeight);
        if (ObjUtil.isNull(model)) {
            EcbudWeight ecbudWeight = new EcbudWeight();
            ecbudWeight.setEcbudId(ecbudId);
            ecbudWeightService.insert(ecbudWeight);
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public EcbudPriceVo getListAndCount(EcbudPriceBo bo) {
        Integer ecbudId = bo.getEcbudId();

        EcbudPrice record = new EcbudPrice();
        record.setStartType(bo.getStartType());
        record.setEcbudId(ecbudId);
        List<EcbudPrice> list = ecbudPriceService.getList(record);
        long count = ecbudPriceService.getCount(record);
        if (count == 0L) {
            // 初始化省份运价信息
            load(ecbudId);
            list = ecbudPriceService.getList(record);
            count = ecbudPriceService.getCount(record);
        }
        return new EcbudPriceVo(list, count);
    }


    public EcbudPrice getObject(EcbuPriceBaseBo bo) {
        EcbudPrice record = new EcbudPrice();
        record.setEcbudpId(bo.getEcbudpId());
        return ecbudPriceService.getObject(record);
    }


    @Transactional(rollbackFor = Exception.class)
    public String deal(EcbudPriceInsertBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecbudpId = bo.getEcbudpId();
        Integer ecbudId = bo.getEcbudId();
        String provinceName = bo.getProvinceName();
        BigDecimal firstPrice = ObjUtil.isNotNull(bo.getFirstPrice()) ? bo.getFirstPrice() : BigDecimal.ZERO;
        BigDecimal price1 = ObjUtil.isNotNull(bo.getPrice1()) ? bo.getPrice1() : BigDecimal.ZERO;
        BigDecimal price2 = ObjUtil.isNotNull(bo.getPrice2()) ? bo.getPrice2() : BigDecimal.ZERO;
        BigDecimal price3 = ObjUtil.isNotNull(bo.getPrice3()) ? bo.getPrice3() : BigDecimal.ZERO;
        BigDecimal price4 = ObjUtil.isNotNull(bo.getPrice4()) ? bo.getPrice4() : BigDecimal.ZERO;
        BigDecimal price5 = ObjUtil.isNotNull(bo.getPrice5()) ? bo.getPrice5() : BigDecimal.ZERO;

        EcbudPrice record = new EcbudPrice();
        record.setEcbudId(ecbudId);
        record.setProvinceName(provinceName);
        EcbudPrice ecbudPrice = ecbudPriceService.getObjectPassProvinceName(record);
        String msg = "";
        if (ecbudPrice != null) {
            throw new RuntimeException("名称已占用");
        }
        if (ObjectUtil.isNull(ecbudpId)) {// 插入
            int sortId = 1;
            ecbudPrice = ecbudPriceService.getLatestObject(record);
            if (ecbudPrice != null) {
                sortId = ecbudPrice.getSortId() + 1;
            }
            EcuProvince province = ecuProvinceService.insertProvinceName(provinceName, sysUser.getEcCompanyId());
            record.setSortId(sortId);
            record.setStartType(true);
            record.setEcpId(province.getEcpId());
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
            EcbudPrice price = new EcbudPrice();
            price.setEcbudId(ecbudpId);
            EcbudPrice object = ecbudPriceService.getObject(price);
            record.setEcbudpId(ecbudpId);
            record.setProvinceName(provinceName);
            record.setFirstPrice(firstPrice);
            record.setPrice1(price1);
            record.setPrice2(price2);
            record.setPrice3(price3);
            record.setPrice4(price4);
            record.setPrice5(price5);
            ecuProvinceService.updateProvinceName(provinceName, object.getEcpId(), sysUser.getEcCompanyId());
            ecbudPriceService.update(record);
            msg = "正常更新数据";
        }
        return msg;
    }


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


    @Transactional(rollbackFor = Exception.class)
    public void delete(EcbuPriceBaseBo bo) {
        Integer ecbudpId = bo.getEcbudpId();
        EcbudPrice record = new EcbudPrice();
        record.setEcbudpId(ecbudpId);
        EcbudPrice ecbudPrice = ecbudPriceService.getObject(record);
        if (ObjUtil.isNull(ecbudPrice)) {
            throw new RuntimeException("此行记录不存在，无法操作");
        }
        Integer sortId = ecbudPrice.getSortId();
        Integer ecpId = ecbudPrice.getEcpId();
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
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        ecuProvinceService.deleteByEcpId(ecpId, sysUser.getEcCompanyId());
    }


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


    // 通过省份和重量获取运费
    public DeliveryPriceBo getPricePassEcbudIdAndProvinceIdAndWeight(Integer ecbudId, Integer provinceId, BigDecimal weight) {
        BigDecimal price = BigDecimal.ZERO;
        BigDecimal unitPrice = BigDecimal.ZERO;
        weight = weight.divide(BigDecimal.ONE, 0, RoundingMode.UP);
        EcbudPrice object = ecbudPriceService.getPricePassEcbudIdAndProvinceIdAndWeight(ecbudId, true, provinceId);
        if (object != null) {
            EcbudWeight recordEcbudWeight = new EcbudWeight();
            recordEcbudWeight.setEcbudId(object.getEcbudId());
            EcbudWeight model = ecbudWeightService.getObject(recordEcbudWeight);
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
            unitPrice = price.divide(weight, 16, RoundingMode.HALF_UP);
        }
        return new DeliveryPriceBo(price, unitPrice);
    }


    @Transactional(rollbackFor = Exception.class)
    public void deal(EcbudPrice record) {
        EcbudPrice recordEcbudPrice = new EcbudPrice();
        recordEcbudPrice.setEcbudId(record.getEcbudId());
        recordEcbudPrice.setEcpId(record.getEcpId());
        recordEcbudPrice.setProvinceName(record.getProvinceName());
        EcbudPrice ecbudPrice = ecbudPriceService.getObject(recordEcbudPrice);
        if (ecbudPrice != null) {
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
