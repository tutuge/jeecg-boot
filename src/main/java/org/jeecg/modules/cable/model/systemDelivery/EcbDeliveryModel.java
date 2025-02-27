package org.jeecg.modules.cable.model.systemDelivery;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.controller.systemDelivery.delivery.bo.EcbDeliveryBaseBo;
import org.jeecg.modules.cable.controller.systemDelivery.delivery.bo.EcbDeliveryDealBo;
import org.jeecg.modules.cable.controller.systemDelivery.delivery.bo.EcbDeliveryListBo;
import org.jeecg.modules.cable.controller.systemDelivery.delivery.bo.EcbDeliverySortBo;
import org.jeecg.modules.cable.controller.systemDelivery.delivery.vo.EcbDeliveryListVo;
import org.jeecg.modules.cable.entity.systemDelivery.EcbDelivery;
import org.jeecg.modules.cable.entity.systemDelivery.EcbdMoney;
import org.jeecg.modules.cable.entity.systemDelivery.EcbdPrice;
import org.jeecg.modules.cable.service.systemDelivery.EcbDeliveryService;
import org.jeecg.modules.cable.service.systemDelivery.EcbdMoneyService;
import org.jeecg.modules.cable.service.systemDelivery.EcbdPriceService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class EcbDeliveryModel {
    @Resource
    private EcbDeliveryService ecbDeliveryService;
    @Resource
    private EcbdMoneyService ecbdMoneyService;
    @Resource
    private EcbdPriceService ecbdPriceService;


    public EcbDeliveryListVo getList(EcbDeliveryListBo bo) {
        EcbDelivery record = new EcbDelivery();
        record.setStartType(bo.getStartType());
        record.setEcbsId(bo.getEcbsId());
        List<EcbDelivery> list = ecbDeliveryService.getList(record);
        //long count = ecbDeliveryService.getCount(record);
        return new EcbDeliveryListVo(list, 0L);
    }


    public EcbDelivery getObject(EcbDeliveryBaseBo bo) {
        return getObjectPassEcbcId(bo.getEcdId());
    }


    @Transactional(rollbackFor = Exception.class)
    public String deal(EcbDeliveryDealBo bo) {
        Integer ecbdId = bo.getEcbdId();
        String deliveryName = bo.getDeliveryName();
        String description = bo.getDescription();

        EcbDelivery record = new EcbDelivery();
        EcbDelivery ecbDelivery;
        String msg;
        if (ObjectUtil.isNull(ecbdId)) {//插入
            record.setDeliveryName(deliveryName);
            ecbDelivery = ecbDeliveryService.getObject(record);
            if (ecbDelivery != null) {
                throw new RuntimeException("名称已占用");
            } else {
                Boolean startType = true;
                int sortId = 1;
                record = new EcbDelivery();
                log.info("record + " + CommonFunction.getGson().toJson(record));
                ecbDelivery = ecbDeliveryService.getObject(record);
                log.info(CommonFunction.getGson().toJson(ecbDelivery));
                if (ecbDelivery != null) {
                    sortId = ecbDelivery.getSortId() + 1;
                }
                Integer deliveryType = bo.getDeliveryType();
                record.setStartType(startType);
                record.setSortId(sortId);
                record.setDeliveryType(deliveryType);
                record.setDeliveryName(deliveryName);
                record.setDescription(description);
                log.info("record + " + CommonFunction.getGson().toJson(record));
                ecbDeliveryService.insert(record);
                msg = "正常插入数据";
            }
        } else {//修改
            record.setEcbdId(ecbdId);
            record.setDeliveryName(deliveryName);
            ecbDelivery = ecbDeliveryService.getObject(record);
            if (ecbDelivery != null) {
                throw new RuntimeException("名称已占用");
            } else {
                record.setEcbdId(ecbdId);
                record.setDeliveryName(deliveryName);
                record.setDescription(description);
                ecbDeliveryService.update(record);
                msg = "正常更新数据";
            }
        }
        return msg;
    }

    //sort
    public void sort(List<EcbDeliverySortBo> bos) {
        for (EcbDeliverySortBo bo : bos) {
            Integer ecbdId = bo.getEcdcId();
            Integer sortId = bo.getSortId();
            EcbDelivery record = new EcbDelivery();
            record.setEcbdId(ecbdId);
            record.setSortId(sortId);
        }
    }


    public String start(EcbDeliveryBaseBo bo) {
        Integer ecbdId = bo.getEcdId();
        EcbDelivery record = new EcbDelivery();
        record.setEcbdId(ecbdId);
        EcbDelivery ecbDelivery = ecbDeliveryService.getObject(record);
        String msg;
        Boolean startType = ecbDelivery.getStartType();
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        record = new EcbDelivery();
        record.setEcbdId(ecbDelivery.getEcbdId());
        record.setStartType(startType);
        ecbDeliveryService.update(record);
        return msg;
    }

    //delete
    @Transactional(rollbackFor = Exception.class)
    public void delete(EcbDeliveryBaseBo bo) {
        Integer ecbdId = bo.getEcdId();
        EcbDelivery record = new EcbDelivery();
        record.setEcbdId(ecbdId);
        EcbDelivery ecbDelivery = ecbDeliveryService.getObject(record);
        Integer deliveryType = ecbDelivery.getDeliveryType();
        if (ObjUtil.equals(1, deliveryType)) {
            EcbdMoney money = new EcbdMoney();
            money.setEcbdId(ecbdId);
            List<EcbdMoney> list = ecbdMoneyService.getList(money);
            if (CollUtil.isNotEmpty(list)) {
                throw new RuntimeException("当前物流数据在快递中还在使用，无法删除");
            }
        }
        if (ObjUtil.equals(2, deliveryType)) {
            EcbdPrice price = new EcbdPrice();
            price.setEcbdId(ecbdId);
            List<EcbdPrice> list = ecbdPriceService.getList(price);
            if (CollUtil.isNotEmpty(list)) {
                throw new RuntimeException("当前物流数据在快运中还在使用，无法删除");
            }
        }

        Integer sortId = ecbDelivery.getSortId();
        record = new EcbDelivery();
        record.setSortId(sortId);
        List<EcbDelivery> list = ecbDeliveryService.getList(record);
        Integer ecbd_id;
        for (EcbDelivery ecb_delivery : list) {
            ecbd_id = ecb_delivery.getEcbdId();
            sortId = ecb_delivery.getSortId() - 1;
            record.setEcbdId(ecbd_id);
            record.setSortId(sortId);
            ecbDeliveryService.update(record);
        }
        record = new EcbDelivery();
        record.setEcbdId(ecbdId);
        ecbDeliveryService.delete(record);
    }


    //getObjectPassEcbdId
    public EcbDelivery getObjectPassEcbcId(Integer ecbdId) {
        EcbDelivery record = new EcbDelivery();
        record.setEcbdId(ecbdId);
        return ecbDeliveryService.getObject(record);
    }


    //getListStart
    public List<EcbDelivery> getListStart() {
        EcbDelivery record = new EcbDelivery();
        record.setStartType(true);
        return ecbDeliveryService.getList(record);
    }

    //getObjectPassDeliveryName
    public EcbDelivery getObjectPassDeliveryName(String deliveryName) {
        EcbDelivery record = new EcbDelivery();
        record.setDeliveryName(deliveryName);
        return ecbDeliveryService.getObject(record);
    }
}
