package org.jeecg.modules.cable.controller.userEcable.programme;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userEcable.programme.bo.ProgrammeSortBo;
import org.jeecg.modules.cable.controller.userEcable.programme.bo.ProgrammeBaseBo;
import org.jeecg.modules.cable.controller.userEcable.programme.bo.ProgrammeDealBo;
import org.jeecg.modules.cable.entity.userOffer.EcuoProgramme;
import org.jeecg.modules.cable.model.userOffer.EcuoProgrammeModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "成本加点方案--用户接口", description = "成本加点方案--用户接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "10", parseValue = true)})})
@RestController
@Slf4j
@RequestMapping("/ecableErpPc/ecuoProgramme")
public class EcuoProgrammeController {
    @Resource
    EcuoProgrammeModel ecuoProgrammeModel;

    @Operation(summary = "编辑提交方案")
    @PostMapping({"/deal"})
    public Result<String> deal(@Validated @RequestBody ProgrammeDealBo bo) {
        return Result.ok(ecuoProgrammeModel.deal(bo));
    }


    @Operation(summary = "方案列表")
    @PostMapping({"/getList"})
    public Result<List<EcuoProgramme>> getList() {
        List<EcuoProgramme> list = ecuoProgrammeModel.getList();
        return Result.ok(list);
    }


    @Operation(summary = "方案详情")
    @PostMapping({"/getObject"})
    public Result<EcuoProgramme> getObject(@Validated @RequestBody ProgrammeBaseBo bo) {
        return Result.ok(ecuoProgrammeModel.getObject(bo));
    }


    @Operation(summary = "方案排序")
    @PostMapping({"/sort"})
    public Result<?> sort(@Validated @RequestBody List<ProgrammeSortBo> request) {
        ecuoProgrammeModel.sort(request);
        return Result.ok();
    }


    @Operation(summary = "方案删除")
    @PostMapping({"/delete"})
    public Result<?> delete(@Validated @RequestBody ProgrammeBaseBo bo) {
        ecuoProgrammeModel.delete(bo);
        return Result.ok();
    }
}
