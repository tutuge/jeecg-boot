package org.jeecg.modules.cable.controller.user.data;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.user.data.bo.EcuDataBaseBo;
import org.jeecg.modules.cable.controller.user.data.bo.EcuDataDealBo;
import org.jeecg.modules.cable.controller.user.data.bo.EcuDataListBo;
import org.jeecg.modules.cable.controller.user.data.bo.EcuDataObjectBo;
import org.jeecg.modules.cable.entity.user.EcuData;
import org.jeecg.modules.cable.model.user.EcuDataModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "默认参数--用户接口", description = "默认参数--用户接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "99", parseValue = true)})})
@RestController
public class EcuDataController {
    @Resource
    EcuDataModel ecuDataModel;

    @Operation(summary = "根据id获取对象")
    @PostMapping({"/ecableErpPc/ecuData/getObject"})
    public Result<EcuData> getObject(@Validated @RequestBody EcuDataObjectBo bo) {
        return Result.ok(ecuDataModel.getObject(bo));
    }

    @Operation(summary = "获取默认参数列表")
    @PostMapping({"/ecableErpPc/ecuData/getList"})
    public Result<Map<String, Object>> getList(@Validated @RequestBody EcuDataListBo bo) {
        return Result.ok(ecuDataModel.getList(bo));
    }

    @Operation(summary = "编辑提交")
    @PostMapping({"/ecableErpPc/ecuData/deal"})
    public Result<String> deal(@Validated @RequestBody EcuDataDealBo bo) {
        return Result.ok(ecuDataModel.deal(bo));
    }

    @Operation(summary = "是否启用")
    @PostMapping({"/ecableErpPc/ecuData/start"})
    public Result<String> start(@Validated @RequestBody EcuDataBaseBo bo) {
        return Result.ok(ecuDataModel.start(bo));
    }
}
