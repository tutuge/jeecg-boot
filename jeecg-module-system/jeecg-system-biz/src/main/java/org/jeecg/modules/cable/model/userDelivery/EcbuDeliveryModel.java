package org.jeecg.modules.cable.model.userDelivery;

import org.jeecg.modules.cable.entity.hand.DeliveryObj;
import org.jeecg.modules.cable.entity.price.EcuQuoted;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.modules.cable.entity.userDelivery.EcbuDelivery;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userDelivery.EcbuDeliveryService;
import org.jeecg.modules.cable.tools.CommonFunction;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@Slf4j
public class EcbuDeliveryModel {
    @Resource
    EcbuDeliveryService ecbuDeliveryService;
    @Resource
    EcUserService ecUserService;
    @Resource
    EcbudMoneyModel ecbudMoneyModel;//快递
    @Resource
    EcbudPriceModel ecbudPriceModel;//快运

    //getListAndCount
    public Map<String, Object> getListAndCount(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        int ecbusId = Integer.parseInt(request.getParameter("ecbusId"));
        EcbuDelivery record = new EcbuDelivery();
        if (request.getParameter("startType") != null) {
            boolean startType = true;
            if (!"0".equals(request.getParameter("startType"))) {
                if ("2".equals(request.getParameter("startType"))) {
                    startType = false;
                }
                record.setStartType(startType);
            }
        }
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setEcbusId(ecbusId);
        List<EcbuDelivery> list = ecbuDeliveryService.getList(record);
        long count = ecbuDeliveryService.getCount(record);
        map.put("list", list);
        map.put("count", count);
        status = 3;//正常获取数据
        code = "200";
        msg = "正常获取数据";
        CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    //getObject
    public Map<String, Object> getObject(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        EcbuDelivery record = new EcbuDelivery();
        if (request.getParameter("ecbudId") != null) {
            int ecbudId = Integer.parseInt(request.getParameter("ecbudId"));
            record.setEcbudId(ecbudId);
        }
        map.put("object", ecbuDeliveryService.getObject(record));
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
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        int ecbudId = Integer.parseInt(request.getParameter("ecbudId"));
        int ecbusId = Integer.parseInt(request.getParameter("ecbusId"));
        int deliveryType = Integer.parseInt(request.getParameter("deliveryType"));
        String deliveryName = request.getParameter("deliveryName");
        String description = request.getParameter("description");
        EcbuDelivery record = new EcbuDelivery();
        record.setEcbudId(ecbudId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setEcbusId(ecbusId);
        record.setDeliveryName(deliveryName);
        EcbuDelivery ecbuDelivery = ecbuDeliveryService.getObjectPassDeliveryName(record);
        if (ecbuDelivery != null) {
            status = 3;//名称已占用
            code = "103";
            msg = "名称已占用";
        } else {
            if (ecbudId == 0) {//插入
                int sortId = 1;
                ecbuDelivery = ecbuDeliveryService.getLatestObject(record);
                if (ecbuDelivery != null) {
                    sortId = ecbuDelivery.getSortId() + 1;
                }
                record = new EcbuDelivery();
                record.setEcCompanyId(ecUser.getEcCompanyId());
                record.setEcbusId(ecbusId);
                record.setDeliveryName(deliveryName);
                record.setSortId(sortId);
                record.setStartType(false);
                record.setDeliveryType(deliveryType);
                record.setDescription(description);
                ecbuDeliveryService.insert(record);
                status = 4;//正常插入数据
                code = "200";
                msg = "正常插入数据";
            } else {//更新
                record = new EcbuDelivery();
                record.setEcbudId(ecbudId);
                record.setDeliveryType(deliveryType);
                record.setDeliveryName(deliveryName);
                record.setDescription(description);
                log.info("record + " + CommonFunction.getGson().toJson(record));
                ecbuDeliveryService.update(record);
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
        int ecbudId = Integer.parseInt(request.getParameter("ecbudId"));
        int sortId = Integer.parseInt(request.getParameter("sortId"));
        EcbuDelivery record = new EcbuDelivery();
        record.setEcbudId(ecbudId);
        record.setSortId(sortId);
        System.out.println(CommonFunction.getGson().toJson(record));
        ecbuDeliveryService.update(record);
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
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        int ecbudId = Integer.parseInt(request.getParameter("ecbudId"));
        EcbuDelivery record = new EcbuDelivery();
        record.setEcbudId(ecbudId);
        EcbuDelivery ecbuDelivery = ecbuDeliveryService.getObject(record);
        int sortId = ecbuDelivery.getSortId();
        record = new EcbuDelivery();
        record.setSortId(sortId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setEcbusId(ecbuDelivery.getEcbusId());
        List<EcbuDelivery> list = ecbuDeliveryService.getListGreaterThanSortId(record);
        int ecbud_id;
        for (EcbuDelivery ecbu_delivery : list) {
            ecbud_id = ecbu_delivery.getEcbudId();
            sortId = ecbu_delivery.getSortId() - 1;
            record.setEcbudId(ecbud_id);
            record.setSortId(sortId);
            ecbuDeliveryService.update(record);
        }
        record = new EcbuDelivery();
        record.setEcbudId(ecbudId);
        ecbuDeliveryService.delete(record);
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
        int ecbudId = Integer.parseInt(request.getParameter("ecbudId"));
        EcbuDelivery record = new EcbuDelivery();
        record.setEcbudId(ecbudId);
        EcbuDelivery ecbuDelivery = ecbuDeliveryService.getObject(record);
        boolean startType = ecbuDelivery.getStartType();
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
        record = new EcbuDelivery();
        record.setEcbudId(ecbuDelivery.getEcbudId());
        record.setStartType(startType);
        //System.out.println(CommonFunction.getGson().toJson(record));
        ecbuDeliveryService.update(record);
        CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    /***===数据模型===***/
    //getDeliveryPriceList 获取运费 ecbusId 仓库ID
    public List<DeliveryObj> getDeliveryPriceList(int ecuId, int ecbusId, EcuQuoted ecuQuoted, BigDecimal weight) {
        Map<String, Object> mapPrice;
        String provinceName = ecuQuoted.getProvinceName();
        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        List<DeliveryObj> listDeliveryPrice = new ArrayList<>();
        EcbuDelivery record = new EcbuDelivery();
        record.setStartType(true);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setEcbusId(ecbusId);
        List<EcbuDelivery> listDelivery = ecbuDeliveryService.getList(record);
        BigDecimal price;
        BigDecimal unitPrice;
        DeliveryObj obj;
        for (EcbuDelivery ecbuDelivery : listDelivery) {
            price = new BigDecimal("0");
            unitPrice = new BigDecimal("0");
            int ecbudId = ecbuDelivery.getEcbudId();
            String deliveryName = ecbuDelivery.getDeliveryName();
            int deliveryType = ecbuDelivery.getDeliveryType();
            if (weight.compareTo(new BigDecimal("0")) != 0) {
                if (deliveryType == 1) {//快递
                    mapPrice = ecbudMoneyModel
                            .getPricePassEcbudIdAndAndProvinceNameAndWeight(ecbudId, provinceName, weight);
                } else {//快运
                    mapPrice = ecbudPriceModel
                            .getPricePassEcbudIdAndAndProvinceNameAndWeight(ecbudId, provinceName, weight);
                }
                price = new BigDecimal(mapPrice.get("price").toString());
                unitPrice = new BigDecimal(mapPrice.get("unitPrice").toString());
            }
            obj = new DeliveryObj();
            obj.setEcbudId(ecbudId);
            obj.setDeliveryName(deliveryName);
            obj.setDescription(ecbuDelivery.getDescription());
            obj.setUnitPrice(unitPrice);
            obj.setPrice(price);
            listDeliveryPrice.add(obj);
        }
        listDeliveryPrice.sort(Comparator.comparing(DeliveryObj::getPrice));
        return listDeliveryPrice;
    }

    /***===数据模型===***/
    //deal
    public void deal(EcbuDelivery record) {
        EcbuDelivery recordEcbuDelivery = new EcbuDelivery();
        recordEcbuDelivery.setEcCompanyId(record.getEcCompanyId());
        recordEcbuDelivery.setEcbusId(record.getEcbusId());
        recordEcbuDelivery.setDeliveryType(record.getDeliveryType());
        recordEcbuDelivery.setDeliveryName(record.getDeliveryName());
        EcbuDelivery ecbuDelivery = ecbuDeliveryService.getObject(recordEcbuDelivery);
        if (ecbuDelivery != null) {
            ecbuDeliveryService.update(record);
        } else {
            ecbuDeliveryService.insert(record);
        }
    }

    //getListStart
    public List<EcbuDelivery> getListStart(int ecCompanyId) {
        EcbuDelivery record = new EcbuDelivery();
        record.setEcCompanyId(ecCompanyId);
        record.setStartType(true);
        return ecbuDeliveryService.getList(record);
    }

    //deletePassEcCompanyId
    public void deletePassEcCompanyId(int ecCompanyId) {
        EcbuDelivery record = new EcbuDelivery();
        record.setEcCompanyId(ecCompanyId);
        ecbuDeliveryService.delete(record);
    }
}
