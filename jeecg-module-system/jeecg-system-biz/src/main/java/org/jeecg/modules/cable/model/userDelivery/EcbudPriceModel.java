package org.jeecg.modules.cable.model.userDelivery;

import org.jeecg.modules.cable.entity.pcc.EcProvince;
import org.jeecg.modules.cable.entity.userDelivery.EcbudModel;
import org.jeecg.modules.cable.entity.userDelivery.EcbudPrice;
import org.jeecg.modules.cable.service.pcc.EcProvinceService;
import org.jeecg.modules.cable.service.userDelivery.EcbudModelService;
import org.jeecg.modules.cable.service.userDelivery.EcbudPriceService;
import org.jeecg.modules.cable.tools.CommonFunction;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

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
    EcProvinceService ecProvinceService;//省

    //load 加载默认省信息
    public void load(HttpServletRequest request) {
        int ecbudId = Integer.parseInt(request.getParameter("ecbudId"));
        EcbudPrice record = new EcbudPrice();
        record.setEcbudId(ecbudId);
        List<EcbudPrice> list_price = ecbudPriceService.getList(record);
        boolean startType = true;
        int sortId = 1;
        if (list_price.isEmpty()) {
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
                int ecpId = province.getEcpId();
                record.setEcpId(ecpId);
                record.setSortId(sortId);
                record.setProvinceName(province.getProvinceName());
                ecbudPriceService.insert(record);
            }
        }
    }

    //getListAndCount
    public Map<String, Object> getListAndCount(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        int ecbudId = Integer.parseInt(request.getParameter("ecbudId"));
        EcbudPrice record = new EcbudPrice();
        if (request.getParameter("startType") != null) {
            boolean startType = true;
            if (!"0".equals(request.getParameter("startType"))) {
                if ("2".equals(request.getParameter("startType"))) {
                    startType = false;
                }
                record.setStartType(startType);
            }
        }
        record.setEcbudId(ecbudId);
        List<EcbudPrice> list = ecbudPriceService.getList(record);
        long count = ecbudPriceService.getCount(record);
        map.put("list", list);
        map.put("count", count);
        status = 3;//正常获取数据
        code = "200";
        msg = "正常加载数据";
        CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    //getObject
    public Map<String, Object> getObject(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        EcbudPrice record = new EcbudPrice();
        if (request.getParameter("ecbudpId") != null) {
            int ecbudpId = Integer.parseInt(request.getParameter("ecbudpId"));
            record.setEcbudpId(ecbudpId);
        }
        map.put("object", ecbudPriceService.getObject(record));
        status = 3;//正常获取数据
        code = "200";
        msg = "正常获取数据";
        CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    //deal
    public Map<String, Object> deal(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        int ecbudpId = Integer.parseInt(request.getParameter("ecbudpId"));
        int ecbudId = Integer.parseInt(request.getParameter("ecbudId"));
        String provinceName = request.getParameter("provinceName");
        BigDecimal firstPrice = new BigDecimal(0);
        BigDecimal price1 = new BigDecimal(0);
        BigDecimal price2 = new BigDecimal(0);
        BigDecimal price3 = new BigDecimal(0);
        BigDecimal price4 = new BigDecimal(0);
        BigDecimal price5 = new BigDecimal(0);
        if (ecbudpId != 0) {
            firstPrice = new BigDecimal(request.getParameter("firstPrice"));
            price1 = new BigDecimal(request.getParameter("price1"));
            price2 = new BigDecimal(request.getParameter("price2"));
            price3 = new BigDecimal(request.getParameter("price3"));
            price4 = new BigDecimal(request.getParameter("price4"));
            price5 = new BigDecimal(request.getParameter("price5"));
        }
        EcbudPrice record = new EcbudPrice();
        record.setEcbudpId(ecbudpId);
        record.setEcbudId(ecbudId);
        record.setProvinceName(provinceName);
        EcbudPrice ecbudPrice = ecbudPriceService.getObjectPassProvinceName(record);
        if (ecbudPrice != null) {
            status = 3;//名称已占用
            code = "103";
            msg = "名称已占用";
        } else {
            if (ecbudpId == 0) {//插入
                int sortId = 1;
                ecbudPrice = ecbudPriceService.getLatestObject(record);
                if (ecbudPrice != null) {
                    sortId = ecbudPrice.getSortId() + 1;
                }
                record = new EcbudPrice();
                record.setEcbudId(ecbudId);
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
                status = 4;//正常插入数据
                code = "200";
                msg = "正常插入数据";
            } else {//更新
                record = new EcbudPrice();
                record.setEcbudpId(ecbudpId);
                record.setProvinceName(provinceName);
                record.setFirstPrice(firstPrice);
                record.setPrice1(price1);
                record.setPrice2(price2);
                record.setPrice3(price3);
                record.setPrice4(price4);
                record.setPrice5(price5);
                ecbudPriceService.update(record);
                status = 5;//正常更新数据
                code = "201";
                msg = "正常更新数据";
            }
        }
        CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    //sort
    public Map<String, Object> sort(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        int ecbudpId = Integer.parseInt(request.getParameter("ecbudpId"));
        int sortId = Integer.parseInt(request.getParameter("sortId"));
        EcbudPrice record = new EcbudPrice();
        record.setEcbudpId(ecbudpId);
        record.setSortId(sortId);
        ecbudPriceService.update(record);
        status = 3;//数据操作成功
        code = "200";
        msg = "数据操作成功";
        CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    //delete
    public Map<String, Object> delete(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        int ecbudpId = Integer.parseInt(request.getParameter("ecbudpId"));
        EcbudPrice record = new EcbudPrice();
        record.setEcbudpId(ecbudpId);
        EcbudPrice ecbudPrice = ecbudPriceService.getObject(record);
        int sortId = ecbudPrice.getSortId();
        record = new EcbudPrice();
        record.setSortId(sortId);
        record.setEcbudId(ecbudPrice.getEcbudId());
        List<EcbudPrice> list = ecbudPriceService.getListGreaterThanSortId(record);
        int ecbudp_id;
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
        status = 3;//数据操作成功
        code = "200";
        msg = "数据操作成功";
        CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    //start
    public Map<String, Object> start(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        int ecbudpId = Integer.parseInt(request.getParameter("ecbudpId"));
        EcbudPrice record = new EcbudPrice();
        record.setEcbudpId(ecbudpId);
        EcbudPrice ecbudPrice = ecbudPriceService.getObject(record);
        boolean startType = ecbudPrice.getStartType();
        if (!startType) {
            startType = true;
            status = 3;
            code = "200";
            msg = "数据启用成功";
        } else {
            startType = false;
            status = 4;
            code = "201";
            msg = "数据禁用成功";
        }
        record = new EcbudPrice();
        record.setEcbudpId(ecbudPrice.getEcbudpId());
        record.setStartType(startType);
        ecbudPriceService.update(record);
        CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    /***===数据模型===***/
    //getPricePassEcbudIdAndAndProvinceNameAndWeight 通过省份和重量获取运费
    public Map<String, Object> getPricePassEcbudIdAndAndProvinceNameAndWeight(int ecbudId, String provinceName, BigDecimal weight) {
        Map<String, Object> map = new HashMap<>();
        BigDecimal price = new BigDecimal("0");
        BigDecimal unitPrice = new BigDecimal("0");
        weight = weight.divide(new BigDecimal("1"), 0, RoundingMode.UP);
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
    //deal
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

    //deletePassEcbudId
    public void deletePassEcbudId(int ecbudId) {
        EcbudPrice record = new EcbudPrice();
        record.setEcbudId(ecbudId);
        ecbudPriceService.delete(record);
    }
}
