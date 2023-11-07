package org.jeecg.modules.cable.model.userDelivery;

import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.userDelivery.delivery.bo.EcbuDeliveryBaseBo;
import org.jeecg.modules.cable.controller.userDelivery.delivery.bo.EcbuDeliveryBo;
import org.jeecg.modules.cable.controller.userDelivery.delivery.bo.EcbuDeliveryInsertBo;
import org.jeecg.modules.cable.controller.userDelivery.delivery.bo.EcbuDeliverySortBo;
import org.jeecg.modules.cable.controller.userDelivery.delivery.vo.EcbuDeliveryVo;
import org.jeecg.modules.cable.entity.hand.DeliveryObj;
import org.jeecg.modules.cable.entity.price.EcuQuoted;
import org.jeecg.modules.cable.entity.userDelivery.EcbuDelivery;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userDelivery.EcbuDeliveryService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EcbuDeliveryModel {
    @Resource
    EcbuDeliveryService ecbuDeliveryService;
    @Resource
    EcUserService ecUserService;
    @Resource
    EcbudMoneyModel ecbudMoneyModel;// 快递
    @Resource
    EcbudPriceModel ecbudPriceModel;// 快运


    public EcbuDeliveryVo getListAndCount(EcbuDeliveryBo bo) {
        // 获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        Integer ecbusId = bo.getEcbusId();
        EcbuDelivery record = new EcbuDelivery();
        record.setStartType(bo.getStartType());
        record.setEcCompanyId(sysUser.getEcCompanyId());
        record.setEcbusId(ecbusId);
        List<EcbuDelivery> list = ecbuDeliveryService.getList(record);
        long count = ecbuDeliveryService.getCount(record);
        return new EcbuDeliveryVo(list, count);
    }


    public EcbuDelivery getObject(EcbuDeliveryBaseBo bo) {
        EcbuDelivery record = new EcbuDelivery();
        Integer ecbudId = bo.getEcbudId();
        record.setEcbudId(ecbudId);
        return ecbuDeliveryService.getObject(record);
    }


    @Transactional(rollbackFor = Exception.class)
    public String deal(EcbuDeliveryInsertBo bo) {
        // 获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();


        Integer ecbudId = bo.getEcbudId();
        Integer ecbusId = bo.getEcbusId();
        Integer deliveryType = bo.getDeliveryType();
        String deliveryName = bo.getDeliveryName();
        String description = bo.getDescription();

        EcbuDelivery record = new EcbuDelivery();
        record.setEcbudId(ecbudId);
        record.setEcCompanyId(sysUser.getEcCompanyId());
        record.setEcbusId(ecbusId);
        record.setDeliveryName(deliveryName);
        EcbuDelivery ecbuDelivery = ecbuDeliveryService.getObjectPassDeliveryName(record);
        String msg;
        if (ecbuDelivery != null) {
            throw new RuntimeException("名称已占用");
        }
        if (ObjectUtil.isNull(ecbudId)) {// 插入
            Integer sortId = 1;
            ecbuDelivery = ecbuDeliveryService.getLatestObject(record);
            if (ecbuDelivery != null) {
                sortId = ecbuDelivery.getSortId() + 1;
            }
            record = new EcbuDelivery();
            record.setEcCompanyId(sysUser.getEcCompanyId());
            record.setEcbusId(ecbusId);
            record.setDeliveryName(deliveryName);
            record.setSortId(sortId);
            record.setStartType(false);
            record.setDeliveryType(deliveryType);
            record.setDescription(description);
            ecbuDeliveryService.insert(record);
            msg = "正常插入数据";
        } else {// 更新
            record = new EcbuDelivery();
            record.setEcbudId(ecbudId);
            record.setDeliveryType(deliveryType);
            record.setDeliveryName(deliveryName);
            record.setDescription(description);
            log.info("record + " + CommonFunction.getGson().toJson(record));
            ecbuDeliveryService.update(record);
            msg = "正常更新数据";
        }
        return msg;
    }


    @Transactional(rollbackFor = Exception.class)
    public void sort(List<EcbuDeliverySortBo> bos) {
        for (EcbuDeliverySortBo bo : bos) {
            Integer ecbudId = bo.getEcbudId();
            Integer sortId = bo.getSortId();

            EcbuDelivery record = new EcbuDelivery();
            record.setEcbudId(ecbudId);
            record.setSortId(sortId);
            System.out.println(CommonFunction.getGson().toJson(record));
            ecbuDeliveryService.update(record);
        }
    }


    public void delete(EcbuDeliveryBaseBo bo) {
        // 获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        Integer ecbudId = bo.getEcbudId();

        EcbuDelivery record = new EcbuDelivery();
        record.setEcbudId(ecbudId);
        EcbuDelivery ecbuDelivery = ecbuDeliveryService.getObject(record);
        Integer sortId = ecbuDelivery.getSortId();
        record = new EcbuDelivery();
        record.setSortId(sortId);
        record.setEcCompanyId(sysUser.getEcCompanyId());
        record.setEcbusId(ecbuDelivery.getEcbusId());
        List<EcbuDelivery> list = ecbuDeliveryService.getListGreaterThanSortId(record);
        Integer ecbud_id;
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

    }


    public String start(EcbuDeliveryBaseBo bo) {

        Integer ecbudId = bo.getEcbudId();
        EcbuDelivery record = new EcbuDelivery();
        record.setEcbudId(ecbudId);
        EcbuDelivery ecbuDelivery = ecbuDeliveryService.getObject(record);
        Boolean startType = ecbuDelivery.getStartType();
        String msg = "";
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        record = new EcbuDelivery();
        record.setEcbudId(ecbuDelivery.getEcbudId());
        record.setStartType(startType);
        // System.out.println(CommonFunction.getGson().toJson(record));
        ecbuDeliveryService.update(record);

        return msg;
    }

    /***===数据模型===***/
    // getDeliveryPriceList 获取运费 ecbusId 仓库ID
    public List<DeliveryObj> getDeliveryPriceList(Integer ecCompanyId, Integer ecbusId, EcuQuoted ecuQuoted, BigDecimal weight) {
        Map<String, Object> mapPrice;
        String provinceName = ecuQuoted.getProvinceName();
        List<DeliveryObj> listDeliveryPrice = new ArrayList<>();
        EcbuDelivery record = new EcbuDelivery();
        record.setStartType(true);
        record.setEcCompanyId(ecCompanyId);
        record.setEcbusId(ecbusId);
        List<EcbuDelivery> listDelivery = ecbuDeliveryService.getList(record);
        BigDecimal price;
        BigDecimal unitPrice;
        DeliveryObj obj;
        for (EcbuDelivery ecbuDelivery : listDelivery) {
            price = new BigDecimal("0");
            unitPrice = new BigDecimal("0");
            Integer ecbudId = ecbuDelivery.getEcbudId();
            String deliveryName = ecbuDelivery.getDeliveryName();
            Integer deliveryType = ecbuDelivery.getDeliveryType();
            if (weight.compareTo(new BigDecimal("0")) != 0) {
                if (deliveryType == 1) {// 快递
                    mapPrice = ecbudMoneyModel
                            .getPricePassEcbudIdAndAndProvinceNameAndWeight(ecbudId, provinceName, weight);
                } else {// 快运
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

    @Transactional(rollbackFor = Exception.class)
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

    // getListStart
    public List<EcbuDelivery> getListStart(Integer ecCompanyId) {
        EcbuDelivery record = new EcbuDelivery();
        record.setEcCompanyId(ecCompanyId);
        record.setStartType(true);
        return ecbuDeliveryService.getList(record);
    }

    // deletePassEcCompanyId
    public void deletePassEcCompanyId(Integer ecCompanyId) {
        EcbuDelivery record = new EcbuDelivery();
        record.setEcCompanyId(ecCompanyId);
        ecbuDeliveryService.delete(record);
    }
}
