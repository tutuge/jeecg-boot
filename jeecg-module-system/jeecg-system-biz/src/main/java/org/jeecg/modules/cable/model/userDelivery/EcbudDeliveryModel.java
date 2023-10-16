package org.jeecg.modules.cable.model.userDelivery;

import org.jeecg.modules.cable.entity.user.EcUser;
import org.jeecg.modules.cable.entity.userDelivery.EcbudDelivery;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userDelivery.EcbudDeliveryService;
import org.jeecg.modules.cable.tools.CommonFunction;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class EcbudDeliveryModel {//用户默认仓库
    @Resource
    EcbudDeliveryService ecbudDeliveryService;
    @Resource
    EcUserService ecUserService;

    //getObject
    public Map<String, Object> getObject(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        EcbudDelivery record = new EcbudDelivery();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setEcuId(ecuId);//暂不开启
        //log.info(CommonFunction.getGson().toJson(record));
        EcbudDelivery ecbudDelivery = ecbudDeliveryService.getObject(record);
        if (ecbudDelivery == null) {
            record.setSortId(1);
            ecbudDeliveryService.insert(record);
            ecbudDelivery = ecbudDeliveryService.getObject(record);
        }
        if (ecbudDelivery == null) {
            status = 3;//未获得数据
            code = "103";
            msg = "未获得数据";
        } else {
            map.put("object", ecbudDelivery);
            status = 4;//正常获取数据
            code = "200";
            msg = "正常获取数据";
        }
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
        int sortId = Integer.parseInt(request.getParameter("sortId"));
        EcbudDelivery record = new EcbudDelivery();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setEcuId(ecuId);
        EcbudDelivery ecbudDelivery = ecbudDeliveryService.getObject(record);
        if (ecbudDelivery == null) {//插入
            record.setEcCompanyId(ecUser.getEcCompanyId());
            record.setEcuId(ecuId);
            record.setSortId(sortId);
            ecbudDeliveryService.insert(record);
            status = 3;//正常插入数据
            code = "200";
            msg = "正常插入数据";
        } else {
            record.setEcbuddId(ecbudDelivery.getEcbuddId());
            record.setSortId(sortId);
            //log.info("record + " + CommonFunction.getGson().toJson(record));
            ecbudDeliveryService.updateByPrimaryKeySelective(record);
            status = 4;//正常插入数据
            code = "201";
            msg = "正常更新数据";
        }
        CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

}
