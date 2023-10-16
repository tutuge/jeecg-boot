package org.jeecg.modules.cable.controller.userDelivery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.model.userDelivery.EcbuDeliveryModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "物流")
@RestController
public class EcbuDeliveryController {
    @Resource
    EcbuDeliveryModel ecbuDeliveryModel;
    @Resource
    EcuLoginModel ecuLoginModel;

    @Operation(summary = "获取物流列表")
    //getList
    @PostMapping({"/ecableErpPc/ecbuDelivery/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {

            map = ecbuDeliveryModel.getListAndCount(request);
        return map;
    }

    @Operation(summary = "获取物流详情")
    //getObject
    @PostMapping({"/ecableErpPc/ecbuDelivery/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {

            map = ecbuDeliveryModel.getObject(request);
        return map;
    }


    @Operation(summary = "编辑物流")
    //deal
    @PostMapping({"/ecableErpPc/ecbuDelivery/deal"})
    public Map<String, Object> deal(HttpServletRequest request) {

            map = ecbuDeliveryModel.deal(request);
        return map;
    }


    @Operation(summary = "物流排序")
    //sort
    @PostMapping({"/ecableErpPc/ecbuDelivery/sort"})
    public Map<String, Object> sort(HttpServletRequest request) {

            map = ecbuDeliveryModel.sort(request);
        return map;
    }


    @Operation(summary = "删除")
    //delete
    @PostMapping({"/ecableErpPc/ecbuDelivery/delete"})
    public Map<String, Object> delete(HttpServletRequest request) {

            ecbuDeliveryModel.delete(request);
        return map;
    }


    @Operation(summary = "开启禁用")
    //start
    @PostMapping({"/ecableErpPc/ecbuDelivery/start"})
    public Map<String, Object> start(HttpServletRequest request) {

            map = ecbuDeliveryModel.start(request);
        return map;
    }
}
