package org.jeecg.modules.cable.controller.systemDelivery.model;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemDelivery.model.bo.EcbdModelDealBo;
import org.jeecg.modules.cable.controller.systemDelivery.model.bo.ModelBaseBo;
import org.jeecg.modules.cable.entity.systemDelivery.EcbdModel;
import org.jeecg.modules.cable.model.systemDelivery.EcbdModelModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@ApiSupport(order =2050)
@RestController
@Slf4j
public class EcbdModelController {
    @Resource
    EcbdModelModel ecbdModelModel;

    //getObject
    @PostMapping({"/ecableAdminPc/ecbdModel/getObject"})
    public Result<EcbdModel> getObject(@RequestBody ModelBaseBo bo) {
        return Result.ok(ecbdModelModel.getObject(bo));
    }

    //deal
    @PostMapping({"/ecableAdminPc/ecbdModel/deal"})
    public Result<String> deal(@RequestBody EcbdModelDealBo bo) {
        return Result.ok(ecbdModelModel.deal(bo));
    }
}
