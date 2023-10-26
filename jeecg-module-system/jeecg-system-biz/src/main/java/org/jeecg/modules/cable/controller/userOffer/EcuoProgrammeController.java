package org.jeecg.modules.cable.controller.userOffer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userOffer.programme.bo.ProgrammeBaseBo;
import org.jeecg.modules.cable.controller.userOffer.programme.bo.ProgrammeDealBo;
import org.jeecg.modules.cable.controller.userOffer.programme.bo.ProgrammeSortBo;
import org.jeecg.modules.cable.entity.userOffer.EcuoProgramme;
import org.jeecg.modules.cable.model.userOffer.EcuoProgrammeModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "方案")
@RestController
@Slf4j
public class EcuoProgrammeController {
    @Resource
    EcuoProgrammeModel ecuoProgrammeModel;

    @Operation(summary = "编辑提交方案")
    @PostMapping({"/ecableErpPc/ecuoProgramme/deal"})
    public Result<String> deal(@RequestBody ProgrammeDealBo bo) {
        return Result.ok(ecuoProgrammeModel.deal(bo));
    }


    @Operation(summary = "方案列表")
    @PostMapping({"/ecableErpPc/ecuoProgramme/getList"})
    public Result<List<EcuoProgramme>> getList() {
        return Result.ok(ecuoProgrammeModel.getList());
    }


    @Operation(summary = "方案详情")
    @PostMapping({"/ecableErpPc/ecuoProgramme/getObject"})
    public Result<EcuoProgramme> getObject(@RequestBody ProgrammeBaseBo bo) {
        return Result.ok(ecuoProgrammeModel.getObject(bo));
    }


    @Operation(summary = "方案排序")
    @PostMapping({"/ecableErpPc/ecuoProgramme/sort"})
    public Result<?> sort(@RequestBody List<ProgrammeSortBo> request) {
        ecuoProgrammeModel.sort(request);
        return Result.ok();
    }


    @Operation(summary = "方案删除")
    @PostMapping({"/ecableErpPc/ecuoProgramme/delete"})
    public Result<?> delete(@RequestBody ProgrammeBaseBo bo) {
        ecuoProgrammeModel.delete(bo);
        return Result.ok();
    }
}
