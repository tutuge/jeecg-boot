package org.jeecg.modules.cable.controller.userOffer.core;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userOffer.core.bo.CoreBo;
import org.jeecg.modules.cable.entity.userOffer.EcuoCore;
import org.jeecg.modules.cable.model.userOffer.EcuoCoreModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "芯数")
@RestController
@Slf4j
public class EcuoCoreController {
    @Resource
    EcuoCoreModel ecuoCoreModel;

    @Operation(summary = "根据质量ID获取芯数列表")
    @PostMapping({"/ecableErpPc/ecuoCore/getList"})
    public Result<List<EcuoCore>> getList(@RequestBody CoreBo bo) {
        return Result.ok(ecuoCoreModel.getList(bo));
    }
}
