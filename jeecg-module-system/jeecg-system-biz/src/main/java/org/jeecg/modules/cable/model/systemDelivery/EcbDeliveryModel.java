package org.jeecg.modules.cable.model.systemDelivery;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.controller.systemDelivery.delivery.bo.EcbDeliveryBaseBo;
import org.jeecg.modules.cable.controller.systemDelivery.delivery.bo.EcbDeliveryDealBo;
import org.jeecg.modules.cable.controller.systemDelivery.delivery.bo.EcbDeliveryListBo;
import org.jeecg.modules.cable.controller.systemDelivery.delivery.bo.EcbDeliverySortBo;
import org.jeecg.modules.cable.controller.systemDelivery.delivery.vo.EcbDeliveryListVo;
import org.jeecg.modules.cable.entity.systemDelivery.EcbDelivery;
import org.jeecg.modules.cable.service.systemDelivery.EcbDeliveryService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EcbDeliveryModel {
    @Resource
    EcbDeliveryService ecbDeliveryService;


    //getList
    public EcbDeliveryListVo getList(EcbDeliveryListBo bo) {
        EcbDelivery record = new EcbDelivery();
        record.setStartType(bo.getStartType());
        List<EcbDelivery> list = ecbDeliveryService.getList(record);
        long count = ecbDeliveryService.getCount(record);
        return new EcbDeliveryListVo(list, count);
    }

    //getObject
    public EcbDelivery getObject(EcbDeliveryBaseBo bo) {
        return getObjectPassEcbcId(bo.getEcdcId());
    }

    //deal
    public String deal(EcbDeliveryDealBo bo) {
        Integer ecbdId = bo.getEcdcId();
        String deliveryName = bo.getDeliveryName();
        String description = bo.getDescription();

        EcbDelivery record = new EcbDelivery();
        EcbDelivery ecbDelivery;
        String msg;
        if (ecbdId == 0) {//插入
            record.setDeliveryName(deliveryName);
            ecbDelivery = ecbDeliveryService.getObject(record);
            if (ecbDelivery != null) {
                throw new RuntimeException("名称已占用");
            } else {
                boolean startType = true;
                Integer sortId = 1;
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

    //start
    public String start(EcbDeliveryBaseBo bo) {
        Integer ecbdId = bo.getEcdcId();
        EcbDelivery record = new EcbDelivery();
        record.setEcbdId(ecbdId);
        EcbDelivery ecbDelivery = ecbDeliveryService.getObject(record);
        String msg;
        boolean startType = ecbDelivery.getStartType();
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
    public void delete(EcbDeliveryBaseBo bo) {

        Integer ecbdId = bo.getEcdcId();
        EcbDelivery record = new EcbDelivery();
        record.setEcbdId(ecbdId);
        EcbDelivery ecbDelivery = ecbDeliveryService.getObject(record);
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

    /***===数据模型===***/
    //getObjectPassEcbdId
    public EcbDelivery getObjectPassEcbcId(Integer ecbdId) {
        EcbDelivery record = new EcbDelivery();
        record.setEcbdId(ecbdId);
        return ecbDeliveryService.getObject(record);
    }

    /***===数据模型===***/
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
